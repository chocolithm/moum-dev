package moum.project.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moum.project.config.CustomUserDetails;
import moum.project.service.CollectionCategoryService;
import moum.project.service.CollectionService;
import moum.project.service.UserService;
import moum.project.vo.Collection;
import moum.project.vo.Maincategory;
import moum.project.vo.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
 * 24. 11. 13.        narilee       Spring Security를 이용한 로그인유무 인증 도입
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

  private final UserService userService;
  private final CollectionService collectionService;
  private final CollectionCategoryService categoryService;

  /**
   * 비로그인 유저면 /home, 로그인 유저면 /myhome으로 보내는 클래스입니다.
   *
   * @param model 모델 객체
   * @return 로그인시 /myhome, 비로그인시 /home으로 이동
   * @throws Exception 로그인 인증 오류시 발생
   */
  @GetMapping("/home")
  public String home(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) throws Exception {
    if (userDetails == null) {
      return "home";
    }

    User loginUser = userService.getByEmail(userDetails.getUsername());
    List<Collection> collectionList = collectionService.list(loginUser.getNo());
    List<Maincategory> maincategoryList = categoryService.listMaincategory();

    model.addAttribute("collectionList", collectionList);
    model.addAttribute("maincategoryList", maincategoryList);

    if (loginUser.isAdmin()) {
      return "admin/management";
    }

    // OAuth2 로그인인 경우 myhome으로 리다이렉트
//    if (userDetails.getAttributes() != null) {
//      return "redirect:/myhome";
//    }

    return "myhome";
  }

  /**
   * 비로그인 유저면 /home, 로그인 유저면 /myhome으로 보내는 클래스입니다.
   *
   * @param authentication 인증 요청 또는 인증된 주체에 대한 토큰
   * @param model 모델 객체
   * @return 로그인시 /myhome, 비로그인시 /home으로 이동
   * @throws Exception 로그인 인증 오류시 발생
   */
  @GetMapping("/myhome")
  public String myHome(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) throws Exception {
    if (userDetails == null) {
      return "redirect:/home?login=true";
    }

    User loginUser = userService.getByEmail(userDetails.getUsername());
    List<Collection> collectionList = collectionService.list(loginUser.getNo());
    List<Maincategory> maincategoryList = categoryService.listMaincategory();

    model.addAttribute("collectionList", collectionList);
    model.addAttribute("maincategoryList", maincategoryList);

    return "myhome";
  }

  @GetMapping("/")
  public String index() {
    return "redirect:/home";
  }
}
