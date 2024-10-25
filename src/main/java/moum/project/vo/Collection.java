package moum.project.vo;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Collection implements Serializable {
  private static final long serialVersionUID = 1L;

  @EqualsAndHashCode.Include private int no;
  private User user;
  private Maincategory maincategory;
  private Subcategory subcategory;
  private String name;
  private String enName;
  private CollectionStatus status;
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
