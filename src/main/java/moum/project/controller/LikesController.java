package moum.project.controller;

import moum.project.service.LikesService;
import moum.project.service.UserService;
import moum.project.vo.Likes;
import moum.project.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/board")
public class LikesController {

    private final LikesService likesService;
    private final UserService userService;

    @Autowired
    public LikesController(LikesService likesService, UserService userService) {

        this.likesService = likesService;
        this.userService = userService;
    }

//    @PostMapping("/increaseLike")
//    @ResponseBody
//    public Map<String, Object> increaseLike(@RequestParam("boardNo") int boardNo, @RequestParam("userNo") int userNo) {
//        Map<String, Object> response = new HashMap<>();
//        try {
//            // 해당 유저가 이미 해당 게시글을 추천했는지 확인
//            int isLiked = likesService.isLikedByUser(boardNo, userNo);
//
//            if (isLiked > 0) {
//                // 이미 추천한 상태면 추천 취소
//                likesService.deleteLike(boardNo, userNo);
//            } else {
//                // 추천하지 않은 상태면 추천 추가
//                likesService.addLike(boardNo, userNo);
//            }
//
//            // 최신 추천수 조회
//            int likeCount = likesService.countLikesByBoard(boardNo);
//            response.put("likeCount", likeCount);
//            response.put("isLiked", isLiked == 0); // 추천 상태 전달 (0이면 추천 추가, 아니면 취소)
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.put("likeCount", 0); // 실패 시 기본값 반환
//        }
//        return response;
//    }


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
    public int isLikedByUser(@RequestParam int boardNo, @RequestParam int userNo) {
        try {
            return likesService.isLikedByUser(boardNo, userNo); // 1이면 좋아요 누름, 0이면 누르지 않음
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // 예외 발생 시 -1 반환 (실패 상태)
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
    @ResponseBody

    public Map<String, Object> toggleLike(@RequestParam("boardNo") int boardNo, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        Map<String, Object> response = new HashMap<>();
        User loginUser = userService.getByEmail(userDetails.getUsername());
        try {
            // 좋아요 토글
            likesService.toggleLike(boardNo, loginUser.getNo());

            // 최신 좋아요 수 조회
            int likeCount = likesService.countLikesByBoard(boardNo);
            response.put("likeCount", likeCount);
            response.put("message", "좋아요 상태가 변경되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            response.put("likeCount", 0); // 실패 시 기본값 반환
            response.put("message", "좋아요 상태 변경에 실패했습니다.");
        }
        return response;
    }
}
