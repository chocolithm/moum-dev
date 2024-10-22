package moum.project.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import moum.project.dao.BoardDao;
import moum.project.vo.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class DefaultBoardService implements BoardService {

    @NonNull
    BoardDao boardDao;

    @Override
    public void add(Board board) throws Exception {
        boardDao.insert(board);
    }


    @Override
    public List<Board> list() throws Exception {
        return boardDao.list();
    }

    @Override
    public Board get(int no) throws Exception {
        return null;
    }

    @Override
    public boolean update(Board board) throws Exception {
        return false;
    }

    @Override
    public void delete(int no) throws Exception {

    }

    @Override
    public void increaseViewCount(int id) {

    }

    @Override
    public List<Board> selectByUserId(int userId) {
        return List.of();
    }

    @Override
    public List<Board> selectRecent(int limit) {
        return List.of();
    }
}
