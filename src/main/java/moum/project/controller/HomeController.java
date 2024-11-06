package moum.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import moum.project.service.CollectionCategoryService;
import moum.project.service.CollectionService;
import moum.project.service.UserService;
import moum.project.vo.Collection;
import moum.project.vo.Maincategory;
import moum.project.vo.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
 * 24. 10. 19.        narilee       테스트용 순수 HTML인 home2로 뷰 제거
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

  private final UserService userService;
  private final CollectionService collectionService;
  private final CollectionCategoryService categoryService;

  /**
   * 이 메서드는 "/", "/home" URL로 들어오는 GET 요청을 처리합니다.
   *
   * @return "home" 뷰 이름을 반환합니다.
   */
  @GetMapping("/home")
  public String home(
      HttpServletRequest request,
      Model model,
      @AuthenticationPrincipal UserDetails userDetails) throws Exception {

    if (userDetails == null) {
      return "/home";

    } else {
      String email = userDetails.getUsername();
      User loginUser = userService.getByEmail(email);

      List<Collection> collectionList = collectionService.list(loginUser.getNo());
      List<Maincategory> maincategoryList = categoryService.listMaincategory();

      model.addAttribute("collectionList", collectionList);
      model.addAttribute("maincategoryList", maincategoryList);

      return "/myhome";
    }
  }

  @GetMapping("/")
  public String index() {
    return "redirect:/home";
  }
}
