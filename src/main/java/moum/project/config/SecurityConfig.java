package moum.project.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
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
 */
@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfig {

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
            .requestMatchers("/", "/home", "/css/**", "/js/**", "/images/**", "/error", "/user/**", "/auth/**").permitAll()
            .requestMatchers("/login").permitAll() // 로그인 페이지는 접근 가능해야 함
            .anyRequest().authenticated()
        )
        .formLogin((form) -> form
            .loginPage("/login")
            .loginProcessingUrl("/auth/login")
            .usernameParameter("email")
            .passwordParameter("password")
            .defaultSuccessUrl("/myHome", true)
            .failureUrl("/login?error=true")
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
