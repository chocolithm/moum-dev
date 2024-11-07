package moum.project.service;

import java.util.List;
import moum.project.vo.Achievement;
import moum.project.vo.User;

public interface AchievementService {
  void add(Achievement achievement) throws Exception;

  List<Achievement> list() throws Exception;

  List<Achievement> listByUser(int userNo) throws Exception;

  Achievement get(String id) throws Exception;

  boolean update(Achievement achievement) throws Exception;

  void delete(String id) throws Exception;

  List<Achievement> listByUserRank() throws Exception;

  Achievement findRankByUser(int no) throws Exception;

  List<Achievement> listUserGetAchievement(int userNo) throws Exception;

  boolean updateMyinfoAchievement(Achievement achievement) throws Exception;

  boolean deleteMyinfoAchievement(User user) throws Exception;

  Achievement findPrimary(int no) throws Exception;

  List<Achievement> listByPage(int pageNo, int pageCount) throws Exception;

  int count() throws Exception;
}
