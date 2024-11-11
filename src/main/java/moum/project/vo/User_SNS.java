package moum.project.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName    : moum.project.vo
 * fileName       : User_SNS
 * author         : narilee
 * date           : 24. 11. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 11. 11.        narilee       최초 생성
 */
@Data
@NoArgsConstructor
public class User_SNS {

  private int snsAccountId;

  private String provider;

  private String providerUserId;

  private int userId;

}
