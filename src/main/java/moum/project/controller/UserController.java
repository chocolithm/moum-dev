package moum.project.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import moum.project.service.UserService;
import moum.project.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
 */
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  /**
   * 사용자 가입 폼을 반환 합니다.
   *
   * @param model 사용자 폼에 데이터를 추가하기 위한 모델 객체
   * @return 회원가입 폼 뷰의 이름
   */
  @GetMapping("/signup")
  public String signupForm(Model model) {
    model.addAttribute("user", new User());
    return "user/signup";
  }

  /**
   * 사용자 가입을 처리합니다.
   *
   * @param user 가입할 사용자 정보
   * @param redirectAttributes 리다이렉트 후 전달할 메시지를 추가하기 위한 객체
   * @return 성공 시 인증 폼 페이지로 리다이렉트, 실패시 가입 폼 페이지로 리다이렉트
   */
  @PostMapping("/signup")
  public String signupSubmit(@ModelAttribute User user, RedirectAttributes redirectAttributes) {

    try {
      userService.add(user);
      redirectAttributes.addFlashAttribute("signupSuccess", true);
      return "redirect:/";

    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("signupError", "회원가입 중 오류가 발생했습니다.");
      return "redirect:/user/signup";
    }
  }

  /**
   * "myInfo" 엔드포인트에 대한 HTTP GET 요청을 처리합니다.
   *
   * @param session 사용자를 포합하는 현재 HTTP 세션
   * @param model 뷰에 속성을 추가하기 위한 모델 객체
   * @return 사용자 정보가 표시될 뷰 이름 "home"
   * @throws Exception 사용자가 로그인 되어 있지 않은 경우
   */
  @GetMapping("myInfo")
  public String myInfo(
      HttpSession session,
      Model model) throws Exception {
    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인이 필요합니다.");
    }
    User user = userService.get(loginUser.getNo());
    model.addAttribute("user", user);
    return "/home";
  }
}
