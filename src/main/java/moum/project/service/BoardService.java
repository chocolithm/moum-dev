package moum.project.service;

import java.util.List;

import moum.project.vo.AttachedFile;
import moum.project.vo.Board;

public interface BoardService {

    void add(Board board) throws Exception;

    List<Board> list() throws Exception;

    Board get(int no) throws Exception;

    boolean update(Board board) throws Exception;

    void delete(int no) throws Exception;


    void increaseViewCount(int id);

    List<Board> selectByUserId(int userId);

    List<Board> selectRecent(int limit);


    List<Board> listAll() throws Exception;

    List<Board> listPopular() throws Exception;

    List<Board> listTradeSellPosts() throws Exception;
    List<Board> listTradeBuyPosts() throws Exception;

    List<Board> listBraggingPosts() throws Exception;

    List<Board> listByPage(int pageNo, int pageCount) throws Exception;

    int count() throws Exception;

    List<Board> listTradeSellPostsByPage(int offset, int limit) throws Exception;

    int countTradeSellPosts() throws Exception;

    // 구매 게시글 페이징 조회
    List<Board> listTradeBuyPostsByPage(int offset, int limit) throws Exception;

    // 구매 게시글 총 개수 조회
    int countTradeBuyPosts() throws Exception;

    void updateAttachedFiles(int no, List<AttachedFile> newFiles);

   void updateTrade(Board board);
   void deleteAttachedFiles(int boardNo);
   void insertAttachedFiles(List<AttachedFile> files);
}
