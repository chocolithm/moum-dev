package moum.project.service;

import java.util.List;
import moum.project.vo.Maincategory;

public interface MaincategoryService {
  void add(Maincategory maincategory) throws Exception;

  List<Maincategory> list() throws Exception;

  Maincategory get(int no) throws Exception;

  boolean update(Maincategory maincategory) throws Exception;

  void delete(int no) throws Exception;
}
