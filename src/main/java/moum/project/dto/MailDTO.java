package moum.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName    : moum.project.dto
 * fileName       : MailDTO
 * author         : narilee
 * date           : 24. 11. 4.
 * description    : 메일 전송을 위한 데이터 전송 객체(DTO)입니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 11. 4.        narilee       최초 생성
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailDTO {
  private String email;
}
