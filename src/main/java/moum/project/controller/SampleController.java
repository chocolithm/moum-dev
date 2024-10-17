package moum.project.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : moum.project.controller
 * fileName       : SampleController
 * author         : narilee
 * date           : 24. 10. 15.
 * description    : 이 클래스는 웹 애플리케이션에서 접근 수준을 처리하는 컨트롤러입니다.
 *                  모든 사용자, 회원, 관리자를 위한 다양한 매핑을 보여줍니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 10. 15.        narilee       최초 생성
 */
@Controller
@Log4j2
@RequestMapping("/sample")
public class SampleController {

  // 로그인을 하지 않은 사용자도 접근 할수 있는 경로
  @GetMapping("/all")
  public String exAll(){
    log.info("exAll..........");
    return "sample/all";
  }

  // 로그인한 사용자만이 접근할 수 있는 경로
  @GetMapping("/member")
  public void exMember(){
    log.info("exMember...........");
  }

  // 관리자 권한이 있는 사용자만이 접근할 수 있는 경로
  @GetMapping("/admin")
  public void exAdmin(){
    log.info("exAdmin...........");
  }
}
