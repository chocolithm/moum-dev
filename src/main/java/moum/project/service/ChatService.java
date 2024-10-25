package moum.project.service;

import java.util.List;
import java.util.Map;
import moum.project.vo.Chat;
import moum.project.vo.Chatroom;

public interface ChatService {
  List<Chat> loadChat(Map map) throws Exception;

  List<Chatroom> listRoom(int userNo) throws Exception;

  Chatroom getRoom(int roomNo) throws Exception;
}
