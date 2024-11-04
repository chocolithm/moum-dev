package moum.project.controller;

import lombok.RequiredArgsConstructor;
import moum.project.service.ReportService;
import moum.project.vo.Report;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

  private final ReportService reportService;

  @GetMapping("/list")
  public String list(Model model) throws Exception {
    model.addAttribute("reportList", reportService.list());
    return "report/list";
  }
}
