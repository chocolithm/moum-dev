package moum.project.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moum.project.dao.CollectionDao;
import moum.project.dao.UserDao;
import moum.project.vo.AttachedFile;
import moum.project.vo.Collection;
import moum.project.vo.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultCollectionService implements CollectionService {

  private final CollectionDao collectionDao;
  private final UserDao userDao;

  @Override
  @Transactional
  public void add(Collection collection) throws Exception {
    collectionDao.insert(collection);
    if (collection.getAttachedFiles().size() > 0) {
      collectionDao.insertFiles(collection);
    }
  }

  @Override
  public List<Collection> list(String email) throws Exception {
    User user = userDao.findByEmail(email);
    return collectionDao.list(user.getNo());
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

  @Override
  public AttachedFile getAttachedFile(int no) throws Exception {
    return collectionDao.getFile(no);
  }

  @Override
  public boolean deleteFile(int no) throws Exception {
    return collectionDao.deleteFile(no);
  }
}
