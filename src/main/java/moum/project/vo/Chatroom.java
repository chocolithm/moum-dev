package moum.project.vo;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Chatroom {
  @EqualsAndHashCode.Include private int no;
  private Board board;
  private User owner;
  private User participant;
  private String lastMessage;
  private LocalDateTime chatDate;

  public Chatroom() {

  }

  public Chatroom(int no) {
    this.no = no;
  }
}
