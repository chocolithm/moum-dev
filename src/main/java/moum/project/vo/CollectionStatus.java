package moum.project.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CollectionStatus {
  @EqualsAndHashCode.Include
  private int no;
  private String name;
}
