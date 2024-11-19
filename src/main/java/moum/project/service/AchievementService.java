package moum.project.service;

import java.util.List;
import moum.project.vo.Achievement;
import moum.project.vo.User;

public interface AchievementService {
  void add(Achievement achievement) throws Exception;

  List<Achievement> list() throws Exception;

  List<Achievement> listByUser(int userNo) throws Exception;

  List<Achievement> listByUserRank() throws Exception;

  List<Achievement> listUserGetAchievement(int userNo) throws Exception;

  List<Achievement> listByPage(Achievement achievement, int pageNo, int pageCount) throws Exception;

  Achievement get(String id) throws Exception;

  Achievement findRankByUser(int no) throws Exception;

  Achievement findPrimary(int no) throws Exception;

  Achievement findMyAchievement(String id, int userNo) throws Exception;

  boolean update(Achievement achievement) throws Exception;

  boolean updatePrimaryAchievement(Achievement achievement) throws Exception;

  boolean updateCount(Achievement achievement) throws Exception;

  void delete(String id) throws Exception;

  boolean deletePrimaryAchievement(User user) throws Exception;

  boolean completeAchievement(Achievement achievement) throws Exception;

  int count(Achievement achievement) throws Exception;
}
