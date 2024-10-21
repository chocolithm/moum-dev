package moum.project.vo;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Data
public class Collection implements Serializable {
  private static final long serialVersionUID = 1L;

  @EqualsAndHashCode.Include private int no;
  private int userNo;
  private int maincategoryNo;
  private int subcategoryNo;
  private String name;
  private String enName;
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
}
