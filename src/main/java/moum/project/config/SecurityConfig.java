package moum.project.config;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import moum.project.dao.UserDao;
import moum.project.service.CustomOAuth2UserService;
import moum.project.service.CustomUserDetailsService;
import moum.project.vo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * packageName    : moum.project.config
 * fileName       : SecurityConfig
 * author         : narilee
 * date           : 24. 10. 15.
 * description    : 애플리케이션의 보안 설정을 정의하는 구성 클래스입니다.
 *                  이 클래스는 Spring Security 관련 설정을 담당합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 10. 15.        narilee       최초 생성
 * 24. 10. 21.        narilee       /myHome, /auth, /user, /achievement 예외처리
 * 24. 10. 22.        narilee       로그인 구현후 예외처리 변경
 * 24. 10. 24.        narilee       로그인, 로그아웃 이관, 로그인시 시간 저장 추가
 * 24. 10. 25.        narilee       로그인시 세션에 닉네임 저장
 * 24. 11. 13.        narilee       구글 로그인 구현
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Log4j2
public class SecurityConfig {

  private final UserDao userDao;
  private final CustomOAuth2UserService customOAuth2UserService;
  private final CustomUserDetailsService customUserDetailsService;

  /**
   * 사용자가 성공적으로 인증되면, 사용자의 이메일을 기반으로 DB에서 정보를 조회하여
   * 마지막 로그인 시간을 업데이트한 후, 홈 페이지로 리다이렉트합니다.
   *
   * @return AuthenticationSuccessHandler 인증 성공 처리 핸들러
   */
  @Bean
  public AuthenticationSuccessHandler authenticationSuccessHandler() {
    return (request, response, authentication) -> {
      CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
      LocalDateTime lastLogin = null;

      try {
        User user = userDao.findByEmail(userDetails.getUsername());
        lastLogin = user.getLastLogin();
        if (user != null) {
          userDao.updateLastLogin(user.getNo(), LocalDateTime.now());
          request.getSession().setAttribute("nickname", userDetails.getNickname());
        }
      } catch (Exception e) {
        log.error("Login success handler error", e);
      }

      response.sendRedirect("/home?lastLogin=" + lastLogin);
    };
  }

  /**
   * 사용자가 로그인 인증이 실패 후 호출되는 메소드입니다.
   *
   * @return /home?error=true를 통한 로그인 실패 알림
   */
  @Bean
  public AuthenticationFailureHandler authenticationFailureHandler() {
    return (request, response, exception) -> {
      response.sendRedirect("/home?error=true");
    };
  }

  /**
   * Spring Security 필터 체인을 구성합니다.
   *
   * @param http Http Security 객체
   * @return 구성된 SecurityFilterChain
   * @throws Exception 보안 구성 중 발생할 수 있는 예외
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((requests) -> requests
            .requestMatchers("/", "/home", "/css/**", "/js/**", "/images/**", "/error",
                "/user/signup", "/user/check-duplicate", "/auth/**", "/emailCheck").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/home?login=true")
            .loginProcessingUrl("/auth/login")
            .usernameParameter("email")
            .passwordParameter("password")
            .successHandler(authenticationSuccessHandler())
            .failureHandler(authenticationFailureHandler())
            .permitAll()
        )
        .oauth2Login(oauth2 -> oauth2
            .loginPage("/home?login=true")
            .userInfoEndpoint(userInfo -> userInfo
                .userService(customOAuth2UserService)
            )
            .successHandler((request, response, authentication) -> {
              CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

              try {
                User user = userDao.findByEmail(userDetails.getUsername());
                if (user != null) {
                  userDao.updateLastLogin(user.getNo(), LocalDateTime.now());
                  request.getSession().setAttribute("nickname", userDetails.getNickname());
                }

                // OAuth2 로그인은 myhome으로 리다이렉트
                response.sendRedirect("/home");
              } catch (Exception e) {
                log.error("OAuth2 login success handler error", e);
                response.sendRedirect("/home?error=true");
              }
            })
            .failureUrl("/home?error=true")
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .permitAll()
        )
        .csrf(csrf -> csrf
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        )
        .headers(headers -> headers
            .httpStrictTransportSecurity(hsts -> hsts
                .includeSubDomains(false)
                .maxAgeInSeconds(31536000)
            )
        );

    return http.build();
  }

  /**
   * 비밀번호를 BCrypt형식으로 암호화하는 메서드입니다.
   *
   * @return 암호화된 패스워드를 반환합니다.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
