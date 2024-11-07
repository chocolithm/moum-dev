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
  public Achievement get(String id) throws Exception {
    return achievementDao.findBy(id);
  }

  @Override
  public boolean updateMyinfoAchievement(Achievement achievement) throws Exception {
    return achievementDao.updateMyinfoAchievement(achievement);
  }

  @Override
  public boolean deleteMyinfoAchievement(User user) throws Exception {
    return achievementDao.deleteMyinfoAchievement(user);
  }

  @Override
  public boolean update(Achievement achievement) throws Exception {
    return achievementDao.update(achievement);
  }


  @Override
  public void delete(String id) throws Exception {
    achievementDao.delete(id);
  }

  @Override
  public List<Achievement> listByUserRank() throws Exception {
    return achievementDao.listByUserRank();
  }

  @Override
  public Achievement findRankByUser(int no) throws Exception {
    return achievementDao.findRankByUser(no);
  }

  @Override
  public List<Achievement> listUserGetAchievement(int no) throws Exception {
    return achievementDao.listUserGetAchievement(no);
  }

  @Override
  public Achievement findPrimary(int no) throws Exception {
    return achievementDao.findPrimary(no);
  }

  @Override
  public List<Achievement> listByPage(int pageNo, int pageCount) throws Exception {
    return achievementDao.listByPage(pageNo, pageCount);
  }

  @Override
  public int count() throws Exception {
    return achievementDao.count();
  }
}
