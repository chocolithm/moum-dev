package moum.project.service;

import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import moum.project.dao.CollectionDao;
import moum.project.dao.CollectionStatusDao;
import moum.project.vo.Collection;
import moum.project.vo.CollectionStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultCollectionStatusService implements CollectionStatusService {

  @NonNull CollectionStatusDao collectionStatusDao;

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

  // ID로 CollectionStatus 조회 메서드
  @Override
  public CollectionStatus getById(int id) throws Exception {
    CollectionStatus status = collectionStatusDao.findById(id);
    if (status == null) {
      throw new IllegalArgumentException("해당 상태를 찾을 수 없습니다: " + id);
    }
    return status;
  }
}
