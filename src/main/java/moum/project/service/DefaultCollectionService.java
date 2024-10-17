package moum.project.service;

import java.util.List;
import moum.project.dao.CollectionDao;
import moum.project.vo.Collection;
import org.springframework.stereotype.Service;

@Service
public class DefaultCollectionService implements CollectionService {

  CollectionDao collectionDao;

  public DefaultCollectionService(CollectionDao collectionDao) {
    this.collectionDao = collectionDao;
  }

  @Override
  public void add(Collection collection) throws Exception {

  }

  @Override
  public List<Collection> list() throws Exception {
    return collectionDao.list();
  }

  @Override
  public Collection get(int no) throws Exception {
    return null;
  }

  @Override
  public boolean update(Collection collection) throws Exception {
    return false;
  }

  @Override
  public void delete(int no) throws Exception {

  }
}
