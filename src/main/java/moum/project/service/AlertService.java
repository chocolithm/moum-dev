package moum.project.service;

import java.util.List;
import moum.project.vo.Alert;

public interface AlertService {
  boolean add(Alert alert) throws Exception;

  List<Alert> list(int no) throws Exception;

  Alert get(int no) throws Exception;

  boolean update(Alert alert) throws Exception;

  boolean delete(int no) throws Exception;
}
