package moum.project.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * packageName    : moum.project.vo
 * fileName       : User
 * author         : narilee
 * date           : 24. 10. 15.
 * description    : 사용자의 기본 정보(이메일, 비밀번호, 닉네임, 관리자여부)를 나타내는 클래스입니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 10. 15.        narilee       최초 생성
 * 24. 10. 22.        narilee       name 삭제
 */
@Data
@NoArgsConstructor
public class User implements Serializable {

    /**
     * 직렬화 과정에서 클래스의 호환성을 보장하기 위한 serialVersionUID 입니다.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 사용자의 고유 식별자입니다.
     */
    private int no;

    /**
     * 사용자의 닉네임입니다.
     */
    private String nickname;

    /**
     * 사용자의 이메일입니다.
     */
    private String email;

    /**
     * 사용자의 비밀번호입니다.
     */
    private String password;

    /**
     * 사용자의 관리자 여부입니다.(ture = 관리자, false = 일반유저)
     */
    private boolean admin;

    /**
     * 지정된 식별자로 사용자를 초기화하는 생성자입니다,
     *
     * @param no 사용자의 고유 식별자
     */
    public User(int no) {
        this.no = no;
    }
}
