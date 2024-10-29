package moum.project.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moum.project.dao.ChatDao;
import moum.project.vo.Chat;
import moum.project.vo.Chatroom;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultChatService implements ChatService {

  private final ChatDao chatDao;

  @Override
  public boolean addChat(Chat chat) throws Exception {
    return chatDao.insertChat(chat);
  }

  @Override
  public boolean addRoom(Chatroom chatroom) throws Exception {
    return chatDao.insertRoom(chatroom);
  }

  @Override
  public List<Chat> loadChat(int roomNo, int pageNo, int pageCount) throws Exception {
    return chatDao.listChat(roomNo, pageNo, pageCount);
  }

  @Override
  public List<Chatroom> listRoom(int userNo) throws Exception {
    return chatDao.listRoom(userNo);
  }

  @Override
  public Chatroom getRoom(int roomNo) throws Exception {
    return chatDao.findRoom(roomNo);
  }

  @Override
  public Chatroom getRoomByBoard(int boardNo, int userNo) throws Exception {
    return chatDao.findRoomByBoard(boardNo, userNo);
  }
}
