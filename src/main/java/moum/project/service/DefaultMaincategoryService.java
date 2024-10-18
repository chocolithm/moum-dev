package moum.project.service;

import java.util.List;
import moum.project.dao.MaincategoryDao;
import moum.project.vo.Maincategory;
import org.springframework.stereotype.Service;

@Service
public class DefaultMaincategoryService implements MaincategoryService {

  MaincategoryDao maincategoryDao;

  public DefaultMaincategoryService(MaincategoryDao maincategoryDao) {
    this.maincategoryDao = maincategoryDao;
  }

  @Override
  public void add(Maincategory maincategory) throws Exception {
    maincategoryDao.insert(maincategory);
  }

  @Override
  public List<Maincategory> list() throws Exception {
    return maincategoryDao.list();
  }

  @Override
  public Maincategory get(int no) throws Exception {
    return null;
  }

  @Override
  public boolean update(Maincategory maincategory) throws Exception {
    return false;
  }

  @Override
  public void delete(int no) throws Exception {

  }
}
