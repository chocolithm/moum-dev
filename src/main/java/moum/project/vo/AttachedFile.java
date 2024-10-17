package moum.project.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class AttachedFile {
  private int no;
  private String filepath;

  @Override
  public String toString() {
    return "AttachedFile{" +
        "no=" + no +
        ", filepath='" + filepath + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    AttachedFile that = (AttachedFile) o;
    return no == that.no;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(no);
  }

}
