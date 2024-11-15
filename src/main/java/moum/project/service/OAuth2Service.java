package moum.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import moum.project.dao.UserSnsDao;
import moum.project.vo.User_SNS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * packageName    : moum.project.service
 * fileName       : OAuth2Service
 * author         : narilee
 * date           : 24. 11. 11.
 * description    : 간편로그인 관련 서비스를 처리하는 클래스입니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 11. 11.        narilee       최초 생성
 * 24. 11. 15.        narilee       sns 연동 해제 기능 추가
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class OAuth2Service {

  private final UserService userService;
  private final RestTemplate restTemplate = new RestTemplate();
  private final UserSnsDao userSnsDao;

  @Value("${kakao.client-admin}")
  private String kakaoAdminId;

  /**
   * 사용자의 모든 SNS  연동을 해제합니다.
   *
   * @param userId 사용자 ID
   */
  public void unlinkAllSnsConnections(int userId) {
    List<User_SNS> snsConnections = userSnsDao.findAllByUserId(userId);

    for (User_SNS connection : snsConnections) {
      try {
        switch (connection.getProvider()) {
          case "google":
              unlinkGoogle(connection.getProviderUserId());
              break;
          case "naver":
              unlinkNaver(connection.getProviderUserId());
              break;
          case "kakao":
              unlinkKakao(connection.getProviderUserId());
              break;
        }
      }catch (Exception e) {
        log.error(userId + "회원의 " + connection.getProvider() + "연동 해제에 실패했습니다.", e);
      }
    }
  }

  /**
   * 구글 연동을 해제합니다.
   *
   * @param providerUserId 연동된 sns 회원 번호
   */
  private void unlinkGoogle(String providerUserId) {
    String url = "https://oauth2.googleapis.com/revoke";
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("token", providerUserId);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
    restTemplate.postForObject(url, request, String.class);
  }

  /**
   * Naver 연동을 해제합니다.
   *
   * @param providerUserId 연동된 sns 회원 번호
   */
  private void unlinkNaver(String providerUserId) {
    String url = "https://nid.naver.com/oauth2.0/token";
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("client_id", "${naver.client.id}");
    params.add("client_secret", "${naver.client.secret}");
    params.add("access_token", providerUserId);
    params.add("grant_type", "delete");
    params.add("service_provider", "NAVER");

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
    restTemplate.postForObject(url, request, String.class);
  }

  /**
   * 카카오 연동을 해제합니다.
   *
   * @param providerUserId 연동된 sns 회원 번호
   */
  private void unlinkKakao(String providerUserId) {
    String url = "https://kapi.kakao.com/v1/user/unlink";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.set("Authorization", "KakaoAK " + kakaoAdminId);  // Admin 키 사용

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("target_id_type", "user_id");
    params.add("target_id", providerUserId);  // 카카오 사용자 ID

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

    try {
      ResponseEntity<String> response = restTemplate.exchange(
          url,
          HttpMethod.POST,
          request,
          String.class
      );

      if (!response.getStatusCode().is2xxSuccessful()) {
        log.error("카카오 연동해제 실패 이유: {}", response.getStatusCode());
        throw new RuntimeException("카카오 연동해제 실패 이유: " + response.getStatusCode());
      }
    } catch (HttpClientErrorException e) {
      log.error("카카오 연동해제 실패 이유: {}", e.getStatusCode());
      throw e;
    }
  }
}
