package moum.project.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Collection implements Serializable {

  private static final long serialVersionUID = 1L;

  private int no;
  private int userNo;
  private int maincategoryNo;
  private int subcategoryNo;
  private String name;
  private int statusNo;
  private java.sql.Date purchaseDate;
  private String purchasePlace;
  private int price;
  private String storageLocation;
  private java.util.Date postDate;
  private List<AttachedFile> attachedFiles;

  public Collection() {
  }

  public Collection(int no) {
    this.no = no;
  }

  @Override
  public String toString() {
    return "Collection{" +
        "no=" + no +
        ", userNo=" + userNo +
        ", maincategoryNo=" + maincategoryNo +
        ", subcategoryNo=" + subcategoryNo +
        ", name='" + name + '\'' +
        ", statusNo=" + statusNo +
        ", purchaseDate=" + purchaseDate +
        ", purchasePlace='" + purchasePlace + '\'' +
        ", price=" + price +
        ", storageLocation='" + storageLocation + '\'' +
        ", postDate=" + postDate +
        ", attachedFiles=" + attachedFiles +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Collection that = (Collection) o;
    return no == that.no;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(no);
  }

}
