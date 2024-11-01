package moum.project.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class ReportResultCategory {
  @EqualsAndHashCode.Include private int no;
  private String name; // 신고처리유형명
}
