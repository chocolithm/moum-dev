    package moum.project.controller;

    import lombok.RequiredArgsConstructor;
    import moum.project.service.BoardService;
    import moum.project.service.CommentService;
    import moum.project.service.UserService;
    import moum.project.vo.Board;
    import moum.project.vo.CommentRequest;
    import moum.project.vo.CommentResponse;
    import moum.project.vo.User;
    import org.springframework.messaging.handler.annotation.DestinationVariable;
    import org.springframework.security.core.annotation.AuthenticationPrincipal;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;

    import java.util.List;

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
                @AuthenticationPrincipal UserDetails userDetails,
                RedirectAttributes redirectAttributes) throws Exception {

            // 현재 로그인한 사용자 정보 가져오기
            User loginUser = userService.getByEmail(userDetails.getUsername());

            // 댓글에 사용자 ID와 게시글 ID 설정
            params.setUserNo(loginUser.getNo());
            params.setBoardNo(boardId);

            int id = commentService.saveComment(params);

            // 저장한 댓글을 반환
            CommentResponse comment = commentService.findCommentById(id);
            // 작성자 정보 설정
            comment.setUser(loginUser);

            return comment;
        }


        @GetMapping("/{boardId}")
        public String viewBoard(@PathVariable int boardId, Model model) {
            Board board = boardService.findBoardById(boardId);
            List<CommentResponse> comments = commentService.findAllComment(boardId);

            model.addAttribute("board", board);
            model.addAttribute("comments", comments);

            return "boardView";
        }

        @GetMapping("/board/{boardId}/comments")
        @ResponseBody // JSON 형식으로 댓글 목록을 반환
        public List<CommentResponse> listComment(@PathVariable final int boardId) {
            return commentService.findAllComment(boardId);
        }

        @DeleteMapping("/comments/{no}")
        public String deleteComment(@PathVariable String no) throws Exception {
            // 게시글과 첨부 파일 삭제
            if (commentService.deleteComment(Integer.parseInt(no))) {
                return "success";
            }
            return "failure";
        }

    }
