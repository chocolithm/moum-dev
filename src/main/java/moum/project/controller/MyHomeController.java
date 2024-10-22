package moum.project.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moum.project.service.CollectionService;
import moum.project.service.MaincategoryService;
import moum.project.vo.Collection;
import moum.project.vo.Maincategory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MyHomeController {

  private final CollectionService collectionService;
  private final MaincategoryService maincategoryService;

  @RequestMapping("/myHome")
  public void myHome(
      Model model,
      @AuthenticationPrincipal UserDetails userDetails) throws Exception {

    List<Collection> collectionList = collectionService.list(userDetails.getUsername());
    List<Maincategory> maincategoryList = maincategoryService.list();

    model.addAttribute("collectionList", collectionList);
    model.addAttribute("maincategoryList", maincategoryList);
  }
}
