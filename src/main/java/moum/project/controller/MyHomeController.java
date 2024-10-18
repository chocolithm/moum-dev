package moum.project.controller;

import java.util.List;
import moum.project.service.CollectionService;
import moum.project.service.MaincategoryService;
import moum.project.vo.Collection;
import moum.project.vo.Maincategory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyHomeController {

  CollectionService collectionService;
  MaincategoryService maincategoryService;

  public MyHomeController(
      CollectionService collectionService,
      MaincategoryService maincategoryService) {
    this.collectionService = collectionService;
    this.maincategoryService = maincategoryService;
  }

  @RequestMapping("/myHome")
  public void myHome(Model model) throws Exception {
    List<Collection> collectionList = collectionService.list();
    List<Maincategory> maincategoryList = maincategoryService.list();

    model.addAttribute("collectionList", collectionList);
    model.addAttribute("maincategoryList", maincategoryList);
  }
}
