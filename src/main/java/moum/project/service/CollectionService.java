package moum.project.service;

import java.util.List;
import moum.project.vo.Collection;

public interface CollectionService {
  void add(Collection collection) throws Exception;

  List<Collection> list() throws Exception;

  Collection get(int no) throws Exception;

  boolean update(Collection collection) throws Exception;

  void delete(int no) throws Exception;
}
