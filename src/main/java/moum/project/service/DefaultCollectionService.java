package moum.project.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moum.project.dao.CollectionDao;
import moum.project.dao.OtherCategoryDao;
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
  private final OtherCategoryDao otherCategoryDao;

  @Override
  @Transactional
  public boolean add(Collection collection) throws Exception {
    if (collectionDao.insert(collection)) {
      if (collection.getAttachedFiles().size() > 0) {
        collectionDao.insertFiles(collection);
      }
      if (!collection.getOtherCategory().getName().isEmpty()) {
        otherCategoryDao.insert(collection.getOtherCategory());
      }
      return true;
    }
    return false;
  }

  @Override
  public List<Collection> list(int no) throws Exception {
    User user = userDao.findBy(no);
    return collectionDao.list(user.getNo());
  }

  @Override
  public Collection get(int no) throws Exception {
    return collectionDao.findBy(no);
  }

  @Override
  public Collection getByFileNo(int fileNo) throws Exception {
    return collectionDao.findByFileNo(fileNo);
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
  public boolean delete(int no) throws Exception {
    collectionDao.deleteFiles(no);
    if (collectionDao.delete(no)) {
      return true;
    }
    return false;
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
