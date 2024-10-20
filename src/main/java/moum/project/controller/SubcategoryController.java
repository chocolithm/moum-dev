package moum.project.controller;

import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import moum.project.service.SubcategoryService;
import moum.project.vo.Collection;
import moum.project.vo.Maincategory;
import moum.project.vo.Subcategory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/subcategory")
@RequiredArgsConstructor
public class SubcategoryController {

  @NonNull SubcategoryService subcategoryService;

  @GetMapping("list")
  @ResponseBody
  public List<Subcategory> list(int maincategoryNo) throws Exception {
    return subcategoryService.list(maincategoryNo);
  }
}
