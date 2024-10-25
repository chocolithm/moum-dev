package moum.project.controller;

import lombok.RequiredArgsConstructor;
import moum.project.service.UserService;
import moum.project.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

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

  /**
   * 사용자의 닉네임과 이메일의 중복 여부를 확인하는 API 엔드포인트입니다.
   *
   * @param nickname 중복 검사할 닉네임 (선택적)
   * @param email 중복 검사할 이메일 주소 (선택적)
   * @return 닉네임과 이메일의 중복 여부를 담은 Map 객체
   * @throws Exception 중복 검사 과정에서 발생할 수 있는 예외
   */
  @GetMapping("/check-duplicate")
  @ResponseBody
  public Map<String, Boolean> checkDuplicate(@RequestParam(required = false) String nickname,
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
