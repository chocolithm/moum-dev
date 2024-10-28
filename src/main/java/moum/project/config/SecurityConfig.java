package moum.project.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import moum.project.dao.UserDao;
import moum.project.vo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.io.IOException;
import java.time.LocalDateTime;

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
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Log4j2
public class SecurityConfig {

  private final UserDao userDao;

  /**
   * 사용자가 성공적으로 인증되면, 사용자의 이메일을 기반으로 DB에서 정보를 조회하여
   * 마지막 로그인 시간을 업데이트한 후, 홈 페이지로 리다이렉트합니다.
   *
   * @return AuthenticationSuccessHandler 인증 성공 처리 핸들러
   */
  @Bean
  public AuthenticationSuccessHandler authenticationSuccessHandler() {
    return new AuthenticationSuccessHandler() {

      /**
       * 사용자가 성공적으로 인증된 후 호출되는 메소드입니다.
       * 인증된 사용자의 이메일을 통해 DB에서 사용자 정보를 가져오고,
       * 로그인 성공 시 현재 시간으로 'last_login'을 업데이트합니다.
       *
       * @param request        HttpServletRequest 객체로 사용자의 요청 정보를 포함합니다.
       * @param response       HttpServletResponse 객체로 응답을 처리합니다.
       * @param authentication 인증된 사용자 정보가 포함된 Authentication 객체입니다.
       * @throws IOException      입출력 예외가 발생할 경우 던집니다.
       * @throws ServletException 서블릿 관련 예외가 발생할 경우 던집니다.
       */
      @Override
      public void onAuthenticationSuccess(HttpServletRequest request,
          HttpServletResponse response,
          Authentication authentication) throws IOException, ServletException {

        String username = authentication.getName();
        LocalDateTime now = LocalDateTime.now();

        try {
          User user = userDao.findByEmail(username);
          if (user != null) {
            userDao.updateLastLogin(user.getNo(), now);
            request.getSession().setAttribute("nickname", user.getNickname());
          }
        } catch (Exception e) {
          e.printStackTrace();
        }

        response.sendRedirect("/home");
      }
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
            .requestMatchers("/", "/home", "/css/**", "/js/**", "/images/**", "/error", "/user/**", "/auth/**").permitAll() // 모든 사용자 접근 가능
            .requestMatchers("/login").permitAll() // 로그인 페이지 접근 허용
            .requestMatchers("/admin/**").hasRole("ADMIN") // ADMIN 권한만 접근 가능
            .anyRequest().authenticated()
        )
        .formLogin((form) -> form
            .loginPage("/auth/form")
            .loginProcessingUrl("/auth/login")
            .usernameParameter("email")
            .passwordParameter("password")
            .defaultSuccessUrl("/home", true)
            .failureHandler(authenticationFailureHandler())
            .successHandler(authenticationSuccessHandler())
            .permitAll()
        )
        .csrf(csrf -> csrf
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .permitAll()
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
