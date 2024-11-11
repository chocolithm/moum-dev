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
  private int senderNo;
  private String lastMessage;
  private LocalDateTime chatDate;
  private boolean read;
  private int count;

  public Chatroom() {

  }

  public Chatroom(int no) {
    this.no = no;
  }
}
