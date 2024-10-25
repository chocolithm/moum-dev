package moum.project.dao;

import java.util.List;
import moum.project.vo.Subcategory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SubcategoryDao {
  boolean insert(Subcategory subcategory) throws Exception;

  List<Subcategory> list(int maincategoryNo) throws Exception;

  Subcategory findBy(int no) throws Exception;

  boolean update(Subcategory subcategory) throws Exception;

  boolean delete(int no) throws Exception;
}
