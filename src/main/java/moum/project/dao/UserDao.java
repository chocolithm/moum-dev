package moum.project.dao;


import java.time.LocalDateTime;
import java.util.List;
import moum.project.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * packageName    : moum.project.Service
 * fileName       : UserDao
 * author         : narilee
 * date           : 24. 10. 15.
 * description    :사용자 데이터 처리를 위한 MyBatis Mapper 인터페이스
 *                 사용자의 CRUD 작업을 담당합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 10. 15.        narilee       최초 생성
 * 24. 10. 24.        narilee       로그인 시간 업데이트를 위한 updateLastLogin 추가
 * 24. 10. 25.        narilee       회원 가입시 닉네임, 이메일 중복체크
 */
@Mapper
public interface UserDao {

    /**
     * 새로운 사용자를 데이터베이스에 등록합니다.
     *
     * @param user 등록할 사용자 정보
     * @throws Exception 데이터베이스 작업 중 오류 발생시
     */
    void insert(User user) throws Exception;

    /**
     * 모든 사용자 목록을 조회합니다.
     *
     * @return 전체 사용자 목록
     * @throws Exception 데이터베이스 작업 중 오류 발생시
     */
    List<User> list() throws Exception;

    /**
     * 사용자 번호로 특정 사용자를 조회합니다.
     *
     * @param no 조회할 사용자의 번호
     * @return 조회된 사용자 정보, 없을 경우 null
     * @throws Exception 데이터베이스 작업 중 오류 발생시
     */
    User findBy(int no) throws Exception;

    /**
     * 이메일과 비밀번호로 사용자를 조회합니다.
     *
     * @param email 사용자 이메일
     * @param password 사용자 비밀번호
     * @return 조회된 사용자 정보, 없을 경우 null
     * @throws Exception 데이터베이스 작업 중 오류 발생시
     */
    User findByEmailAndPassword(@Param("email") String email, @Param("password") String password) throws Exception;

    /**
     * 사용자 정보를 수정합니다.
     *
     * @param user 수정할 사용자 정보
     * @return 수정 성공 여부
     * @throws Exception 데이터베이스 작업 중 오류 발생시
     */
    boolean update(User user) throws Exception;

    /**
     * 특정 사용자를 삭제합니다.
     *
     * @param no 삭제할 사용자의 번호
     * @return 삭제 성공 여부
     * @throws Exception 데이터베이스 작업 중 오류 발생시
     */
    boolean delete(int no) throws Exception;

    /**
     * 이메일로 사용자를 조회합니다.
     *
     * @param email 조회할 사용자의 이메일
     * @return 조회된 사용자 정보, 없을 경우 null
     */
    User findByEmail(String email);

    /**
     * 닉네임으로 사용자를 조회합니다.
     *
     * @param nickname 조회할 사용자의 닉네임
     * @return 조회된 사용자 정보, 없을 경우 null
     */
    User findByNickname(String nickname);

    /**
     * 사용자의 마지막 로그인 시간을 업데이트합니다.
     *
     * @param userId 사용자 ID
     * @param lastLoginTime 마지막 로그인 시간
     */
    void updateLastLogin(@Param("userId") int userId, @Param("lastLoginTime")
    LocalDateTime lastLoginTime);

    List<User> listByPage(
        @Param("pageNo") int pageNo,
        @Param("pageCount") int pageCount) throws Exception;

    int count() throws Exception;

}
