package moum.project.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Chatroom {
  @EqualsAndHashCode.Include private int no;
  private int boardNo;
  private int userNo;
}
