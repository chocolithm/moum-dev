package moum.project.service;

import lombok.RequiredArgsConstructor;
import moum.project.dao.LikesDao;
import moum.project.vo.Likes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultLikesService implements LikesService {

  private final LikesDao likesDao;



  @Override
  public List<Likes> selectLikesByUser(int userNo) throws Exception {
    // 특정 유저가 좋아요를 누른 게시글 목록 반환
    return likesDao.selectLikesByUser(userNo);
  }

  @Override
  public int isLikedByUser(int boardNo, int userNo) throws Exception {
    // 특정 유저가 특정 게시글에 대해 좋아요를 눌렀는지 여부 확인
    List<Likes> likes = likesDao.selectLikesByBoard(boardNo);
    return likes.stream().anyMatch(like -> like.getUserNo() == userNo) ? 1 : 0;
  }


  @Override
  public void toggleLike(int boardNo, int userNo) throws Exception {
    // 좋아요 여부 확인
    int isLiked = isLikedByUser(boardNo, userNo);

    if (isLiked == 1) {
      // 이미 좋아요를 누른 상태이므로, 좋아요 취소
      likesDao.deleteLike(boardNo, userNo);
    } else {
      // 좋아요를 누르지 않은 상태이므로, 좋아요 추가
      likesDao.addLike(boardNo, userNo);
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

  @Override
  public boolean hasLiked(int boardNo, int userNo) throws Exception {
    return false;
  }
}
