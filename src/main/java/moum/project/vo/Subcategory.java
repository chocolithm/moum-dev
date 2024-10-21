package moum.project.vo;

import java.util.Objects;

import lombok.*;

@Data
public class Subcategory {
  @EqualsAndHashCode.Include int no;
  int maincategoryNo;
  String name;
}
