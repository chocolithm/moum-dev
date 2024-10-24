package moum.project.service;

import java.time.LocalDateTime;
import java.util.List;
import moum.project.vo.User;

/**
 * packageName    : moum.project.service
 * fileName       : UserService
 * author         : narilee
 * date           : 24. 10. 21.
 * description    : UserService 인터페이스는 사용자 관리에 관련된 핵심 비즈니스 로직을 정의합니다.
 *                  이 인터페이스는 사용자 추가, 조회, 수정, 삭제 및 인증 기능을 제공합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 10. 21.        narilee       최초 생성
 */
  public interface UserService {

  /**
   * 새로운 사용자를 추가합니다.
   *
   * @param user 추가할 사용자 정보
   * @throws Exception 사용자 추가 중 발생할 수 있는 예외
   */
  void add(User user) throws Exception;

  /**
   * 모든 사용자 목록을 반환합니다.
   *
   * @return 전체 사용자 목록
   * @throws Exception 사용자 목록 조회 중 발생할 수 있는 예외
   */
  List<User> list() throws Exception;

  /**
   * 지정된 사용자 번호에 해당하는 사용자 정보를 반환합니다.
   *
   * @param userNo 조회할 사용자의 번호
   * @return 조회된 사용자 정보
   * @throws Exception 사용자 정보 조회 중 발생할 수 있는 예외
   */
  User get(int userNo) throws Exception;

  /**
   * 사용자 정보를 업데이트합니다.
   *
   * @param user 업데이트할 사용자 정보
   * @return 업데이트 성공 여부
   * @throws Exception 사용자 정보 업데이트 중 발생할 수 있는 예외
   */
  boolean update(User user) throws Exception;

  /**
   * 지정된 사용자 번호에 해당하는 사용자를 삭제합니다.
   *
   * @param userNo 삭제할 사용자의 번호
   * @return 삭제 성공 여부
   * @throws Exception 사용자 삭제 중 발생할 수 있는 예외
   */
  boolean delete(int userNo) throws Exception;

  /**
   * 주어진 이메일과 비밀번호로 사용자 인증을 수행합니다.
   *
   * @param email 사용자 이메일
   * @param password 사용자 비밀번호
   * @return 인증된 사용자 정보, 인증 실패 시 null
   * @throws Exception 사용자 인증 중 발생할 수 있는 예외
   */
  User exists(String email, String password) throws Exception;

  User getByEmail(String email) throws Exception;
}
