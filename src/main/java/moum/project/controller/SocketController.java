package moum.project.controller;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import moum.project.service.ChatService;
import moum.project.vo.Alert;
import moum.project.vo.Chat;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/send")
@RequiredArgsConstructor
public class SocketController {

  private final ChatService chatService;

  @MessageMapping("/chat/{roomNo}")
  @SendTo("/receive/chat/{roomNo}")
  public Chat sendMessage(
      @DestinationVariable String roomNo,
      Chat chat) throws Exception {

    chat.setChatDate(LocalDateTime.now());
    if (chatService.addChat(chat)) {
      return chat;
    } else {
      return null;
    }
  }

  @MessageMapping("/alert/{userNo}")
  @SendTo("/receive/alert/{userNo}")
  public Alert sendAlert(
      @DestinationVariable String userNo,
      Alert alert) throws Exception {
    return null;
  }
}
