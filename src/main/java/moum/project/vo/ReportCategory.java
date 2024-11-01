package moum.project.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class ReportCategory {
  @EqualsAndHashCode.Include private int no;
  private String name; // 신고항목 내용
}
