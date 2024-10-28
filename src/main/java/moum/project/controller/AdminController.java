package moum.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

  @GetMapping("management")
  public void management(Model model) {

  }
}
