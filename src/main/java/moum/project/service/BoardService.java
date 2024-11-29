package moum.project.service;

import java.util.List;
import moum.project.vo.AttachedFile;
import moum.project.vo.Board;

public interface BoardService {

    void add(Board board) throws Exception;

    List<Board> list() throws Exception;

    Board get(int no) throws Exception;

    Board getBragging(int no) throws Exception;


    boolean update(Board board) throws Exception;

    boolean completeTrade(int no) throws Exception;

    void delete(int no) throws Exception;

    void increaseViewCount(int boardId) throws Exception;

    List<Board> selectByUserId(int userId);

    List<Board> selectRecent(int limit);

    List<Board> listPopularByPage(int offset, int limit) throws Exception;

    int countPopularPosts() throws Exception;

    List<Board> listTradeSellPosts() throws Exception;

    List<Board> listTradeBuyPosts() throws Exception;


    List<Board> listByPage(int pageNo, int pageCount) throws Exception;

    List<Board> listByPageFromAdmin(Board board, int pageNo, int pageCount) throws Exception;

    int count() throws Exception;

    int countFromAdmin(Board board) throws Exception;

    List<Board> listTradeSellPostsByPage(int offset, int limit) throws Exception;

    int countTradeSellPosts() throws Exception;

    // 구매 게시글 페이징 조회
    List<Board> listTradeBuyPostsByPage(int offset, int limit) throws Exception;

    // 구매 게시글 총 개수 조회
    int countTradeBuyPosts() throws Exception;

    void updateAttachedFiles(int no, List<AttachedFile> newFiles);

    void updateTrade(Board board);

    void deleteAttachedFiles(int boardNo);

    void deleteAttachedFile(String filename);

    void insertAttachedFiles(List<AttachedFile> files);

    Board findBoardById(int boardId);

    // 기존 메서드 유지
    List<Board> searchByPage(String keyword, int offset, int limit) throws Exception;
    int countByKeyword(String keyword) throws Exception;

    // 새로운 메서드 추가
    List<Board> searchByCategoryAndPage(String keyword, Integer categoryNo, int offset, int limit) throws Exception;
    int countByKeywordAndCategory(String keyword, Integer categoryNo) throws Exception;

    List<Board> searchPopularByCategoryAndPage(String keyword, Integer categoryNo, int offset, int limit) throws Exception;

    int countPopularByKeywordAndCategory(String keyword, Integer categoryNo) throws Exception;

    List<Board> searchTradeSellPostsByPage(String keyword, Integer categoryNo, int offset, int limit) throws Exception;

    int countTradeSellPostsByKeywordAndCategory(String keyword, Integer categoryNo) throws Exception;

    List<Board> searchTradeBuyPostsByPage(String keyword, Integer categoryNo, int offset, int limit) throws Exception;

    int countTradeBuyPostsByKeywordAndCategory(String keyword, Integer categoryNo) throws Exception;


    // Bragging 관련 메서드
    List<Board> listBraggingPosts(int offset, int limit) throws Exception;
    int countBraggingPosts() throws Exception;
    List<Board> searchBraggingPosts(String keyword, Integer categoryNo, int offset, int limit) throws Exception;
    int countBraggingSearchResults(String keyword, Integer categoryNo) throws Exception;

    // Bragging Popular 관련 메서드
    List<Board> listBraggingPopularPosts(int offset, int size, int minViewCount);
    int countBraggingPopularPosts(int minViewCount);
    List<Board> searchBraggingPopularPosts(String keyword, Integer categoryNo, int offset, int size, int minViewCount);
    int countBraggingPopularSearchResults(String keyword, Integer categoryNo, int minViewCount);


}
