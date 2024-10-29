package moum.project.vo;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

import java.io.Serializable;

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

    public Achievement(String id) {
        this.id = id;
    }
}
