package moum.project.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

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




}
