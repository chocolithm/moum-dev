package moum.project.controller;

import lombok.RequiredArgsConstructor;
import moum.project.service.BoardService;
import moum.project.service.CommentService;
import moum.project.vo.CommentRequest;
import moum.project.vo.CommentResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Comment")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    // 신규 댓글 생성
    @PostMapping("/board/{boardId}/comments")
    public CommentResponse saveComment(@PathVariable final int boardId, @RequestBody final CommentRequest params) {
        int id = commentService.saveComment(params);
        return commentService.findCommentById(id);
    }

    // 신규 댓글 생성
    @GetMapping("/board/{boardId}/comments")
    public List<CommentResponse> listComment(@PathVariable final int boardId) {
        return commentService.findAllComment(boardId);
    }



}
