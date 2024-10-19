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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/collection")
public class CollectionController {

  CollectionService collectionService;
  MaincategoryService maincategoryService;
  CollectionStatusService collectionStatusService;

  public CollectionController(
      CollectionService collectionService,
      MaincategoryService maincategoryService,
      CollectionStatusService collectionStatusService) {
    this.collectionService = collectionService;
    this.maincategoryService = maincategoryService;
    this.collectionStatusService = collectionStatusService;
  }

  @GetMapping("form")
  public String form(Model model) throws Exception {
    List<Maincategory> maincategoryList = maincategoryService.list();
    List<CollectionStatus> collectionStatusList = collectionStatusService.list();

    model.addAttribute("maincategoryList", maincategoryList);
    model.addAttribute("collectionStatusList", collectionStatusList);

    return "collection/form";
  }

  @PostMapping("add")
  public String add(Collection collection) throws Exception {
    collection.setUserNo(2);
    collectionService.add(collection);
    return "redirect:/myHome";
  }
}
