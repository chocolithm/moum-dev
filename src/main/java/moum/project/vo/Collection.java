package moum.project.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
  private LocalDate purchaseDate;
  private String purchasePlace;
  private int price;
  private String storageLocation;
  private LocalDateTime postDate;
  private List<AttachedFile> attachedFiles;

  public Collection() {
  }

  public Collection(int no) {
    this.no = no;
  }
}
