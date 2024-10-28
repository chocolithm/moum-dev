package moum.project.controller;

import moum.project.service.LikesService;
import moum.project.vo.Likes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/likes")
public class LikesController {

    private final LikesService likesService;

    @Autowired
    public LikesController(LikesService likesService) {
        this.likesService = likesService;
    }

    // 특정 게시글에 좋아요 추가
    @PostMapping("/increaseLike")
    @ResponseBody
    public Map<String, Object> increaseLike(@RequestParam("boardNo") int boardNo) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 추천수 증가
            likesService.addLike(boardNo);

            // 증가 후 추천수 조회
            int likeCount = likesService.countLikesByBoard(boardNo);
            response.put("likeCount", likeCount);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("likeCount", 0); // 실패 시 기본값 반환
        }
        return response;
    }



    // 특정 유저가 좋아요한 게시글 목록 조회
    @GetMapping("/user/{userNo}")
    public List<Likes> getLikesByUser(@PathVariable int userNo) {
        try {
            return likesService.selectLikesByUser(userNo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 특정 게시글에 대해 유저가 좋아요를 눌렀는지 확인
    @GetMapping("/isLiked")
    public boolean isLikedByUser(@RequestParam int boardNo, @RequestParam int userNo) {
        try {
            return likesService.isLikedByUser(boardNo, userNo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 특정 게시글의 좋아요 수 조회
    @GetMapping("/count/{boardNo}")
    public int countLikesByBoard(@PathVariable int boardNo) {
        try {
            return likesService.countLikesByBoard(boardNo);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 특정 게시글에 대해 유저의 좋아요 삭제
    @DeleteMapping
    public String deleteLike(@RequestParam int boardNo, @RequestParam int userNo) {
        try {
            likesService.deleteLike(boardNo, userNo);
            return "좋아요 취소 성공";
        } catch (Exception e) {
            return "좋아요 취소 실패: " + e.getMessage();
        }
    }

    @PostMapping("/toggleLike")
    public Map<String, Object> toggleLike(@RequestParam("boardNo") int boardNo, @RequestParam("userNo") int userNo) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 좋아요 토글
            likesService.toggleLike(boardNo, userNo);

            // 최신 좋아요 수 조회
            int likeCount = likesService.countLikesByBoard(boardNo);
            response.put("likeCount", likeCount);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("likeCount", 0); // 실패 시 기본값 반환
        }
        return response;
    }
}
