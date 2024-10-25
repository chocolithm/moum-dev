package moum.project.dao;

import java.util.List;
import java.util.Map;
import moum.project.vo.Chat;
import moum.project.vo.Chatroom;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatDao {
  boolean insertChat(Chat chat) throws Exception;

  boolean insertRoom(Chatroom chatroom) throws Exception;

  List<Chat> listChat(Map map) throws Exception;

  List<Chatroom> listRoom(int userNo) throws Exception;

  Chat findChat(int chatNo) throws Exception;

  Chatroom findRoom(int roomNo) throws Exception;
}
