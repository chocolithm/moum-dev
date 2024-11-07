package moum.project.service;

import java.util.List;
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

    List<Board> listTradePosts() throws Exception;

    List<Board> listBraggingPosts() throws Exception;

    List<Board> listByPage(int pageNo, int pageCount) throws Exception;

    int count() throws Exception;
}
