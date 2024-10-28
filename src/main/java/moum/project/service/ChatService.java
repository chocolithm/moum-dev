package moum.project.service;

import java.util.List;
import moum.project.vo.Chat;
import moum.project.vo.Chatroom;

public interface ChatService {
  boolean addChat(Chat chat) throws Exception;

  List<Chat> loadChat(int roomNo, int pageNo, int pageCount) throws Exception;

  List<Chatroom> listRoom(int userNo) throws Exception;

  Chatroom getRoom(int roomNo) throws Exception;
}
