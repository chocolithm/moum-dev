package moum.project.dao;

import java.util.List;
import moum.project.vo.Alert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AlertDao {
  boolean insert(Alert alert) throws Exception;

  List<Alert> list() throws Exception;

  Alert findBy(int no) throws Exception;

  boolean update(Alert alert) throws Exception;

  boolean delete(int no) throws Exception;
}
