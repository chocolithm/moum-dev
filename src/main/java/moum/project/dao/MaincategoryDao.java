package moum.project.dao;

import java.util.List;
import moum.project.vo.Maincategory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MaincategoryDao {
  boolean insert(Maincategory maincategory) throws Exception;

  List<Maincategory> list() throws Exception;

  Maincategory findBy(int no) throws Exception;

  boolean update(Maincategory maincategory) throws Exception;

  boolean delete(int no) throws Exception;
}
