package moum.project.service;

import lombok.RequiredArgsConstructor;
import moum.project.config.CustomUserDetails;
import moum.project.dao.UserDao;
import moum.project.vo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : moum.project.service
 * fileName       : CustomUserDetailsService
 * author         : narilee
 * date           : 24. 10. 22.
 * description    : 사용자 인증을 위한 커스텀 UserDetailsService 구현체
 *                  Spring Security에서 사용자 정보를 로드하는 역할을 담당합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 10. 22.        narilee       최초 생성
 * 24. 10. 25.        narilee       닉네임 추가
 * 24. 10. 28.        narilee       권한 추가
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserDao userDao;

  @Override
  public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userDao.findByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException("이메일로 사용자를 찾을 수 없습니다: " + email);
    }

    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    if (user.isAdmin()) {
      authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    } else {
      authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    return new CustomUserDetails(
        user.getEmail(),
        user.getPassword(),
        user.getNickname(),
        authorities
    );
  }
}
