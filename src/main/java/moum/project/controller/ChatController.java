package moum.project.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moum.project.service.AlertService;
import moum.project.service.BoardService;
import moum.project.service.ChatService;
import moum.project.service.UserService;
import moum.project.vo.Alert;
import moum.project.vo.Board;
import moum.project.vo.Chat;
import moum.project.vo.Chatroom;
import moum.project.vo.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

  private final ChatService chatService;
  private final UserService userService;
  private final BoardService boardService;
  private final AlertService alertService;

  @GetMapping("/sender")
  @ResponseBody
  public User getSender(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
    User sender = userService.getByEmail(userDetails.getUsername());
    sender.setPassword("");
    return sender;
  }

  @GetMapping("/checkRoom")
  @ResponseBody
  public Chatroom checkRoom(
      int boardNo,
      @AuthenticationPrincipal UserDetails userDetails) throws Exception {

    User loginUser = userService.getByEmail(userDetails.getUsername());

    Chatroom chatroom = chatService.getRoomByBoard(boardNo, loginUser.getNo());

    if (chatroom == null) {
      chatroom = new Chatroom();
      Board board = boardService.get(boardNo);
      board.setContent("");
      chatroom.setBoard(board);
    }

    return chatroom;
  }

  @GetMapping("/getRoom")
  @ResponseBody
  public Chatroom getRoom(int no) throws Exception {
    return chatService.getRoom(no);
  }

  @GetMapping("/addRoom")
  @ResponseBody
  public Chatroom addRoom(
      int boardNo,
      @AuthenticationPrincipal UserDetails userDetails) throws Exception {

    User loginUser = userService.getByEmail(userDetails.getUsername());
    Board board = boardService.get(boardNo);

    if (board.getUserNo() == loginUser.getNo()) {
      return null;
    }

    Chatroom chatroom = new Chatroom();
    chatroom.setBoard(board);
    chatroom.setParticipant(loginUser);

    if (chatService.addRoom(chatroom)) {
      return chatroom;
    }
    return null;

  }

  @GetMapping("/listRoom")
  @ResponseBody
  public List<Chatroom> listRoom(@AuthenticationPrincipal UserDetails userDetails) throws Exception {

    User loginUser = userService.getByEmail(userDetails.getUsername());

    return chatService.listRoom(loginUser.getNo());
  }

  @GetMapping("/openRoom")
  @ResponseBody
  public Chatroom openRoom(int no, @AuthenticationPrincipal UserDetails userDetails) throws Exception {

    User loginUser = userService.getByEmail(userDetails.getUsername());
    chatService.updateRead(no, loginUser.getNo());

    Alert alert = new Alert();
    alert.setCategory("chatroom");
    alert.setCategoryNo(no);
    alert.setUser(loginUser);
    alertService.updateReadByCategoryAndUser(alert);

    return chatService.getRoom(no);
  }

  @GetMapping("/loadChat")
  @ResponseBody
  public List<Chat> loadChat(int no, int pageNo) throws Exception {
    int pageCount = 20;
    return chatService.loadChat(no, (pageNo - 1) * pageCount, pageCount);
  }
}
