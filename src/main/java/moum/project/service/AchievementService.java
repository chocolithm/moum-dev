package moum.project.service;

import java.util.List;
import moum.project.vo.Achievement;

public interface AchievementService {
  void add(Achievement achievement) throws Exception;

  List<Achievement> list() throws Exception;

  List<Achievement> listByUser(int userNo) throws Exception;

  Achievement get(String id) throws Exception;

  boolean update(Achievement achievement) throws Exception;

  void delete(String id) throws Exception;

  List<Achievement> listByUserRank() throws Exception;

  Achievement findRankByUser(int no) throws Exception;

}
