package moum.project.dao;

import java.util.List;
import moum.project.vo.Alert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AlertDao {
  boolean insert(Alert alert) throws Exception;

  List<Alert> list() throws Exception;

  List<Alert> listByUser(
      @Param("userNo") int userNo,
      @Param("pageNo") int pageNo,
      @Param("pageCount") int pageCount
  ) throws Exception;

  Alert findBy(int no) throws Exception;

  boolean updateTime(int no) throws Exception;

  boolean updateRead(int no) throws Exception;

  boolean delete(int no) throws Exception;

  int exists(Alert alert) throws Exception;

}
