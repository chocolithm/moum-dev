package moum.project.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moum.project.dao.UserDao;
import moum.project.vo.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : moum.project.service
 * fileName       : DefaultUserService
 * author         : narilee
 * date           : 24. 10. 21.
 * description    : DefaultUserService는 UserService 인터페이스의 구현체입니다.
 *                  이 클래스는 사용자 관리(CRUD)와 관련된 기능 및 사용자 인증 기능을 포함합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 10. 21.        narilee       최초 생성
 */
@Component
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    /**
     * 새로운 사용자를 시스템에 추가합니다.
     *
     * @param user 추가할 사용자 정보
     * @throws Exception 사용자 추가 중 발생할 수 있는 예외
     */
    @Override
    @Transactional
    public void add(User user) throws Exception {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      userDao.insert(user);
    }

    /**
     * 모든 사용자 목록을 반환합니다.
     *
     * @return 전체 사용자 목록
     * @throws Exception 사용자 목록 조회 중 발생할 수 있는 예외
     */
    @Override
    public List<User> list() throws Exception {
      return userDao.list();
    }

    /**
     * 지정된 사용자 번호에 해당하는 사용자 정보를 반환합니다.
     *
     * @param userNo 조회할 사용자의 번호
     * @return 조회된 사용자 정보
     * @throws Exception 사용자 정보 조회 중 발생할 수 있는 예외
     */
    @Override
    public User get(int userNo) throws Exception {
      return userDao.findBy(userNo);
    }

    /**
     * 사용자 정보를 업데이트합니다.
     *
     * @param user 업데이트할 사용자 정보
     * @return 업데이트 성공 여부
     * @throws Exception 사용자 정보 업데이트 중 발생할 수 있는 예외
     */
    @Override
    @Transactional
    public boolean update(User user) throws Exception {
      return userDao.update(user);
    }

    /**
     * 지정된 사용자 번호에 해당하는 사용자를 삭제합니다.
     *
     * @param userNo 삭제할 사용자의 번호
     * @return 삭제 성공 여부
     * @throws Exception 사용자 삭제 중 발생할 수 있는 예외
     */
    @Override
    public boolean delete(int userNo) throws Exception {
      return userDao.delete(userNo);
    }

    /**
     * 주어진 이메일과 비밀번호로 사용자 인증을 수행합니다.
     *
     * @param email    사용자 이메일
     * @param password 사용자 비밀번호
     * @return 인증된 사용자 정보, 인증 실패 시 null
     * @throws Exception 사용자 인증 중 발생할 수 있는 예외
     */
    @Override
    public User exists(String email, String password) throws Exception {
      User user = userDao.findByEmail(email);
      if (user != null && passwordEncoder.matches(password, user.getPassword())) {
        return user;
      }
      return null;
    }

  @Override
  public User getByEmail(String email) throws Exception {
    return userDao.findByEmail(email);
  }
}
