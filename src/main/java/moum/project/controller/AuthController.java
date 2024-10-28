package moum.project.controller;

import jakarta.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
 */
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

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

}
