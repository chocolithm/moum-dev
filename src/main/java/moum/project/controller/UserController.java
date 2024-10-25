package moum.project.controller;

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
 * 24. 10. 25.        narilee       회원 가입 성공시 알림 표시
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

  @GetMapping("/myInfo")
  public void myInfo(Model model) {

  }

  /**
   * 사용자 가입을 처리합니다.
   *
   * @param user 가입할 사용자 정보
   * @return 성공 시 성공 알러트 표시후 /home으로 실패시 실패 알러트 표시
   */
  @PostMapping("/signup")
  public String signupSubmit(@ModelAttribute User user, RedirectAttributes redirectAttributes) {

    try {
      userService.add(user);
      return "redirect:/home?signupSuccess=true";  // 성공 시 쿼리 파라미터 전달
    } catch (Exception e) {
      return "redirect:/user/signup?signupError=true";  // 실패 시 쿼리 파라미터 전달
    }
  }

}
