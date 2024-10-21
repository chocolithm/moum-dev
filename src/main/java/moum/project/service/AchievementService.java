package moum.project.service;

import moum.project.vo.Achievement;
import moum.project.vo.Maincategory;

import java.util.List;

public interface AchievementService {
  void add(Achievement achievement) throws Exception;

  List<Achievement> list() throws Exception;

  Achievement get(String id) throws Exception;

  boolean update(Achievement achievement) throws Exception;

  void delete(String id) throws Exception;
}
