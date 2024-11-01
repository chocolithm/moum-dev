package moum.project.controller;

import lombok.RequiredArgsConstructor;
import moum.project.service.ReportService;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ReportController {

  private final ReportService reportService;

}
