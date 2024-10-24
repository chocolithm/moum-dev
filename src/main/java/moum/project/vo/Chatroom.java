package moum.project.vo;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Chatroom {
  @EqualsAndHashCode.Include private int no;
  private Board board;
  private User user;
  private String lastMessage;
  private Date chatDate;
}
