package moum.project.dao;


import moum.project.vo.Achievement;
import moum.project.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AchievementDao {

    boolean insert(Achievement achievement) throws Exception;

    List<Achievement> list() throws Exception;

    List<Achievement> listByUser(int userNo) throws Exception;

    User findBy(int to) throws Exception;

    Achievement findBy(String id) throws Exception;

    boolean update(Achievement achievement) throws Exception;

    boolean delete(String id) throws Exception;

    List<Achievement> listByUserRank() throws Exception;

    Achievement findRankByUser(int no) throws Exception;

    boolean insertByUser(Achievement achievement) throws Exception;

    List<Achievement> listUserGetAchievement(int userNo) throws Exception;

    boolean updateMyinfoAchievement(Achievement achievement) throws Exception;

    boolean deleteMyinfoAchievement(User user) throws Exception;

    Achievement findPrimary(int no) throws Exception;
}
