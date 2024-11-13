package moum.project.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName    : moum.project.vo
 * fileName       : User_SNS
 * author         : narilee
 * date           : 24. 11. 11.
 * description    : 사용자의 SNS연동을 나타내는 클래스입니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 11. 11.        narilee       최초 생성
 */
@Data
@NoArgsConstructor
public class User_SNS {

  /**
   * 간편 로그인을 등록한 SNS 고유 식별자 번호입니다.
   */
  private int snsAccountId;

  /**
   * 간편 로그인을 등록한 SNS 이름입니다.
   */
  private String provider;

  /**
   * 간편 로그인을 등록한 SNS에서 제공한 식별자입니다.
   */
  private String providerUserId;

  /**
   * 사용자의 고유 식별자입니다.
   */
  private int userId;

}
