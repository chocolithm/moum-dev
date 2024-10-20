package moum.project.controller;

import java.util.List;
import moum.project.service.CollectionService;
import moum.project.service.CollectionStatusService;
import moum.project.service.MaincategoryService;
import moum.project.service.SubcategoryService;
import moum.project.vo.Collection;
import moum.project.vo.CollectionStatus;
import moum.project.vo.Maincategory;
import moum.project.vo.Subcategory;
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
  SubcategoryService subcategoryService;
  CollectionStatusService collectionStatusService;

  public CollectionController(
      CollectionService collectionService,
      MaincategoryService maincategoryService,
      SubcategoryService subcategoryService,
      CollectionStatusService collectionStatusService) {
    this.collectionService = collectionService;
    this.maincategoryService = maincategoryService;
    this.subcategoryService = subcategoryService;
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

  @GetMapping("view")
  public String view(int no, Model model) throws Exception {
    Collection collection = collectionService.get(no);
    List<Maincategory> maincategoryList = maincategoryService.list();
    List<Subcategory> subcategoryList = subcategoryService.list(collection.getMaincategoryNo());
    List<CollectionStatus> collectionStatusList = collectionStatusService.list();

    model.addAttribute("collection", collection);
    model.addAttribute("maincategoryList", maincategoryList);
    model.addAttribute("subcategoryList", subcategoryList);
    model.addAttribute("collectionStatusList", collectionStatusList);

    return "collection/view";
  }
}
