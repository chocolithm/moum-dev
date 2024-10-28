package moum.project.service;

import lombok.RequiredArgsConstructor;
import moum.project.dao.LikesDao;
import moum.project.vo.Likes;
import org.springframework.stereotype.Service;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultLikesService implements LikesService {

    private final LikesDao likesDao;

    @Override
    public void addLike(int boardNo) throws Exception {
        try {
            likesDao.addLike(boardNo); // 좋아요 추가
        } catch (DuplicateKeyException e) {
            // 중복 좋아요 예외 처리 (동일한 유저가 같은 게시글을 다시 좋아요하는 경우)
            System.out.println("이미 좋아요를 누른 게시글입니다.");
        }
    }

    @Override
    public List<Likes> selectLikesByUser(int userNo) throws Exception {
        // 특정 유저가 좋아요를 누른 게시글 목록 반환
        return likesDao.selectLikesByUser(userNo);
    }

    @Override
    public boolean isLikedByUser(int boardNo, int userNo) throws Exception {
        // 특정 유저가 특정 게시글에 대해 좋아요를 눌렀는지 여부 확인
        List<Likes> likes = likesDao.selectLikesByBoard(boardNo);
        return likes.stream().anyMatch(like -> like.getUserNo() == userNo);
    }

    @Override
    public void toggleLike(int boardNo, int userNo) throws Exception {
        // 좋아요 여부 확인
        boolean isLiked = isLikedByUser(boardNo, userNo);

        if (isLiked) {
            // 이미 좋아요를 누른 상태이므로, 좋아요 취소
            likesDao.deleteLike(boardNo, userNo);
        } else {
            // 좋아요를 누르지 않은 상태이므로, 좋아요 추가
            likesDao.addLike(boardNo);
        }
    }

    @Override
    public int countLikesByBoard(int boardNo) throws Exception {
        // 최신 좋아요 수 반환
        return likesDao.countLikesByBoard(boardNo);
    }

    @Override
    public void deleteLike(int boardNo, int userNo) throws Exception {

    }
}
