package moum.project.dao;

import java.util.List;
import moum.project.vo.OtherCategory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OtherCategoryDao {
  boolean insert(OtherCategory otherCategory) throws Exception;

  List<OtherCategory> list() throws Exception;

  OtherCategory findBy(int no) throws Exception;

  boolean update(OtherCategory otherCategory) throws Exception;

  boolean delete(int no) throws Exception;
}
