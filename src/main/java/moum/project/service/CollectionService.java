package moum.project.service;

import java.util.List;
import moum.project.vo.AttachedFile;
import moum.project.vo.Collection;

public interface CollectionService {
  void add(Collection collection) throws Exception;

  List<Collection> list(String email) throws Exception;

  Collection get(int no) throws Exception;

  boolean update(Collection collection) throws Exception;

  void delete(int no) throws Exception;

  AttachedFile getAttachedFile(int no) throws Exception;

  boolean deleteFile(int no) throws Exception;
}
