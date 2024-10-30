package moum.project.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import moum.project.service.StorageService;
import moum.project.service.UserService;
import moum.project.vo.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

/**
 * packageName    : moum.project.controller
 * fileName       : UserController
 * author         : narilee
 * date           : 24. 10. 21.
 * description    : UserController는 사용자 관련 요청을 처리하는 컨트롤러입니다,
 *                  이 컨트롤러는 사용자 가입 폼과 가입 처리를 위한 기능을 제공합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 10. 21.        narilee       최초 생성
 * 24. 10. 25.        narilee       회원 가입 성공시 알림 표시
 * 24. 10. 25.        narilee       회원 가입시 닉네임, 이메일 중복체크
 * 24. 10. 29.        narilee       닉네임, 비밀번호, 프로필 수정 기능 추가)
 */
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final StorageService storageService;
  private final PasswordEncoder passwordEncoder;

  private String folderName = "user/profile/";


  /**
   * 이 메서드는 "/user/myInfo" URL로 들어오는 GET 요청을 처리합니다.
   *
   * @return "myInfo" 뷰 이름을 반환합니다.
   */
  @GetMapping("/myInfo")
  public String myInfo(@AuthenticationPrincipal UserDetails userDetails, Model model) throws Exception {
    if (userDetails == null) {
      throw new Exception("로그인이 필요합니다.");
    }

    String email = userDetails.getUsername();
    User user = userService.getByEmail(email);

    model.addAttribute("user", user);
    return "user/myInfo";
  }

  /**
   * 이 메서드는 "/user/update" URL로 들어오는 GET 요청을 처리합니다.
   *
   * @return "update" 뷰 이름을 반환합니다.
   */
  @GetMapping("/update")
  public String updateMyInfo(@AuthenticationPrincipal UserDetails userDetails, Model model) throws Exception {
    if (userDetails == null) {
      throw new Exception("로그인이 필요합니다.");
    }

    String email = userDetails.getUsername();
    User user = userService.getByEmail(email);

    model.addAttribute("user", user);
    return "user/update";
  }

  /**
   * 사용자 정보를 업데이트하는 메서드입니다.
   * 닉네임, 비밀번호, 프로필 사진을 수정할 수 있습니다.
   *
   * @param no 사용자 번호
   * @param nickname 새로운 닉네임 (선택)
   * @param password 새로운 비밀번호 (선택)
   * @param userDetails 현재 로그인된 사용자 정보
   * @param file 새로운 프로필 사진 파일 (선택)
   * @param redirectAttributes 리다이렉트 시 전달할 속성
   * @return 리다이렉트할 페이지 경로
   * @throws Exception 로그인되지 않은 경우 또는 회원 정보가 존재하지 않는 경우
   */
  @PostMapping("/update")
  public String updateMyInfo(
      @RequestParam("no") int no,
      @RequestParam("nickname") String nickname,
      @RequestParam(value = "password", required = false) String password,
      @AuthenticationPrincipal UserDetails userDetails,
      MultipartFile file,
      RedirectAttributes redirectAttributes,
      HttpSession session
      ) throws Exception {

    User old = userService.get(no);

    if (userDetails == null) {
      throw new Exception("로그인이 필요합니다.");
    }

    String email = userDetails.getUsername();
    User user = userService.getByEmail(email);

    // 닉네임 수정
    if (nickname != null && !nickname.isEmpty()) {
      user.setNickname(nickname);
    } else {
      user.setNickname(old.getNickname());
    }

      // 비밀번호 수정 (암호화하여 저장)
      if (password != null && !password.isEmpty()) {
        user.setPassword(passwordEncoder.encode(password));
      } else {
        user.setPassword(old.getPassword());
      }

    // 프로필 사진 처리 로직
    if (file != null && file.getSize() > 0) {
      // 기존 프로필 사진 삭제
      storageService.delete(folderName + old.getPhoto());

      // 새 파일 이름 생성
      String filename = UUID.randomUUID().toString();

      // 파일 업로드 옵션 설정
      HashMap<String, Object> options = new HashMap<>();
      options.put(StorageService.CONTENT_TYPE, file.getContentType());

      // 새 파일 업로드
      storageService.upload(folderName + filename, file.getInputStream(), options);

      // 사용자 프로필에 새로운 파일명 설정
      user.setPhoto(filename);

    } else {
      // 파일이 없을 경우 기존 파일명 유지
      user.setPhoto(old.getPhoto());
    }

    if (userService.update(user)) {
      redirectAttributes.addFlashAttribute("message", "정보가 성공적으로 수정되었습니다.");
      return "redirect:/user/myInfo";
    } else {
      throw new Exception("없는 회원입니다!");
    }
  }

  /**
   * 회원가입 폼을 표시하는 메서드입니다.
   *
   * @param model 뷰에 전달할 모델 객체
   * @return 회원가입 페이지의 뷰 이름
   */
  @GetMapping("/signup")
  public String signupForm(Model model) {
    model.addAttribute("user", new User());
    return "user/signup";
  }

  /**
   * 회원가입 요청을 처리하는 메서드입니다.
   *
   * @param user 회원가입 폼에서 제출된 사용자 정보
   * @param redirectAttributes 리다이렉트 시 전달할 속성
   * @return 성공 시 홈페이지로, 실패 시 회원가입 페이지로 리다이렉트
   */
  @PostMapping("/signup")
  public String signupSubmit(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
    try {
      userService.add(user);
      return "redirect:/home?signupSuccess=true";
    } catch (Exception e) {
      return "redirect:/user/signup?signupError=true";
    }
  }

  /**
   * 닉네임과 이메일의 중복 여부를 확인하는 메서드입니다.
   *
   * @param nickname 중복 확인할 닉네임 (선택)
   * @param email 중복 확인할 이메일 (선택)
   * @return 중복 여부를 담은 Map (키: isNicknameTaken/isEmailTaken, 값: boolean)
   * @throws Exception 서버 오류 발생 시
   */
  @GetMapping("/check-duplicate")
  @ResponseBody
  public Map<String, Boolean> checkDuplicate(
      @RequestParam(required = false) String nickname,
      @RequestParam(required = false) String email) throws Exception {
    Map<String, Boolean> response = new HashMap<>();
    if (nickname != null && !nickname.isEmpty()) {
      response.put("isNicknameTaken", userService.isNicknameTaken(nickname));
    }
    if (email != null && !email.isEmpty()) {
      response.put("isEmailTaken", userService.isEmailTaken(email));
    }
    return response;
  }
}
