package moum.project.vo;

import lombok.*;
//댓글 요청(Request) 클래스
import java.io.Serializable;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include private int no;
    private int commentNo; // 댓글 번호 pk
    private int userNo; // 작성자 번호
    private int boardNo; // 게시글 번호 fk
    private String content; // 내용

}
