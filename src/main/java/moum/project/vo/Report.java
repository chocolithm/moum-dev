package moum.project.vo;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Report {
  @EqualsAndHashCode.Include private int no;
  private User user; // 신고자
  private String reportContent; // 신고내용
  private LocalDateTime reportDate; // 신고일시

  private ReportCategory reportCategory; // 신고항목 (스팸인지 욕설인지 등등)

  private ReportResultCategory resultCategory; // 처리결과유형
  private String resultContent; // 처리결과내용

}
