package moum.project.controller;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import moum.project.service.AlertService;
import moum.project.service.ChatService;
import moum.project.vo.Alert;
import moum.project.vo.Chat;
import moum.project.vo.Chatroom;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/send")
@RequiredArgsConstructor
public class SocketController {

  private final ChatService chatService;
  private final AlertService alertService;
  private final SimpUserRegistry simpUserRegistry;

  @MessageMapping("/chat/{roomNo}")
  @SendTo("/receive/chat/{roomNo}")
  public Chat sendMessage(
      @DestinationVariable String roomNo,
      Chat chat) throws Exception {

    if (chat.getMessage().trim().isEmpty()) {
      return null;
    }

    chat.setChatDate(LocalDateTime.now());

    int connectUserCount = getConnectedUsersInRoom(roomNo);

    if (connectUserCount >= 2) {
      chat.setRead(true);
    }

    if (chatService.addChat(chat)) {

      if (connectUserCount < 2) {
        Chatroom chatroom = chatService.getRoom(chat.getChatroom().getNo());

        Alert alert = new Alert();
        alert.setCategory("chatroom");
        alert.setCategoryNo(chatroom.getNo());

        if (chatroom.getParticipant().getNo() == chat.getSender().getNo()) {
          alert.setUser(chatroom.getOwner());
        } else {
          alert.setUser(chatroom.getParticipant());
        }

        if (alertService.exists(alert) == 0) {
          alert.setContent("새로운 메시지가 도착했습니다.");
          alert.setDate(LocalDateTime.now());
          alert.setRead(false);
          alertService.add(alert);

        } else {
          alertService.updateTime(alert.getNo());
        }
      }

      return chat;
    } else {
      return null;
    }
  }

  private int getConnectedUsersInRoom(String roomNo) {
    return (int) simpUserRegistry.getUsers().stream()
        .flatMap(user -> user.getSessions().stream())
        .filter(session -> session.getSubscriptions().stream()
            .anyMatch(subscription -> subscription.getDestination().equals("/receive/chat/" + roomNo)))
        .count();
  }
}
