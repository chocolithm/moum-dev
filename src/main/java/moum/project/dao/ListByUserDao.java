package moum.project.dao;

import moum.project.vo.Achievement;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ListByUserDao {

    boolean insert(Achievement achievement) throws Exception;

    List<Achievement> list() throws Exception;

    Achievement findBy(String id) throws Exception;

    boolean update(Achievement achievement) throws Exception;

    boolean delete(String id) throws Exception;

}
