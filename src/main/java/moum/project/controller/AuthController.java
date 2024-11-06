package moum.project.controller;

import jakarta.servlet.http.HttpSession;

import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import moum.project.dao.UserDao;
import moum.project.dto.MailDTO;
import moum.project.service.MailService;
import moum.project.service.UserService;
import moum.project.vo.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * packageName    : moum.project.controller
 * fileName       : AuthController
 * author         : narilee
 * date           : 24. 10. 21.
 * description    : AuthController는 사용자 인증과 관련된 요청을 처리하는 Spring MVC 컨트롤러입니다.
 *                  로그인, 로그아웃 기능을 제공합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 10. 21.        narilee       최초 생성
 * 24. 10. 22.        narilee       로그인 구현 완료
 * 24. 10. 23.        narilee       쿠키 적용
 * 24. 10. 24.        narilee       스프링 시큐리티로 로그인, 로그아웃 이관
 * 24. 11. 04.        narilee       비밀번호 변경 기능 추가
 * 24. 11. 05.        narilee       비밀번호 찾기 기능 추가
 * 24. 11. 06.        narilee       비밀번호 찾기 기능 변수명 newPassword -> password
 */
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final MailService mailService;
  private final UserDao userDao;

  /**
   * 로그인 폼을 표시합니다.
   */
  @GetMapping("form")
  public String form() {
    return "auth/form";
  }

  /**
   * 로그인 실패 폼을 표시합니다.
   */
  @GetMapping("fail")
  public String fail(Model model) {
    model.addAttribute("errorMessage", "잘못된 아이디나 비밀번호입니다.");
    return "auth/fail";  // fail.html 페이지
  }

  /**
   * 로그인 상태를 확인하는 메서드 입니다.
   *
   * @param session
   * @return
   */
  @GetMapping("/auth/checkLoginStatus")
  @ResponseBody
  public Map<String, Boolean> checkLoginStatus(HttpSession session) {
    boolean isLoggedIn = session.getAttribute("user") != null;
    return Collections.singletonMap("isLoggedIn", isLoggedIn);
  }

  /**
   * 비밀번호 재설정 페이지를 표시합니다.
   */
  @GetMapping("/reset-password")
  public String resetPasswordForm() {
    return "auth/reset-password";
  }

  /**
   * 비밀번호 재설정을 처리합니다.
   * 이메일 인증 후 새로운 비밀번호로 업데이트합니다.
   *
   * @param requestBody 이메일과 새 비밀번호 정보를 포함한 요청
   * @return 처리 결과를 담은 ResponseEntity
   */
  @PostMapping("/reset-password")
  @ResponseBody
  public ResponseEntity<String> resetPassword(
      @RequestBody Map<String, String> requestBody
    ) {

    String email = requestBody.get("email");
    String password = requestBody.get("password");

    try {
      // 이메일로 사용자 찾기
      User user = userService.getByEmail(email);
      if (user == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("사용자를 찾을 수 없습니다.");
      }

      // 새 비밀번호 암호화 및 저장
      user.setPassword(passwordEncoder.encode(password));
      userService.update(user);

      return ResponseEntity.ok().body("success");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("비밀번호 변경 중 오류가 발생했습니다.");
    }
  }

  /**
   * 이메일 존재 여부를 확인하고 인증 코드를 발송합니다.
   *
   * @param mailDTO 메일 전송을 위한 데이터 전송 객체
   * @return 인증 메일
   */
  @PostMapping("/check-email")
  @ResponseBody
  public ResponseEntity<String> checkEmail(@RequestBody MailDTO mailDTO) {
    try {
      String email = mailDTO.getEmail();

      // 이메일 존재 여부 확인
      if (!userService.isEmailTaken(email)) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("등록되지 않은 이메일입니다.");
      }

      // 인증 코드 생성 및 발송
      String authCode = mailService.sendSimpleMessage(email);
      return ResponseEntity.ok(authCode);

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("인증 코드 발송 중 오류가 발생했습니다.");
    }
  }
}
