package moum.project.controller;

import lombok.RequiredArgsConstructor;
import moum.project.dto.MailDTO;
import moum.project.service.MailService;
import moum.project.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : moum.project.controller
 * fileName       : MailController
 * author         : narilee
 * date           : 24. 11. 4.
 * description    : 이메일 인증 관련 요청을 처리하는 REST 컨트롤러입니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 11. 4.        narilee       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class MailController {

  /**
   * 메일 발송 서비스
   */
  private final MailService mailService;

  /**
   * 사용자 관련 서비스
   */
  private final UserService userService;

  /**
   * 이메일 중복 확인 및 인증 코드 발송을 처리합니다.
   *
   * @param mailDTO 이메일 정보를 담고 있는 DTO
   * @return 중복되지 않은 경우 생성된 인증 코드를 반환
   * @throws Exception 메일 발송 실패 등의 예외 발생 시
   */
  @ResponseBody
  @PostMapping("/emailCheck")
  public String emailCheck(@RequestBody MailDTO mailDTO) throws Exception {
    String email = mailDTO.getEmail();

    // 이메일 중복 확인
    if (userService.isEmailTaken(email)) {
      return "duplicate";
    }

    // 중복이 아닐 경우 인증 코드 발송
    String authCode = mailService.sendSimpleMessage(email);
    return authCode;
  }

  /**
   * 이메일 중복 확인 및 인증 코드 발송을 처리합니다.
   *
   * @param mailDTO 이메일 정보를 담고 있는 DTO
   * @return 중복되는 경우 생성된 인증 코드를 반환
   * @throws Exception 메일 발송 실패 등의 예외 발생 시
   */
  @ResponseBody
  @PostMapping("/findEmail")
  public String findEmail(@RequestBody MailDTO mailDTO) throws Exception {
    String email = mailDTO.getEmail();

    if (userService.isEmailTaken(email)) {
      String authCode = mailService.sendSimpleMessage(email);
      return authCode;
    }

    return "존재하지 않는 이메일입니다.";
  }
}
