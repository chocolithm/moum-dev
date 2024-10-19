package moum.project.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import moum.project.dao.CollectionDao;
import moum.project.dao.CollectionStatusDao;
import moum.project.vo.Collection;
import moum.project.vo.CollectionStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultCollectionStatusService implements CollectionStatusService {

  CollectionStatusDao collectionStatusDao;

  @Override
  public void add(CollectionStatus status) throws Exception {
    collectionStatusDao.insert(status);
  }

  @Override
  public List<CollectionStatus> list() throws Exception {
    return collectionStatusDao.list();
  }

  @Override
  public CollectionStatus get(int no) throws Exception {
    return null;
  }

  @Override
  public boolean update(CollectionStatus status) throws Exception {
    return false;
  }

  @Override
  public void delete(int no) throws Exception {

  }
}
