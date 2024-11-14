package moum.project.dao;

import moum.project.vo.User_SNS;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * packageName    : moum.project.dao
 * fileName       : UserSnsDao
 * author         : narilee
 * date           : 24. 11. 13.
 * description    : UserSnsDao는 user_sns 테이블과의 데이터베이스 상호작용을 담당합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 11. 13.        narilee       최초 생성
 * 24. 11. 14.        narilee       user정보로 sns연동유무 확인 추가
 */
@Mapper
public interface UserSnsDao {
  /**
   * provider와 providerUserId로 특정 SNS 계정을 조회합니다.
   *
   * @param provider         SNS 제공자 (예: google)
   * @param providerUserId   SNS 제공자에서 제공한 사용자 ID
   * @return 조회된 User_SNS 객체, 없을 경우 null
   */
  User_SNS selectUser_SNSByProviderAndUserId(
      @Param("provider") String provider,
      @Param("providerUserId") String providerUserId);

  /**
   * 새로운 SNS 계정을 삽입합니다.
   *
   * @param userSns 삽입할 User_SNS 객체
   */
  void insertUser_SNS(User_SNS userSns);

  /**
   * 특정 사용자의 모든 SNS 연동 정보를 조회합니다.
   *
   * @param userId 사용자 ID
   * @return SNS 연동 정보 목록
   */
  List<User_SNS> findAllByUserId(int userId);

  /**
   * 특정 사용자의 SNS 연동 정보를 삭제합니다.
   *
   * @param userId 삭제할 사용자의 ID
   */
  void deleteByUserId(int userId);
}
