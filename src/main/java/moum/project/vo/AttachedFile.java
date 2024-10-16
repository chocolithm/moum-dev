package moum.project.vo;

import java.util.Objects;

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

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getFilepath() {
    return filepath;
  }

  public void setFilepath(String filepath) {
    this.filepath = filepath;
  }
}
