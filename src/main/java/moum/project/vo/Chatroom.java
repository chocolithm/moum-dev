package moum.project.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Chatroom {
  @EqualsAndHashCode.Include private int no;
  private int boardNo;
  private int userNo;
}
