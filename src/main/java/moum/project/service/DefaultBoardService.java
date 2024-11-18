package moum.project.service;

import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import moum.project.dao.BoardDao;
import moum.project.vo.AttachedFile;
import moum.project.vo.Board;
import org.springframework.stereotype.Service;
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
    public List<Board> listPopularByPage(int offset, int limit) throws Exception {
        return boardDao.listPopularByPage(offset, limit);
    }

    @Override
    public int countPopularPosts() throws Exception {
        return boardDao.countPopularPosts();
    }


    @Override
    public List<Board> listTradeSellPosts() throws Exception {
        return boardDao.listTradeSellPosts(10); // 최대 3개의 게시글만 가져옴
    }

    public List<Board> listTradeBuyPosts() throws Exception {
        return boardDao.listTradeBuyPosts(10);
    }

    @Override
    public List<Board> listBraggingPosts() throws Exception {
        return boardDao.listBraggingPosts();
    }

    @Override
    public void add(Board board) throws Exception {
        boardDao.insert(board); // 게시글 삽입

        List<AttachedFile> attachedFiles = board.getAttachedFiles();
        if (attachedFiles != null && !attachedFiles.isEmpty()) {
            for (AttachedFile file : attachedFiles) {
                file.setBoardNo(board.getNo()); // 게시글 번호 설정
            }
            boardDao.insertAttachedFiles(attachedFiles); // 첨부 파일 삽입

        }
        if (board.getBoardType().equals("trade")) {
            boardDao.insertTrade(board);
        }
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
        if (boardDao.update(board)) {

            // 추가된 첨부파일이 있으면 insert
            List<AttachedFile> attachedFiles = board.getAttachedFiles();
            if (attachedFiles != null && !attachedFiles.isEmpty()) {
                for (AttachedFile file : attachedFiles) {
                    file.setBoardNo(board.getNo()); // 게시글 번호 설정
                }
                boardDao.insertAttachedFiles(attachedFiles); // 첨부 파일 삽입

            }
            return true;
        }
        return false;

//        int count = boardDao.update(board);
//        if (count > 0) {
//            // 기존 첨부 파일 삭제
//            boardDao.deleteAttachedFilesByBoardId(board.getNo());
//
//            // 새로운 첨부 파일 삽입
//            List<AttachedFile> attachedFiles = board.getAttachedFiles();
//            if (attachedFiles != null && !attachedFiles.isEmpty()) {
//                for (AttachedFile file : attachedFiles) {
//                    file.setBoardNo(board.getNo());
//                }
//                boardDao.insertAttachedFiles(attachedFiles);
//            }
//            return true;
//        }
//        return false;
    }

    @Override
    public boolean completeTrade(int no) throws Exception {
        return boardDao.completeTrade(no);
    }

    // BoardService.java
    public void updateTrade(Board board) {
        boardDao.updateTrade(board);
    }

    public void deleteAttachedFiles(int boardNo) {
        boardDao.deleteAttachedFilesByBoardId(boardNo);
    }

    @Override
    public void deleteAttachedFile(String filename) {
        boardDao.deleteAttachedFile(filename);
    }

    public void insertAttachedFiles(List<AttachedFile> files) {
        if (!files.isEmpty()) {
            boardDao.insertAttachedFiles(files);
        }
    }

    @Override
    public Board findBoardById(int boardId) {
        return null;
    }


    @Override
    public void delete(int no) throws Exception {
        // 첨부 파일 삭제
        boardDao.deleteAttachedFilesByBoardId(no);
        // 게시글 삭제
        boardDao.delete(no);
    }


    @Override
    public void increaseViewCount(int boardId) throws Exception{
        Board board =boardDao.selectById(boardId);
        if (board != null) {
            boardDao.increaseViewCount(board.getNo(), board.getViewCount() + 1);
        }
    }

    @Override
    public List<Board> selectByUserId(int userId) {
        return boardDao.selectByUserId(userId);
    }

    @Override
    public List<Board> selectRecent(int limit) {
        return boardDao.selectRecent(limit);
    }

    @Override
    public List<Board> listByPage(int pageNo, int pageCount) throws Exception {
        return boardDao.listByPage(pageNo, pageCount);
    }

    @Override
    public int count() throws Exception {
        return boardDao.count();
    }


    @Override
    public List<Board> listTradeSellPostsByPage(int offset, int limit) throws Exception {
        return boardDao.listTradeSellPostsByPage(offset, limit);
    }

    @Override
    public int countTradeSellPosts() throws Exception {
        return boardDao.countTradeSellPosts();
    }

    @Override
    public List<Board> listTradeBuyPostsByPage(int offset, int limit) throws Exception {
        return boardDao.listTradeBuyPostsByPage(offset, limit);
    }

    @Override
    public int countTradeBuyPosts() throws Exception {
        return boardDao.countTradeBuyPosts();
    }


    @Override
    public void updateAttachedFiles(int no, List<AttachedFile> newFiles) {

    }
}
