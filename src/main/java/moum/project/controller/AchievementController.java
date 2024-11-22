package moum.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import moum.project.service.AchievementService;
import moum.project.service.StorageService;
import moum.project.service.UserService;
import moum.project.vo.Achievement;
import moum.project.vo.AttachedFile;
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
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/achievement")
@RequiredArgsConstructor
public class AchievementController {

  private final AchievementService achievementService;
  private final UserService userService;
  private final StorageService storageService;

  private final String folderName = "achievement/";

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
  public Object add(
      Achievement achievement,
      MultipartFile[] files) throws Exception {

    if (files != null) {
      for (int i = 0; i < files.length; i++) {
        if (files[i].getSize() == 0) {
          continue;
        }

        AttachedFile attachedFile = new AttachedFile();
        attachedFile.setFileCategory(AttachedFile.ACHIEVEMENT);
        attachedFile.setFilename(files[0].getOriginalFilename());
        attachedFile.setOriginFilename(files[0].getOriginalFilename());

        Map<String, Object> options = new HashMap<>();
        options.put(StorageService.CONTENT_TYPE, files[i].getContentType());

        storageService.upload(
            folderName + (i == 0 ? "" : "complete/") + attachedFile.getFilename(),
            files[i].getInputStream(),
            options);
      }

      achievement.setPhoto(files[0].getOriginalFilename());
    }

    if (achievementService.add(achievement)) {
      return "success";
    }
    return "failure";
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

    // 현재 로그인 유저
    User loginUser = userService.getByEmail(userDetails.getUsername());
    // Achievement 객체에 업적 ID와 로그인 유저 설정
    Achievement achievement = achievementService.findMyAchievement(id, loginUser.getNo());

    // 이미 취득한 업적이면 ignored 반환
    if (achievement.getProgress() == 100) {
      return "ignored";
    }
    
    // Achievement 객체에 현재 횟수(currentCount) 1증가
    achievement.setCurrentCount(achievement.getCurrentCount() + 1);
    // 현재 횟수(currentCount)와 총 횟수(maxCount) 비교하여 진행도(progress) 산출
    achievement.setProgress(achievement.getCurrentCount() * 100 / achievement.getMaxCount());

    // updateCount() 실행
    if (achievementService.updateCount(achievement)) {
      
      // 업적 취득 여부 확인
      if (achievement.getProgress() >= 100) {
        // progress가 100 이상이면 업적 취득 처리 후 acquired 반환
        achievementService.completeAchievement(achievement);
        return "acquired";
      }
      
      // updateCount 성공 후 업적 미취득 상태이면 success 반환
      return "success";
    }
    
    // updateCount 실패 시 failure 반환
    return "failure";
  }

}
