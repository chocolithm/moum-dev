package moum.project.service;

import lombok.RequiredArgsConstructor;
import moum.project.vo.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

/**
 * packageName    : moum.project.service
 * fileName       : getAuthenticatedUser
 * author         : narilee
 * date           : 24. 11. 13.
 * description    : 사용자 인증 관련 유틸리티를 제공하는 지원 클래스입니다.
 *                  Spring Security의 Authentication 객체를 처리하고
 *                  인증된 사용자 정보를 조회하는 기능을 제공합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 11. 13.        narilee       최초 생성
 */
@Component
@RequiredArgsConstructor
public class AuthenticationSupport {

  private final UserService userService;

  /**
   * 현재 인증된 사용자의 정보를 조회합니다.
   * OAuth2 인증과 일반 폼 로그인 모두를 지원합니다.
   *
   * @param authentication Spring Security의 Authentication 객체
   * @return 인증된 사용자의 User 객체. 인증되지 않았거나 오류 발생 시 null 반환
   *
   * @see Authentication
   * @see OAuth2User
   * @see UserDetails
   * @see User
   */
  public User getAuthenticatedUser(Authentication authentication) {
    try {
      if (authentication == null || !authentication.isAuthenticated()) {
        return null;
      }

      String email = null;
      Object principal = authentication.getPrincipal();

      if (principal instanceof OAuth2User) {
        email = ((OAuth2User) principal).getAttribute("email");
      } else if (principal instanceof UserDetails) {
        email = ((UserDetails) principal).getUsername();
      }

      if (email != null) {
        return userService.getByEmail(email);
      }

      return null;
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 현재 인증이 OAuth2를 통해 이루어졌는지 확인합니다.
   *
   * @param authentication Spring Security의 Authentication 객체
   * @return OAuth2 인증인 경우 true, 그렇지 않은 경우 false
   *
   * @see Authentication
   * @see OAuth2User
   */
  public boolean isOAuth2Authentication(Authentication authentication) {
    return authentication != null &&
        authentication.getPrincipal() instanceof OAuth2User;
  }
}
