package moum.project.controller;

import java.util.*;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import moum.project.service.AchievementService;
import moum.project.service.StorageService;
import moum.project.service.SubcategoryService;
import moum.project.vo.*;
import moum.project.vo.Collection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/achievement")
@RequiredArgsConstructor
public class AchievementController {

  @NonNull
  private final AchievementService achievementService;

  @GetMapping("list")
  @ResponseBody
  public Object list(Model model) throws Exception {
    return achievementService.list();
  }

  @GetMapping("view")
  @ResponseBody
  public Object view(String photo) throws Exception {
    return achievementService.get(photo); // 상세 페이지 뷰 반환
  }


}