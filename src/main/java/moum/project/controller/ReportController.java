package moum.project.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moum.project.service.ReportService;
import moum.project.service.UserService;
import moum.project.vo.Report;
import moum.project.vo.ReportCategory;
import moum.project.vo.ReportResultCategory;
import moum.project.vo.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

  private final ReportService reportService;
  private final UserService userService;

  @PostMapping("/add")
  @ResponseBody
  public String add(Report report, @AuthenticationPrincipal UserDetails userDetails) throws Exception {

    User loginUser = userService.getByEmail(userDetails.getUsername());
    report.setUser(loginUser);

    if (reportService.add(report)) {
      return "success";
    }
    return "failure";
  }

  @GetMapping("/list")
  public String list(Model model) throws Exception {
    model.addAttribute("reportList", reportService.list());
    return "report/list";
  }

  @GetMapping("/listReportCategories")
  @ResponseBody
  public List<ReportCategory> listReportCategories() throws Exception {
    return reportService.listReportCategories();
  }

  @GetMapping("/listResultCategories")
  @ResponseBody
  public List<ReportResultCategory> listResultCategories() throws Exception {
    return reportService.listResultCategories();
  }

  @PutMapping("/updateResult")
  @ResponseBody
  public String updateResult(Report report) throws Exception {
    if (reportService.updateResult(report)) {
      return "success";
    }
    return "failure";
  }
}
