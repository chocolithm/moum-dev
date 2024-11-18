package moum.project.dao;

import java.util.List;
import moum.project.vo.AttachedFile;
import moum.project.vo.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardDao {


    List<Board> list(); // 모든 게시글 조회

    Board selectById(int boardId); // 특정 게시글 조회

    void insert(Board board); // 새 게시글 생성

    void insertTrade(Board board); // 새 게시글 생성

    boolean update(Board board); // 게시글 수정

    int delete(int boardId); // 논리적 삭제

    void increaseViewCount(@Param("no") int boardNo, @Param("count") int count) throws Exception; // 조회수 증가

    List<Board> selectByUserId(int userId); // 특정 사용자의 게시글 조회

    List<Board> selectRecent(int limit); // 최근 게시글 조회

    // 전체 게시글 조회
    List<Board> listAll();

    // 인기 게시글 조회
    List<Board> listPopularByPage(@Param("offset") int offset, @Param("limit") int limit) throws Exception;

    int countPopularPosts() throws Exception;

    // 수집품 거래 게시글 조회
    List<Board> listTradeSellPosts(int limit);
    List<Board> listTradeBuyPosts(int limit);


    // 자랑하기 게시글 조회
    List<Board> listBraggingPosts();

    void updateTrade(Board board);

    boolean completeTrade(int no);

    void insertAttachedFiles(List<AttachedFile> attachedFiles); // 첨부 파일 삽입

    void deleteAttachedFilesByBoardId(int boardId); // 첨부 파일 삭제

    void deleteAttachedFile(String filename);

    List<Board> listByPage(
        @Param("pageNo") int pageNo,
        @Param("pageCount") int pageCount) throws Exception;


    // 판매 게시글 페이징 조회
    List<Board> listTradeSellPostsByPage(@Param("offset") int offset, @Param("limit") int limit) throws Exception;

    // 판매 게시글 총 개수 조회
    int countTradeSellPosts() throws Exception;

    // 구매 게시글 페이징 조회
    List<Board> listTradeBuyPostsByPage(@Param("offset") int offset, @Param("limit") int limit) throws Exception;

    // 구매 게시글 총 개수 조회
    int countTradeBuyPosts() throws Exception;

    int count() throws Exception;
}
