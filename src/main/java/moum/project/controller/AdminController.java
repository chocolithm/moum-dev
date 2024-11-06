package moum.project.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moum.project.service.UserService;
import moum.project.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * packageName    : moum.project.controller
 * fileName       : AdminController
 * author         : narilee
 * date           : 24. 10. 28.
 * description    : 관리자 페이지를 처리하는 컨트롤러입니다.
                    관리자는 admin 설정이 1이거나 true 일때 접근 가능합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 10. 28.        narilee       최초 생성
 */
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

  private final UserService userService;

  /**
   * 이 메서드는 "/admin/management" URL로 들어오는 GET 요청을 처리합니다.
   *
   * @return "management" 뷰 이름을 반환합니다.
   */
  @GetMapping("management")
  public void management(Model model) {

  }

  @GetMapping("management/board/list")
  public String boardlist(Model model) {
    return "admin/board/list";
  }

  @GetMapping("/user/list")
  @ResponseBody
  public List<User> list(int pageNo, int pageCount) throws Exception {
    return userService.listByPage((pageNo - 1) * pageCount, pageCount);
  }
}
