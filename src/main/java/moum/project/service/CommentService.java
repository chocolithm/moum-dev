package moum.project.service;

import lombok.RequiredArgsConstructor;
import moum.project.dao.CommentDao;
import moum.project.vo.CommentRequest;
import moum.project.vo.CommentResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentDao commentDao;

    /**
     * 댓글 저장
     * @param params - 댓글 정보
     * @return Generated PK
     */
    @Transactional
    public int saveComment(final CommentRequest params) {
        commentDao.save(params);
        return params.getNo();
    }

    /**
     * 댓글 상세정보 조회
     * @param id - PK
     * @return 댓글 상세정보
     */
    public CommentResponse findCommentById(final int id) {
        return commentDao.findById(id);
    }

    /**
     * 댓글 수정
     * @param params - 댓글 정보
     * @return PK
     */
    @Transactional
    public int updateComment(final CommentRequest params) {
        commentDao.update(params);
        return params.getNo();
    }

    /**
     * 댓글 삭제
     * @param id - PK
     * @return PK
     */
    @Transactional
    public int deleteComment(final int id) {
        commentDao.deleteById(id);
        return id;
    }

    /**
     * 댓글 리스트 조회
     * @param boardId - 게시글 번호 (FK)
     * @return 특정 게시글에 등록된 댓글 리스트
     */
    public List<CommentResponse> findAllComment(final int boardId) {
        return commentDao.findAll(boardId);
    }

}
