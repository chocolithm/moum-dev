package moum.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class ErrorController {

  @ExceptionHandler(Exception.class)
  public String handleException(Exception e, Model model) {
    model.addAttribute("exception", e);
    return "error";
  }
}
