package moum.project.service;

import java.util.List;
import java.util.Map;
import moum.project.vo.Chat;
import moum.project.vo.Chatroom;

public interface ChatService {
  void addChat(Chat chat) throws Exception;

  void addRoom(Chatroom chatroom) throws Exception;

  List<Chat> listChat(Map map) throws Exception;

  List<Chatroom> listRoom(int userNo) throws Exception;
}
