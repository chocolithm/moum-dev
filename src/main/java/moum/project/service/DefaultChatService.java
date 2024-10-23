package moum.project.service;

import java.util.List;
import java.util.Map;
import moum.project.vo.Chat;
import moum.project.vo.Chatroom;

public class DefaultChatService implements ChatService {
  @Override
  public void addChat(Chat chat) throws Exception {

  }

  @Override
  public void addRoom(Chatroom chatroom) throws Exception {

  }

  @Override
  public List<Chat> listChat(Map map) throws Exception {
    return List.of();
  }

  @Override
  public List<Chatroom> listRoom(int boardNo) throws Exception {
    return List.of();
  }
}
