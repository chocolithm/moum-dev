package moum.project.service;

import java.util.List;
import moum.project.vo.AttachedFile;
import moum.project.vo.Collection;

public interface CollectionService {
  boolean add(Collection collection) throws Exception;

  List<Collection> list(int no) throws Exception;

  Collection get(int no) throws Exception;

  Collection getByFileNo(int fileNo) throws Exception;

  boolean update(Collection collection) throws Exception;

  boolean delete(int no) throws Exception;

  AttachedFile getAttachedFile(int no) throws Exception;

  boolean deleteFile(int no) throws Exception;

  boolean setPrimaryPhoto(Collection collection, int fileNo) throws Exception;

}
