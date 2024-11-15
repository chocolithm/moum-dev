package moum.project.service;

import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import moum.project.dao.AchievementDao;
import moum.project.vo.Achievement;
import moum.project.vo.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultAchievementService implements AchievementService {

  @NonNull
  AchievementDao achievementDao;

  @Override
  public void add(Achievement achievement) throws Exception {
    achievementDao.insert(achievement);
  }

  @Override
  public List<Achievement> list() throws Exception {
    return achievementDao.list();
  }

  @Override
  public List<Achievement> listByUser(int userNo) throws Exception {
    return achievementDao.listByUser(userNo);
  }

  @Override
  public List<Achievement> listByUserRank() throws Exception {
    return achievementDao.listByUserRank();
  }

  @Override
  public List<Achievement> listUserGetAchievement(int no) throws Exception {
    return achievementDao.listUserGetAchievement(no);
  }

  @Override
  public List<Achievement> listByPage(int pageNo, int pageCount) throws Exception {
    return achievementDao.listByPage(pageNo, pageCount);
  }

  @Override
  public Achievement get(String id) throws Exception {
    return achievementDao.findBy(id);
  }

  @Override
  public Achievement findRankByUser(int no) throws Exception {
    return achievementDao.findRankByUser(no);
  }

  @Override
  public Achievement findPrimary(int no) throws Exception {
    return achievementDao.findPrimary(no);
  }

  @Override
  public Achievement findMyAchievement(String id, int userNo) throws Exception {
    return achievementDao.findMyAchievement(id, userNo);
  }

  @Override
  public boolean update(Achievement achievement) throws Exception {
    return achievementDao.update(achievement);
  }

  @Override
  public boolean updatePrimaryAchievement(Achievement achievement) throws Exception {
    return achievementDao.updatePrimaryAchievement(achievement);
  }

  @Override
  public boolean updateCount(Achievement achievement) throws Exception {
    return achievementDao.updateCount(achievement);
  }

  @Override
  public void delete(String id) throws Exception {
    achievementDao.delete(id);
  }

  @Override
  public boolean deletePrimaryAchievement(User user) throws Exception {
    return achievementDao.deletePrimaryAchievement(user);
  }

  @Override
  public int count() throws Exception {
    return achievementDao.count();
  }

  @Override
  public boolean completeAchievement(Achievement achievement) throws Exception {
    return achievementDao.completeAchievement(achievement);
  }
}
