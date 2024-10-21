package moum.project.vo;

import java.util.Objects;

import lombok.*;

@Data
public class Maincategory {
  @EqualsAndHashCode.Include int no;
  String name;
}
