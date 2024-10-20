package moum.project.service;

import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import moum.project.dao.CollectionDao;
import moum.project.vo.Collection;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultCollectionService implements CollectionService {

  @NonNull CollectionDao collectionDao;

  @Override
  public void add(Collection collection) throws Exception {
    collectionDao.insert(collection);
  }

  @Override
  public List<Collection> list() throws Exception {
    return collectionDao.list();
  }

  @Override
  public Collection get(int no) throws Exception {
    return collectionDao.findBy(no);
  }

  @Override
  public boolean update(Collection collection) throws Exception {
    return collectionDao.update(collection);
  }

  @Override
  public void delete(int no) throws Exception {

  }
}
