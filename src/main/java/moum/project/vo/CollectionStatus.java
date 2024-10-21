package moum.project.vo;

import lombok.*;

@Data
public class CollectionStatus {
  @EqualsAndHashCode.Include private int no;
  private String name;
}
