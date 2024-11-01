package moum.project.controller;

import lombok.RequiredArgsConstructor;
import moum.project.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

  private final ReportService reportService;

  @GetMapping("/list")
  public String list() {
    
    return "report/list";

  }
}