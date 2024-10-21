package moum.project.vo;

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
    public Achievement(String id) {
        this.id = id;
    }
}
