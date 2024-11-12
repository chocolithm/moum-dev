package moum.project.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moum.project.service.AlertService;
import moum.project.service.UserService;
import moum.project.vo.Alert;
import moum.project.vo.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/alert")
@RequiredArgsConstructor
public class AlertController {

  private final AlertService alertService;
  private final UserService userService;

  @GetMapping("/listByUser")
  @ResponseBody
  public List<Alert> listByUser(@AuthenticationPrincipal UserDetails userDetails, int pageNo) throws Exception {
    User loginUser = userService.getByEmail(userDetails.getUsername());
    int pageCount = 20;

    return alertService.listByUser(loginUser.getNo(), (pageNo - 1) * pageCount, pageCount);
  }

  @GetMapping("/read")
  @ResponseBody
  public boolean read(int no) throws Exception {
    return alertService.updateRead(no);
  }

  @GetMapping("/readAll")
  @ResponseBody
  public boolean read(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
    User loginUser = userService.getByEmail(userDetails.getUsername());

    return alertService.updateReadAll(loginUser.getNo());
  }

  @GetMapping("delete")
  @ResponseBody
  public String delete(int no) throws Exception {
    if (alertService.delete(no)) {
      return "success";
    }
    return "failure";
  }

  @GetMapping("count")
  @ResponseBody
  public int count(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
    User loginUser = userService.getByEmail(userDetails.getUsername());
    return alertService.countUnreadByUser(loginUser.getNo());
  }
}
