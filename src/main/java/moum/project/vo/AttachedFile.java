package moum.project.vo;

import lombok.*;

@Data
public class AttachedFile {
  public static final int COLLECTION = 1;
  public static final int BOARD = 2;
  public static final int PROFILE = 3;
  public static final int ACHIEVEMENT = 4;

  @EqualsAndHashCode.Include
  private int no; // photo_id에 매핑
  private int boardNo; // board_id에 매핑
  // 필요에 따라 collectionNo 필드도 추가 가능
  // private int collectionNo;

  // fileCategory 필드는 현재 테이블과 매핑되지 않으므로 필요에 따라 유지
  private int fileCategory; // 필요하지 않다면 제거 가능

  private String filename; // filename에 매핑
  private String originFilename; // origin_filename에 매핑

}
