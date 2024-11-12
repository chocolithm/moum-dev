package moum.project.vo;

import lombok.*;
//댓글 요청(Request) 클래스
import java.io.Serializable;
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequest {

        private int no; // 댓글 번호 pk
        private int userNo; // 작성자 번호
        private int boardNo; // 게시글 번호 fk
        private String content; // 내용
        private java.util.Date date; // 생성일시
        private int originalCommentNo;
        private boolean isPublic; // 공개여부
}
