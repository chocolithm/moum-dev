package moum.project.service;

import moum.project.vo.Board;
import moum.project.vo.Collection;

import java.util.List;

public interface BoardService {

    void add(Board board) throws Exception;

    List<Board> list() throws Exception;

    Board get(int no) throws Exception;

    boolean update(Board board) throws Exception;

    void delete(int no) throws Exception;


    void increaseViewCount(int id);

    List<Board> selectByUserId(int userId);

    List<Board> selectRecent(int limit);
}
