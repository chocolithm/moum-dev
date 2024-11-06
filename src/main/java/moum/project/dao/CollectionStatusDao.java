package moum.project.dao;

import java.util.List;
import moum.project.vo.Collection;
import moum.project.vo.CollectionStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CollectionStatusDao {
  boolean insert(CollectionStatus status) throws Exception;

  List<CollectionStatus> list() throws Exception;

  CollectionStatus findBy(int no) throws Exception;

  boolean update(CollectionStatus status) throws Exception;

  boolean delete(int no) throws Exception;
  
  CollectionStatus findById(@Param("id") int id) throws Exception;


}
