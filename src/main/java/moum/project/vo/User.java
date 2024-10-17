package moum.project.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int no;
    private String nickname;
    private String email;
    private String password;
    private boolean admin;

    public User(int no) {
        this.no = no;
    }

}
