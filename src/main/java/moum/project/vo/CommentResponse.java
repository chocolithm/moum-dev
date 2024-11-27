package moum.project.vo;

import lombok.Data;
import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonInclude;

//댓글 응답(Response) 클래스
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponse {
    private int no; // 댓글 번호 pk
    private int userNo; // 작성자 번호
    private int boardNo; // 게시글 번호 fk
    private String content; // 내용
    private java.util.Date date; // 생성일시
    private int originalCommentNo;
    private boolean isPublic; // 공개여부

    private User user; // 작성자 정보 추가
}

