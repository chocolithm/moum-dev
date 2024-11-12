package moum.project.controller;

import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import moum.project.service.AchievementService;
import moum.project.service.UserService;
import moum.project.vo.Achievement;
import moum.project.vo.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/achievement")
@RequiredArgsConstructor
public class AchievementController {


  @NonNull
  private final AchievementService achievementService;

  @NonNull
  private final UserService userService;

  @GetMapping("list")
  public String list(Model model) throws Exception {
    List<Achievement> list = achievementService.list();
    model.addAttribute("list", list); //모델에다가 업적 정보를 가진 list를 list라는 이름으로 담는다.
    return "achievement/list";
  }

  @GetMapping("listByUser")
  public String listByUser(Model model, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
    // 로그인한 사용자 정보를 통해 사용자 번호를 가져옵니다.
    User sender = userService.getByEmail(userDetails.getUsername());
    int userNo = sender.getNo();

    List<Achievement> list = achievementService.listByUser(userNo);
    model.addAttribute("list", list);
    return "achievement/listByUser";
  }


  @GetMapping("view")
  @ResponseBody
  public Object view(String id) throws Exception {
    return achievementService.get(id); // 상세 페이지 뷰 반환
  }

  @GetMapping("delete")
  @ResponseBody
  public Object delete(String id) throws Exception {
    achievementService.delete(id);
    return "Achievement with id" + id + "삭제되었습니다.";
  }

  @PostMapping("add")
  @ResponseBody
  public Object add(@RequestBody Achievement achievement) throws Exception {
    achievementService.add(achievement);
    return achievementService.get(achievement.getId());
  }

  @PostMapping("update")
  @ResponseBody
  public Object update(@RequestBody Achievement achievement) throws Exception {
    achievementService.update(achievement);
    return achievementService.get(achievement.getId());
  }

  @GetMapping("updateCount")
  @ResponseBody
  public String updateCount(
      String id,
      @AuthenticationPrincipal UserDetails userDetails) throws Exception {

    User loginUser = userService.getByEmail(userDetails.getUsername());
    Achievement achievement = achievementService.findMyAchievement(id, loginUser.getNo());

    if (achievement.getProgress() == 100) {
      return "ignored";
    }

    achievement.setCurrentCount(achievement.getCurrentCount() + 1);
    System.out.println(achievement);
    achievement.setProgress(achievement.getCurrentCount() / achievement.getMaxCount());

    if (achievementService.updateCount(achievement)) {
      if (achievement.getProgress() == 100) {
        achievementService.completeAchievement(achievement);
        return "acquired";
      }
      return "success";
    }
    return "failure";
  }

}
