package moum.project.service;

import java.util.List;
import moum.project.vo.Maincategory;
import moum.project.vo.Subcategory;

public interface CollectionCategoryService {
  List<Subcategory> list(int maincategoryNo) throws Exception;

  List<Maincategory> list() throws Exception;
}
