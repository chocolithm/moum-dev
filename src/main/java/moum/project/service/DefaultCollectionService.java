package moum.project.service;

import java.util.List;

import java.util.Map;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import moum.project.dao.CollectionDao;
import moum.project.vo.Collection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultCollectionService implements CollectionService {

  @NonNull CollectionDao collectionDao;

  @Override
  @Transactional
  public void add(Collection collection) throws Exception {
    collectionDao.insert(collection);
    if (collection.getAttachedFiles().size() > 0) {
      collectionDao.insertFiles(collection);
    }
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
  @Transactional
  public boolean update(Collection collection) throws Exception {
    if (collectionDao.update(collection)) {
      if (collection.getAttachedFiles().size() > 0) {
        collectionDao.insertFiles(collection);
      }
      return true;
    }
    return false;
  }

  @Override
  @Transactional
  public void delete(int no) throws Exception {
    collectionDao.deleteFiles(no);
    collectionDao.delete(no);
  }
}
