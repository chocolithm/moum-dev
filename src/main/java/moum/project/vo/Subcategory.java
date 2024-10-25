package moum.project.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Subcategory {
  @EqualsAndHashCode.Include private int no;
  private String name;
}
