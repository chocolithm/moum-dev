package moum.project.dao;

import moum.project.vo.Likes;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

public interface LikesDao {
    void addLike(int boardNo, int userNo) throws DuplicateKeyException;  // 좋아요 추가
    List<Likes> selectLikesByBoard(int boardNo);              // 특정 게시글의 모든 좋아요 조회
    List<Likes> selectLikesByUser(int userNo);                // 특정 유저의 모든 좋아요 조회
    int countLikesByBoard(int boardNo) throws Exception;                       // 특정 게시글의 좋아요 수 카운트

    void deleteLike(int boardNo, int userNo);

}
