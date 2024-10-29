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

    List<Achievement> listByUser() throws Exception;

    User findBy(int to) throws Exception;

    Achievement findBy(String id) throws Exception;

    boolean update(Achievement achievement) throws Exception;

    boolean delete(String id) throws Exception;

}
