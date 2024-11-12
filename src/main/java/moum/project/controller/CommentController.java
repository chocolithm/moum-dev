    package moum.project.controller;

    import lombok.RequiredArgsConstructor;
    import moum.project.service.BoardService;
    import moum.project.service.CommentService;
    import moum.project.service.UserService;
    import moum.project.vo.Board;
    import moum.project.vo.CommentRequest;
    import moum.project.vo.CommentResponse;
    import moum.project.vo.User;
    import org.springframework.security.core.annotation.AuthenticationPrincipal;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @Controller
    @RequestMapping("/Comment")
    @RequiredArgsConstructor
    @RestController
    public class CommentController {

        private final CommentService commentService;
        private final UserService userService;
        private final BoardService boardService;

        @PostMapping("/board/{boardId}/comments")
        public CommentResponse saveComment(
                @PathVariable final int boardId,
                @RequestBody final CommentRequest params,
                @AuthenticationPrincipal UserDetails userDetails) throws Exception {

            // 현재 로그인한 사용자 정보 가져오기
            User loginUser = userService.getByEmail(userDetails.getUsername());

            // 댓글에 사용자 ID와 게시글 ID 설정
            params.setUserNo(loginUser.getNo());
            params.setBoardNo(boardId);

            int id = commentService.saveComment(params);
            return commentService.findCommentById(id);
        }


        // 신규 댓글 생성
        @GetMapping("/board/{boardId}/comments")
        public List<CommentResponse> listComment(@PathVariable final int boardId) {
            return commentService.findAllComment(boardId);
        }



    }
