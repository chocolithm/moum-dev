package moum.project.vo;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Chatroom {
  @EqualsAndHashCode.Include private int no;
  private Board board;
  private User participant;
  private String lastMessage;
  private Date chatDate;

  public Chatroom() {

  }

  public Chatroom(int no) {
    this.no = no;
  }
}
