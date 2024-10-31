package moum.project.vo;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Alert {
  @EqualsAndHashCode.Include private int no;
  private User user;
  private String content;
  private LocalDateTime date;
  private boolean read;
}
