package moum.project.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moum.project.service.ChatService;
import moum.project.service.UserService;
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

  @GetMapping("listRoom")
  @ResponseBody
  public List<Chatroom> listRoom(@AuthenticationPrincipal UserDetails userDetails) throws Exception {


    String email = userDetails.getUsername();
    User loginUser = userService.getByEmail(email);

    List<Chatroom> chatroomList = chatService.listRoom(loginUser.getNo());
    return chatroomList;
  }
}
