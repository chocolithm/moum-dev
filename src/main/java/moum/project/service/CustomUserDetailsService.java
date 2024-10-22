package moum.project.service;

import lombok.RequiredArgsConstructor;
import moum.project.dao.UserDao;
import moum.project.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * packageName    : moum.project.service
 * fileName       : CustomUserDetailsService
 * author         : narilee
 * date           : 24. 10. 22.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 10. 22.        narilee       최초 생성
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserDao userDao;

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
