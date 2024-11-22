package moum.project.dao;

import java.util.List;
import moum.project.vo.Maincategory;
import moum.project.vo.Subcategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CollectionCategoryDao {
  boolean insertSubcategory(Subcategory subcategory) throws Exception;

  List<Subcategory> listSubcategory(int maincategoryNo) throws Exception;

  List<Maincategory> listMaincategory() throws Exception;

  Subcategory findSubcategory(int no) throws Exception;

  List<Subcategory> findMaincategory(int no) throws Exception;

  Subcategory findSubcategoryByName(String name) throws Exception;

  List<Subcategory> listSubcategoryByMaincategory(int no) throws Exception;

  List<Maincategory> listMaincategoryByPage(
      @Param("maincategory") Maincategory maincategory,
      @Param("pageNo") int pageNo,
      @Param("pageCount") int pageCount) throws Exception;

  int count(Maincategory maincategory) throws Exception;
}
