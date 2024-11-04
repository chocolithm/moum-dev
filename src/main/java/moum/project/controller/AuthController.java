package moum.project.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;

import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import moum.project.dao.UserDao;
import moum.project.service.MailService;
import moum.project.vo.User;
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
 */
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

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
   * 비밀번호 재설정 페이지를 보여줍니다.
   *
   * @param model Spring MVC 모델
   * @return 비밀번호 재설정 페이지 뷰 이름
   */
  @GetMapping("/resetPassword")
  public String resetPassword(Model model) {
    return "auth/resetPassword";
  }

  /**
   * 비밀번호 재설정을 위한 인증 코드를 이메일로 전송합니다.
   *
   * @param email 사용자 이메일 주소
   * @param model Spring MVC 모델
   * @return 인증 코드 확인 페이지 또는 에러 발생 시 비밀번호 재설정 페이지
   * @throws MessagingException 이메일 전송 실패 시 발생
   */
  @PostMapping("/sendPasswordResetCode")
  public String sendPasswordResetCode(@RequestParam String email, Model model)
      throws MessagingException {
    User user = userDao.findByEmail(email); // 이메일로 사용자 조회
    if (user != null) {
      String authCode = mailService.sendSimpleMessage(email); // 인증 코드 전송
      model.addAttribute("authCode", authCode); // 인증 코드를 모델에 추가
      model.addAttribute("email", email); // 이메일 추가
      return "/auth/verifyResetCode"; // 인증 코드 확인 페이지로 이동
    } else {
      model.addAttribute("error", "해당 이메일을 사용하는 계정을 찾을 수 없습니다.");
      return "/auth/resetPassword"; // 비밀번호 찾기 페이지로 다시 이동
    }
  }


  /**
   * 인증 코드 확인 페이지를 보여줍니다.
   *
   * @param model Spring MVC 모델
   * @return 인증 코드 확인 페이지 뷰 이름
   */
  @GetMapping("/verifyResetCode")
  public String verifyResetCode(Model model) {
    return "auth/verifyResetCode";
  }

  /**
   * 사용자가 입력한 인증 코드의 유효성을 검증합니다.
   *
   * @param email 사용자 이메일 주소
   * @param inputCode 사용자가 입력한 인증 코드
   * @param authCode 시스템이 생성한 인증 코드
   * @param model Spring MVC 모델
   * @return 인증 성공 시 새 비밀번호 설정 페이지, 실패 시 인증 코드 확인 페이지
   */
  @PostMapping("/verifyResetCode")
  public String verifyResetCode(@RequestParam String email, @RequestParam String inputCode,
      @RequestParam String authCode, Model model) {
    if (inputCode.equals(authCode)) { // 입력한 코드가 인증 코드와 일치하는지 확인
      model.addAttribute("email", email); // 이메일 추가
      return "/auth/setNewPassword"; // 비밀번호 재설정 페이지로 이동
    } else {
      model.addAttribute("error", "인증 코드가 올바르지 않습니다.");
      return "/auth/verifyResetCode"; // 인증 코드 확인 페이지로 다시 이동
    }
  }

  /**
   * 새 비밀번호 설정 페이지를 보여줍니다.
   *
   * @param model Spring MVC 모델
   * @return 새 비밀번호 설정 페이지 뷰 이름
   */
  @GetMapping("/setNewPassword")
  public String setNewPassword(Model model) {
    return "auth/setNewPassword";
  }

  /**
   * 새로운 비밀번호를 설정하고 저장합니다.
   *
   * @param email 사용자 이메일 주소
   * @param newPassword 새로 설정할 비밀번호
   * @param model Spring MVC 모델
   * @return 성공 시 로그인 페이지로 리다이렉트, 실패 시 비밀번호 재설정 페이지
   * @throws Exception 비밀번호 업데이트 실패 시 발생
   */
  @PostMapping("/setNewPassword")
  public String setNewPassword(@RequestParam String email, @RequestParam String newPassword, Model model)
      throws Exception {
    User user = userDao.findByEmail(email);
    if (user != null) {
      user.setPassword(passwordEncoder.encode(newPassword)); // 새 비밀번호 설정
      userDao.update(user); // 데이터베이스에 업데이트
      return "redirect:/auth/form?passwordResetSuccess=true"; // 로그인 페이지로 리다이렉트
    } else {
      model.addAttribute("error", "비밀번호 재설정 중 오류가 발생했습니다.");
      return "/auth/setNewPassword"; // 비밀번호 재설정 페이지로 다시 이동
    }
  }
}
