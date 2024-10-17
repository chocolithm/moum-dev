package moum.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * packageName    : moum.project.controller
 * fileName       : HomeController
 * author         : narilee
 * date           : 24. 10. 16.
 * description    : 홈페이지와 관련된 요청을 처리하는 컨트롤러 클래스입니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 10. 15.        narilee       최초 생성
 * 24. 10. 17.        narilee       테스트용 순수 HTML인 home2로 뷰 추가
 */
@Controller
public class HomeController {

  /**
   * 이 메서드는 "/", "/home" URL로 들어오는 GET 요청을 처리합니다.
   * header.html 문제로 home.html의 header 부분 주석 처리로 현재 접속 가능
   *
   * @return "home" 뷰 이름을 반환합니다.
   */
  @GetMapping( {"/","/home"})
  public String home(HttpServletRequest request, Model model) {
    model.addAttribute("requestUri", request.getRequestURI());
    return "home";
  }

  /**
   * 이 메서드는 테스트용으로 "/home2" URL로 들어오는 GET 요청을 처리합니다.
   *
   * @return 순수 html인 "home2" 뷰 이름을 반환합니다.
   */
  @GetMapping( "/home2")
  public String home2() {
    return "home2";
  }
}
