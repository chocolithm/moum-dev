package moum.project.vo;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Subcategory {
  int no;
  int maincategoryNo;
  String name;

  @Override
  public String toString() {
    return "Subcategory{" +
        "no=" + no +
        ", maincategoryNo=" + maincategoryNo +
        ", name='" + name + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Subcategory that = (Subcategory) o;
    return no == that.no;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(no);
  }
}
