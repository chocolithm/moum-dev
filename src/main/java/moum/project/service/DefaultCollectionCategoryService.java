package moum.project.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moum.project.dao.CollectionCategoryDao;
import moum.project.vo.Maincategory;
import moum.project.vo.Subcategory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultCollectionCategoryService implements CollectionCategoryService {

  private final CollectionCategoryDao categoryDao;

  @Override
  public List<Subcategory> listSubcategory(int maincategoryNo) throws Exception {
    return categoryDao.listSubcategory(maincategoryNo);
  }

  @Override
  public List<Maincategory> listMaincategory() throws Exception {
    return categoryDao.listMaincategory();
  }

  @Override
  public List<Subcategory> listByPage(int pageNo, int pageCount) throws Exception {
    return categoryDao.listByPage(pageNo, pageCount);
  }

  @Override
  public int count() throws Exception {
    return categoryDao.count();
  }
}
