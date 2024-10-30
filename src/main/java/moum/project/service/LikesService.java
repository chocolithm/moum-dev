package moum.project.service;

import moum.project.vo.Likes;

import java.util.List;

public interface LikesService {

    /**
     * 특정 게시글에 대해 좋아요를 추가합니다.
     * 동일한 게시글에 대한 중복 추천이 불가합니다.
     */

    /**
     * 특정 유저가 좋아요를 누른 게시글 목록을 반환합니다.
     */
    List<Likes> selectLikesByUser(int userNo) throws Exception;

    /**
     * 특정 게시글에 대한 좋아요 여부를 확인합니다.
     *
     * @return
     */
    int isLikedByUser(int boardNo, int userNo) throws Exception;

    /**
     * 특정 게시글의 좋아요 수를 반환합니다.
     */
    int countLikesByBoard(int boardNo) throws Exception;

    /**
     * 특정 게시글에 대한 좋아요를 삭제합니다.
     */
    void deleteLike(int boardNo, int userNo) throws Exception;

    void toggleLike(int boardNo, int userNo) throws Exception;
}
