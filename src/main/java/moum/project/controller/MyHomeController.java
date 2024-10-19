package moum.project.controller;

import java.util.List;
import moum.project.service.CollectionService;
import moum.project.service.CollectionStatusService;
import moum.project.service.MaincategoryService;
import moum.project.vo.Collection;
import moum.project.vo.CollectionStatus;
import moum.project.vo.Maincategory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyHomeController {

  CollectionService collectionService;
  MaincategoryService maincategoryService;
  CollectionStatusService collectionStatusService;

  public MyHomeController(
      CollectionService collectionService,
      MaincategoryService maincategoryService,
      CollectionStatusService collectionStatusService) {
    this.collectionService = collectionService;
    this.maincategoryService = maincategoryService;
    this.collectionStatusService = collectionStatusService;
  }

  @RequestMapping("/myHome")
  public void myHome(Model model) throws Exception {
    List<Collection> collectionList = collectionService.list();

    model.addAttribute("collectionList", collectionList);
  }
}
