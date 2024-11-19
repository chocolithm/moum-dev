package moum.project.dao;


import java.util.List;
import moum.project.vo.Achievement;
import moum.project.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AchievementDao {

    boolean insert(Achievement achievement) throws Exception;

    boolean insertByUser(Achievement achievement) throws Exception;

    List<Achievement> list() throws Exception;

    List<Achievement> listByUser(int userNo) throws Exception;

    List<Achievement> listByUserRank() throws Exception;

    List<Achievement> listUserGetAchievement(int userNo) throws Exception;

    List<Achievement> listByPage(
        @Param("achievement") Achievement achievement,
        @Param("pageNo") int pageNo,
        @Param("pageCount") int pageCount) throws Exception;

    User findBy(int no) throws Exception;

    Achievement findBy(String id) throws Exception;

    Achievement findRankByUser(int no) throws Exception;

    Achievement findPrimary(int no) throws Exception;

    Achievement findMyAchievement(
        @Param("id") String id,
        @Param("userNo") int userNo) throws Exception;

    boolean update(Achievement achievement) throws Exception;

    boolean updatePrimaryAchievement(Achievement achievement) throws Exception;

    boolean updateCount(Achievement achievement) throws Exception;

    boolean delete(String id) throws Exception;

    boolean deletePrimaryAchievement(User user) throws Exception;

    boolean completeAchievement(Achievement achievement) throws Exception;

    int count() throws Exception;
}
