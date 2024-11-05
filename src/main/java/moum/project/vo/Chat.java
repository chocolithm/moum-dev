package moum.project.vo;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Chat {
  @EqualsAndHashCode.Include private int no;
  private Chatroom chatroom;
  private User sender;
  private String message;
  private LocalDateTime chatDate;
  private boolean read;

  public Chat() {

  }

  public Chat(Chatroom chatroom) {
    this.chatroom = chatroom;
  }
}
