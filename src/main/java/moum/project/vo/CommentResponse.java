package moum.project.vo;

import lombok.Getter;

//댓글 응답(Response) 클래스
@Getter
public class CommentResponse {
    private int commentNo; // 댓글 번호 pk
    private int userNo; // 작성자 번호
    private int boardNo; // 게시글 번호 fk
    private String content; // 내용
    private java.util.Date date; // 생성일시
    private int originalCommentNo;
    private boolean isPublic; // 공개여부
}
