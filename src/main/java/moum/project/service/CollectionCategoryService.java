package moum.project.service;

import java.util.List;
import moum.project.vo.Maincategory;
import moum.project.vo.Subcategory;

public interface CollectionCategoryService {
  boolean addSubcategory(Subcategory subcategory) throws Exception;

  boolean addMaincategory(Maincategory maincategory) throws Exception;

  List<Subcategory> listSubcategory(int maincategoryNo) throws Exception;

  List<Maincategory> listMaincategory() throws Exception;

  Subcategory getSubcategory(int no) throws Exception;

  List<Subcategory> getMaincategory(int no) throws Exception;

  Subcategory getSubcategoryByName(String name) throws Exception;

  Maincategory getMaincategoryByName(String name) throws Exception;

  List<Subcategory> listSubcategoryByMaincategory(int no) throws Exception;

  List<Maincategory> listMaincategoryByPage(Maincategory maincategory, int pageNo, int pageCount) throws Exception;

  int count(Maincategory maincategory) throws Exception;
}
