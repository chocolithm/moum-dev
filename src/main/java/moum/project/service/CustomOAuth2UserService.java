package moum.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import moum.project.dao.UserDao;
import moum.project.dao.UserSnsDao;
import moum.project.vo.User;
import moum.project.vo.User_SNS;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * packageName    : moum.project.service
 * fileName       : CustomOAuth2UserService
 * author         : narilee
 * date           : 24. 11. 13.
 * description    : OAuth2 사용자 인증을 처리하는 커스텀 서비스 클래스입니다.
 *                  소셜 로그인(OAuth2) 인증 처리 및 사용자 정보 관리를 담당합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 11. 13.        narilee       최초 생성
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {


  private final UserDao userDao;
  private final UserSnsDao userSnsDao;

  /**
   * OAuth2 인증 요청을 처리하고 사용자 정보를 로드합니다.
   * 새로운 사용자인 경우 회원가입을 진행하고, 기존 사용자인 경우 정보를 업데이트합니다.
   *
   * @param userRequest OAuth2 사용자 인증 요청 객체
   * @return OAuth2User 인증된 사용자 정보를 담고 있는 OAuth2User 객체
   * @throws OAuth2AuthenticationException 인증에 문제가 생겼거나 유저가 없을 경우 발생
   */
  @Override
  @Transactional
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = delegate.loadUser(userRequest);

    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    String userNameAttributeName = userRequest.getClientRegistration()
        .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

    Map<String, Object> attributes = oAuth2User.getAttributes();
    Map<String, Object> modifiedAttributes = new HashMap<>(attributes);

    String email = (String) attributes.get("email");
    String name = (String) attributes.get("name");
    String providerId = (String) attributes.get(userNameAttributeName);

    try {
      User_SNS userSns = userSnsDao.selectUser_SNSByProviderAndUserId(registrationId, providerId);
      User user;

      if (userSns == null) {
        user = userDao.findByEmail(email);

        if (user == null) {
          user = new User();
          user.setEmail(email);
          user.setNickname(name);
          user.setPassword(UUID.randomUUID().toString());
          user.setAdmin(false);
          user.setStartDate(LocalDateTime.now());
          userDao.insert(user);
        }

        User_SNS newUserSns = new User_SNS();
        newUserSns.setProvider(registrationId);
        newUserSns.setProviderUserId(providerId);
        newUserSns.setUserId(user.getNo());
        userSnsDao.insertUser_SNS(newUserSns);
      } else {
        user = userDao.findBy(userSns.getUserId());
        if (user == null) {
          throw new OAuth2AuthenticationException(
              new OAuth2Error("user_not_found", "User not found for SNS account", null));
        }
      }

      // Add user information to attributes
      modifiedAttributes.put("user_no", user.getNo());
      modifiedAttributes.put("user_email", user.getEmail());
      modifiedAttributes.put("user_nickname", user.getNickname());

      return new DefaultOAuth2User(
          Collections.singleton(new SimpleGrantedAuthority(user.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER")),
          modifiedAttributes,
          userNameAttributeName
      );

    } catch (Exception e) {
      log.error("OAuth2 authentication error", e);
      throw new OAuth2AuthenticationException(
          new OAuth2Error("authentication_error", e.getMessage(), null));
    }
  }
}
