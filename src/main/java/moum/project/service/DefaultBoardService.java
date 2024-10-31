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
    public List<Board> listAll() throws Exception {
        return boardDao.listAll();
    }

    @Override
    public List<Board> listPopular() throws Exception {
        return boardDao.listPopular();
    }

    @Override
    public List<Board> listTradePosts() throws Exception {
        return boardDao.listTradePosts(3); // 최대 3개의 게시글만 가져옴
    }

    @Override
    public List<Board> listBraggingPosts() throws Exception {
        return boardDao.listBraggingPosts();
    }

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
        return boardDao.selectById(no); // 게시글 조회 로직
    }

    @Override
    public boolean update(Board board) throws Exception {
        return boardDao.update(board) > 0;
    }

    @Override
    public void delete(int no) throws Exception {
        boardDao.delete(no);
    }

    @Override
    public void increaseViewCount(int id) {
        boardDao.increaseViewCount(id);
    }

    @Override
    public List<Board> selectByUserId(int userId) {
        return boardDao.selectByUserId(userId);
    }

    @Override
    public List<Board> selectRecent(int limit) {
        return boardDao.selectRecent(limit);
    }

}
