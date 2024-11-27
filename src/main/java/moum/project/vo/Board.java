package moum.project.vo;

import java.io.Serial;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import lombok.Data;

@Data
public class Board implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int no;
    private String title;
    private String content;
    private int userNo;
    private java.util.Date postDate;
    private int viewCount;
    private boolean isPublic;
    private boolean isDeleted;
    private List<AttachedFile> attachedFiles;
    private String imagePath; // 이미지 경로
    private String description; // 게시글 설명
    private String boardType; // 게시글 종류 ("general" 또는 "trade")

    private Collection collection; // 거래대상 수집품
    private Integer price; // 가격
    private CollectionStatus collectionStatus;
    private boolean sellOrSoldStatus; // 거래중/거래완료 표시
    private int likeCount; // 추천수 추가
    private String tradeType; // sell or buy 거래게시글 종류

    private User user; //작성자 정보를 받기 위한 User 객체
    private String postDateString; // 관리자 검색 시 사용할 작성일자
    private Maincategory maincategory;


    private boolean isPopular; // 인기게시글 여부
    private int commentCount; // 댓글 개수를 저장하는 필드 추가

    public String getFormattedPrice() {
        return price != null ? NumberFormat.getInstance(Locale.KOREA).format(price) : "0";
    }

}
