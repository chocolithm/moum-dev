package moum.project.service;

import java.util.List;
import java.util.Map;
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
  public void addChat(Chat chat) throws Exception {

  }

  @Override
  public void addRoom(Chatroom chatroom) throws Exception {

  }

  @Override
  public List<Chat> listChat(Map map) throws Exception {
    return chatDao.listChat(map);
  }

  @Override
  public List<Chatroom> listRoom(int userNo) throws Exception {
    return chatDao.listRoom(userNo);
  }
}
