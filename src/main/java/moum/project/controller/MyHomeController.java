package moum.project.controller;

import java.util.List;
import moum.project.service.CollectionService;
import moum.project.vo.Collection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MyHomeController {

  CollectionService collectionService;

  public MyHomeController(CollectionService collectionService) {
    this.collectionService = collectionService;
  }

  @PostMapping("/myHome")
  public void myHome(Model model) throws Exception {
    List<Collection> list = collectionService.list();
    model.addAttribute("collectionList", list);
  }
}
