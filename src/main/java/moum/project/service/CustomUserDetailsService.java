package moum.project.service;

import lombok.RequiredArgsConstructor;
import moum.project.dao.UserDao;
import moum.project.vo.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserDao userDao;

  /**
   * 주어진 이메일로 사용자를 조회하여 UserDetails 객체를 생성합니다.
   *
   * @param email 조회할 사용자의 이메일
   * @return 조회된 사용자 정보를 담은 UserDetails 객체
   * @throws UsernameNotFoundException 해당 이메일로 등록된 사용자를 찾을 수 없는 경우
   */
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    // MyBatis를 이용해 이메일로 사용자 조회
    User user = userDao.findByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException("User not found with email: " + email);
    }

    // UserDetails 객체 생성
    return org.springframework.security.core.userdetails.User.builder()
        .username(user.getEmail())
        .password(user.getPassword())  // 암호화된 비밀번호
        .roles("USER")  // 권한 설정 (필요에 따라)
        .build();
  }
}
