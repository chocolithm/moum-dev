package moum.project.service;

import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import moum.project.dao.AchievementDao;
import moum.project.vo.Achievement;
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
  public List<Achievement> listByUser() throws Exception {
    return List.of();
  }

  @Override
  public Achievement get(String id) throws Exception {
    return achievementDao.findBy(id);
  }

  @Override
  public boolean update(Achievement achievement) throws Exception {
    return achievementDao.update(achievement);
  }

  @Override
  public void delete(String id) throws Exception {
    achievementDao.delete(id);
  }
}
