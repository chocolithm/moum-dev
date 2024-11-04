package moum.project.service;

import java.util.List;
import moum.project.vo.OtherCategory;

public interface OtherCategoryService {
  boolean add(OtherCategory otherCategory) throws Exception;

  List<OtherCategory> list() throws Exception;

  OtherCategory get(int no) throws Exception;

  boolean update(OtherCategory otherCategory) throws Exception;

  void delete(int no) throws Exception;
}
