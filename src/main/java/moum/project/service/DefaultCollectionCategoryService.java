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
  public Subcategory getSubcategory(int no) throws Exception {
    return categoryDao.findSubcategory(no);
  }

  @Override
  public List<Subcategory> getMaincategory(int no) throws Exception {
    return categoryDao.findMaincategory(no);
  }

  @Override
  public List<Subcategory> listSubcategoryByMaincategory(int no) throws Exception {
    return categoryDao.listSubcategoryByMaincategory(no);
  }

  @Override
  public List<Maincategory> listMaincategoryByPage(Maincategory maincategory, int pageNo, int pageCount) throws Exception {
    return categoryDao.listMaincategoryByPage(maincategory, pageNo, pageCount);
  }

  @Override
  public int count(Maincategory maincategory) throws Exception {
    return categoryDao.count(maincategory);
  }
}
