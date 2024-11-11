package moum.project.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Achievement implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String content;
    private String photo;
    private String condition;
    private String location;
    private int progress; // 진행도
    private LocalDateTime getDate; // 취득일자
    private int rank;
    private int score;
    private User user;
    private int completeCount;
    private int isPrimary;

    public Achievement(String id) {
        this.id = id;
    }
}
