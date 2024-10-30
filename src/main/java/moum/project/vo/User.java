package moum.project.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
 * 24. 10. 24.        narilee       프로필 사진, 가입일, 탈퇴일, sns 아이디 추가
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
     * 사용자의 프로필 사진입니다.
     */
    private String photo;

    /**
     * 사용자가 처음 회원가입한 날짜입니다.
     */
    private LocalDateTime startDate;

    /**
     * 사용자가 탈퇴한 날짜입니다. 회원 탈퇴시 사용자가 작성한 게시글이나 댓글 처리를 위해 사용됩니다.
     */
    private LocalDate endDate;

    /**
     * 사용자가 마지막으로 로그인한 날짜입니다.
     */
    private LocalDateTime lastLogin;

    /**
     * 간편 로그인시 사용되는 id입니다.
     */
    private String snsId;

    /**
     * 지정된 식별자로 사용자를 초기화하는 생성자입니다,
     *
     * @param no 사용자의 고유 식별자
     */
    public User(int no) {
        this.no = no;
    }
}
