package moum.project.vo;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Chat {
  @EqualsAndHashCode.Include private int no;
  private Chatroom chatroom;
  private User sender;
  private String message;
  private Date chatDate;

  public Chat() {

  }

  public Chat(Chatroom chatroom) {
    this.chatroom = chatroom;
  }
}
