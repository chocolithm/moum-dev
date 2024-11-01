package moum.project.controller;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import moum.project.service.AlertService;
import moum.project.service.ChatService;
import moum.project.service.UserService;
import moum.project.vo.Alert;
import moum.project.vo.Chat;
import moum.project.vo.Chatroom;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/send")
@RequiredArgsConstructor
public class SocketController {

  private final ChatService chatService;
  private final AlertService alertService;
  private final UserService userService;
  private final SimpMessagingTemplate messagingTemplate;

  @MessageMapping("/chat/{roomNo}")
  @SendTo("/receive/chat/{roomNo}")
  public Chat sendMessage(
      @DestinationVariable String roomNo,
      Chat chat) throws Exception {

    chat.setChatDate(LocalDateTime.now());
    if (chatService.addChat(chat)) {

      Chatroom chatroom = chatService.getRoom(chat.getChatroom().getNo());

      Alert alert = new Alert();
      alert.setContent("새로운 메시지가 도착했습니다.");
      alert.setDate(LocalDateTime.now());
      alert.setRead(false);

      if (chatroom.getParticipant().getNo() == chat.getSender().getNo()) {
        alert.setUser(chatroom.getOwner());
      } else {
        alert.setUser(chatroom.getParticipant());
      }
      alertService.add(alert);

      return chat;
    } else {
      return null;
    }
  }
}
