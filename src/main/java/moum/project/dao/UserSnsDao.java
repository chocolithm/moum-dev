package moum.project.dao;

import moum.project.vo.User_SNS;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * packageName    : moum.project.dao
 * fileName       : UserSnsDao
 * author         : narilee
 * date           : 24. 11. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 11. 11.        narilee       최초 생성
 */
@Mapper
public interface UserSnsDao {
  User_SNS selectUser_SNSByProviderAndUserId(@Param("provider") String provider,
      @Param("providerUserId") String providerUserId);

  int insertUser_SNS(User_SNS userSNS);
}
