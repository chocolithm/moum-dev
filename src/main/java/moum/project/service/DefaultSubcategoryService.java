package moum.project.service;

import java.util.List;
import moum.project.dao.MaincategoryDao;
import moum.project.dao.SubcategoryDao;
import moum.project.vo.Maincategory;
import moum.project.vo.Subcategory;
import org.springframework.stereotype.Service;

@Service
public class DefaultSubcategoryService implements SubcategoryService {

  SubcategoryDao subcategoryDao;

  public DefaultSubcategoryService(SubcategoryDao subcategoryDao) {
    this.subcategoryDao = subcategoryDao;
  }

  @Override
  public void add(Subcategory subcategory) throws Exception {
    subcategoryDao.insert(subcategory);
  }

  @Override
  public List<Subcategory> list(int maincategoryNo) throws Exception {
    return subcategoryDao.list(maincategoryNo);
  }

  @Override
  public Subcategory get(int no) throws Exception {
    return null;
  }

  @Override
  public boolean update(Subcategory subcategory) throws Exception {
    return false;
  }

  @Override
  public void delete(int no) throws Exception {

  }
}
