package moum.project.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AttachedFile {
  public static final int COLLECTION = 1;
  public static final int BOARD = 2;

  @EqualsAndHashCode.Include private int no;
  @EqualsAndHashCode.Include private int fileCategory; // 수집품(1) or 게시글(2)
  private String filename;
  private String originFilename;
}
