package moum.project.dao;

import java.util.List;
import moum.project.vo.Maincategory;
import moum.project.vo.Subcategory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CollectionCategoryDao {
  List<Subcategory> list(int maincategoryNo) throws Exception;

  List<Maincategory> list() throws Exception;
}
