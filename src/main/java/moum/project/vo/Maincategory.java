package moum.project.vo;

import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Maincategory {
  @EqualsAndHashCode.Include
  int no;
  String name;
}
