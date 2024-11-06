package moum.project.service;

import java.util.List;
import moum.project.vo.Collection;
import moum.project.vo.CollectionStatus;

public interface CollectionStatusService {
  void add(CollectionStatus status) throws Exception;

  List<CollectionStatus> list() throws Exception;

  CollectionStatus get(int no) throws Exception;

  boolean update(CollectionStatus status) throws Exception;

  void delete(int no) throws Exception;

  CollectionStatus getById(int id) throws Exception;

}
