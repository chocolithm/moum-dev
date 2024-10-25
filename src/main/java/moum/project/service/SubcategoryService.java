package moum.project.service;

import java.util.List;
import moum.project.vo.Subcategory;

public interface SubcategoryService {
  void add(Subcategory subcategory) throws Exception;

  List<Subcategory> list(int maincategoryNo) throws Exception;

  Subcategory get(int no) throws Exception;

  boolean update(Subcategory subcategory) throws Exception;

  void delete(int no) throws Exception;
}
