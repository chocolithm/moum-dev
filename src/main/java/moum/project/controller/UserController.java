package moum.project.controller;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import moum.project.config.CustomUserDetails;
import moum.project.dao.UserSnsDao;
import moum.project.service.AchievementService;
import moum.project.service.OAuth2Service;
import moum.project.service.StorageService;
import moum.project.service.UserService;
import moum.project.vo.Achievement;
import moum.project.vo.User;
import moum.project.vo.User_SNS;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * packageName    : moum.project.controller
 * fileName       : UserController
 * author         : narilee
 * date           : 24. 10. 21.
 * description    : UserController는 사용자 관련 요청을 처리하는 컨트롤러입니다,
 *                  이 컨트롤러는 사용자 가입 폼과 가입 처리, 회원정보 수정등을 위한 기능을 제공합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 10. 21.        narilee       최초 생성
 * 24. 10. 25.        narilee       회원 가입 성공시 알림 표시
 * 24. 10. 25.        narilee       회원 가입시 닉네임, 이메일 중복체크
 * 24. 10. 29.        narilee       닉네임, 비밀번호, 프로필 수정 기능 추가
 * 24. 10. 30.        narilee       회원 조회, 수정 페이지 분리
 * 24. 10. 31.        narilee       회원 삭제 기능 추가
 * 24. 11. 13.        narilee       SNS연동 기능 추가, 타유저 회원 정보 보기 기능 추가
 * 24. 11. 15.        narilee       탈퇴시 모든 연동된 SNS 연동 해제
 * 24. 11. 18.        narilee       회원 수정 페이지에서 연동기능 추가
 */
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Log4j2
public class UserController {

  private final UserService userService;
  private final UserSnsDao userSnsDao;
  private final StorageService storageService;
  private final PasswordEncoder passwordEncoder;
  private final AchievementService achievementService;
  private final OAuth2Service oAuth2Service;

  private String folderName = "user/profile/";

  /**
   * 이 메서드는 "/user/myInfo" URL로 들어오는 GET 요청을 처리합니다.
   *
   * @param userNo 회원 번호
   * @param userDetails 유저 정보를  보관한 userDetails 객체
   * @param model 뷰에 전달할 모델 객체
   * @return 본인이면 "myInfo", 타인이면 info/{userNo} 뷰 이름을 반환합니다.
   * @throws Exception 로그인되지 않은 경우 또는 회원 정보가 존재하지 않는 경우
   */
  @GetMapping({"/myInfo", "/info/{userNo}"})
  public String userInfo(@PathVariable(required = false) Integer userNo,
      @AuthenticationPrincipal CustomUserDetails userDetails,
      Model model) throws Exception {

    User viewUser;
    boolean isOwnProfile = false;

    // 본인 프로필인지 다른 사용자 프로필인지 확인
    if (userNo == null) {
      if (userDetails == null) {
        return "redirect:/home?login=true";
      }
      viewUser = userService.getByEmail(userDetails.getUsername());
      isOwnProfile = true;
    } else {
      viewUser = userService.get(userNo);
    }

    if (viewUser == null || viewUser.getEndDate() != null) {
      return "redirect:/home?notExist=true";
    }

    // 업적 목록 가져오기
    List<Achievement> achievementlist = achievementService.listByUser(viewUser.getNo());
    for (int i = 0; i < achievementlist.size(); i++) {
      Achievement achievement = achievementlist.get(i);
      if(achievement.getProgress() == 100) {
        achievementlist.remove(i--);
      }
    }

    model.addAttribute("achievementlist", achievementlist);
    model.addAttribute("user", viewUser);
    model.addAttribute("isOwnProfile", isOwnProfile);
    model.addAttribute("primaryAchievement", achievementService.findPrimary(viewUser.getNo()));

    return "user/myInfo";
  }

  /**
   * 이 메서드는 "/user/update" URL로 들어오는 GET 요청을 처리합니다.
   *
   * @param userDetails 유저 정보를  보관한 userDetails 객체
   * @param model 뷰에 전달할 모델 객체
   * @return "update" 뷰 이름을 반환합니다.
   * @throws Exception 로그인되지 않은 경우 또는 회원 정보가 존재하지 않는 경우
   */
  @GetMapping("/update")
  public String updateMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails, Model model)
      throws Exception {
    if (userDetails == null) {
      return "redirect:/home?login=true";
    }

    User user = userService.getByEmail(userDetails.getUsername());
    if (user == null) {
      return "redirect:/home?login=true";
    }

    // 사용자의 SNS 연동 정보 조회
    List<User_SNS> snsConnections = userSnsDao.findAllByUserId(user.getNo());
    List<String> connectedProviders = snsConnections.stream()
        .map(User_SNS::getProvider)
        .collect(Collectors.toList());

    model.addAttribute("user", user);
    model.addAttribute("connectedProviders", connectedProviders);
    model.addAttribute("listGetUserAchievement",
        achievementService.listUserGetAchievement(user.getNo()));
    return "user/update";
  }

  /**
   * 사용자 정보를 업데이트하는 메서드입니다.
   * 닉네임, 비밀번호, 프로필 사진을 수정할 수 있습니다.
   *
   * @param no 사용자 번호
   * @param nickname 새로운 닉네임 (선택)
   * @param password 새로운 비밀번호 (선택)
   * @param userDetails 현재 로그인된 사용자 정보
   * @param file 새로운 프로필 사진 파일 (선택)
   * @param redirectAttributes 리다이렉트 시 전달할 속성
   * @return 리다이렉트할 페이지 경로
   * @throws Exception 로그인되지 않은 경우 또는 회원 정보가 존재하지 않는 경우
   */
  @PostMapping("/update")
  public String updateMyInfo(
      @RequestParam("no") int no,
      @RequestParam("nickname") String nickname,
      @RequestParam(value = "password", required = false) String password,
      @RequestParam("user-achievement") String userAchievementId,
      @AuthenticationPrincipal CustomUserDetails userDetails,
      MultipartFile file,
      RedirectAttributes redirectAttributes,
      HttpSession session) throws Exception {

    if (userDetails == null) {
      redirectAttributes.addFlashAttribute("error", "로그인이 필요합니다.");
      return "redirect:/home?login=true";
    }

    User loginUser = userService.getByEmail(userDetails.getUsername());
    User old = userService.get(no);

    if (loginUser == null || loginUser.getNo() != no) {
      redirectAttributes.addFlashAttribute("error", "본인 정보만 수정할 수 있습니다.");
      return "redirect:/user/myInfo";
    }

    // 닉네임 수정
    if (nickname != null && !nickname.isEmpty()) {
      loginUser.setNickname(nickname);
    } else {
      loginUser.setNickname(old.getNickname());
    }

    // 비밀번호 수정
    if (password != null && !password.isEmpty()) {
      if (password.length() < 8) {
        redirectAttributes.addFlashAttribute("error", "비밀번호는 8자 이상이어야 합니다.");
        return "redirect:/user/update";
      }
      loginUser.setPassword(passwordEncoder.encode(password));
    }

    // 업적 수정
    achievementService.deletePrimaryAchievement(loginUser);
    Achievement achievement = new Achievement(userAchievementId);
    achievement.setUser(loginUser);
    achievementService.updatePrimaryAchievement(achievement);

    // 프로필 사진 처리
    if (file != null && file.getSize() > 0) {
      // 기존 프로필 사진 삭제
      if (old.getPhoto() != null) {
        storageService.delete(folderName + old.getPhoto());
      }

      // 새 파일 이름 생성
      String filename = UUID.randomUUID().toString();

      // 파일 업로드 옵션 설정
      HashMap<String, Object> options = new HashMap<>();
      options.put(StorageService.CONTENT_TYPE, file.getContentType());

      // 새 파일 업로드
      storageService.upload(folderName + filename, file.getInputStream(), options);

      // 사용자 프로필에 새로운 파일명 설정
      loginUser.setPhoto(filename);
    } else {
      // 파일이 없을 경우 기존 파일명 유지
      loginUser.setPhoto(old.getPhoto());
    }

    if (userService.update(loginUser)) {
      session.setAttribute("nickname", loginUser.getNickname());
      String successMessage = "정보가 성공적으로 수정되었습니다.";
      if (password != null && !password.isEmpty() && userDetails.getAttributes() != null) {
        successMessage += " 이제 이메일과 비밀번호로도 로그인할 수 있습니다.";
      }
      redirectAttributes.addFlashAttribute("message", successMessage);
      return "redirect:/user/myInfo";
    } else {
      redirectAttributes.addFlashAttribute("error", "정보 수정에 실패했습니다.");
      return "redirect:/user/update";
    }
  }

  /**
   * 회원가입 폼을 표시하는 메서드입니다.
   *
   * @param model 뷰에 전달할 모델 객체
   * @return 회원가입 페이지의 뷰 이름
   */
  @GetMapping("/signup")
  public String signupForm(Model model) {
    model.addAttribute("user", new User());
    return "user/signup";
  }

  /**
   * 회원가입 요청을 처리하는 메서드입니다.
   *
   * @param user 회원가입 폼에서 제출된 사용자 정보
   * @param redirectAttributes 리다이렉트 시 전달할 속성
   * @return 성공 시 홈페이지로, 실패 시 회원가입 페이지로 리다이렉트
   */
  @PostMapping("/signup")
  public String signupSubmit(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
    try {
      userService.add(user);
      return "redirect:/home?openLoginModal=true";
    } catch (Exception e) {
      return "redirect:/user/signup?signupError=true";
    }
  }

  /**
   * 회원 탈퇴를 처리하는 메서드입니다.
   *
   * @param no 탈퇴할 회원의 번호
   * @param userDetails 현재 로그인한 사용자의 정보
   * @param redirectAttributes 리다이렉트 시 전달할 메시지
   * @return 홈페이지로 리다이렉트
   * @throws Exception 권한이 없거나 처리 중 오류 발생시
   */
  @PostMapping("/delete")
  public String deleteUser(
      @RequestParam("no") int no,
      @AuthenticationPrincipal CustomUserDetails userDetails,
      RedirectAttributes redirectAttributes,
      HttpSession session) throws Exception {

    if (userDetails == null) {
      redirectAttributes.addFlashAttribute("error", "로그인이 필요합니다.");
      return "redirect:/home?login=true";
    }

    try {
      User loginUser = userService.getByEmail(userDetails.getUsername());
      if (loginUser == null || loginUser.getNo() != no) {
        redirectAttributes.addFlashAttribute("error", "본인 계정만 탈퇴할 수 있습니다.");
        return "redirect:/user/myInfo";
      }

      // 프로필 사진 삭제
      if (loginUser.getPhoto() != null && !loginUser.getPhoto().isEmpty()) {
        storageService.delete(folderName + loginUser.getPhoto());
      }

      // SNS 연동 해제
      oAuth2Service.unlinkAllSnsConnections(loginUser.getNo());

      // SNS 연동 정보 삭제
      userSnsDao.deleteByUserId(loginUser.getNo());

      // 회원 정보 익명화
      loginUser.setPhoto(null);
      loginUser.setEmail("deleted_" + System.currentTimeMillis());
      loginUser.setNickname("탈퇴한 회원_" + System.currentTimeMillis());
      loginUser.setPassword(passwordEncoder.encode("deleted"));
      loginUser.setEndDate(LocalDate.now());
      loginUser.setSnsId(null);

      // 회원 정보 업데이트
      userService.update(loginUser);

      // 세션 무효화
      session.invalidate();

      redirectAttributes.addFlashAttribute("message", "회원 탈퇴가 완료되었습니다.");
      return "redirect:/home";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error", "회원 탈퇴 처리 중 오류가 발생했습니다.");
      return "redirect:/user/myInfo";
    }
  }

  /**
   * 닉네임과 이메일의 중복 여부를 확인하는 메서드입니다.
   *
   * @param nickname 중복 확인할 닉네임 (선택)
   * @param email 중복 확인할 이메일 (선택)
   * @return 중복 여부를 담은 Map (키: isNicknameTaken/isEmailTaken, 값: boolean)
   * @throws Exception 서버 오류 발생 시
   */
  @GetMapping("/check-duplicate")
  @ResponseBody
  public Map<String, Boolean> checkDuplicate(
      @RequestParam(required = false) String nickname,
      @RequestParam(required = false) String email) throws Exception {
    Map<String, Boolean> response = new HashMap<>();
    if (nickname != null && !nickname.isEmpty()) {
      response.put("isNicknameTaken", userService.isNicknameTaken(nickname));
    }
    if (email != null && !email.isEmpty()) {
      response.put("isEmailTaken", userService.isEmailTaken(email));
    }
    return response;
  }
}
