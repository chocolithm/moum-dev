package moum.project.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moum.project.dao.AlertDao;
import moum.project.vo.Alert;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultAlertService implements AlertService {

  private final AlertDao alertDao;

  @Override
  public boolean add(Alert alert) throws Exception {
    return alertDao.insert(alert);
  }

  @Override
  public List<Alert> list(int no) throws Exception {
    return List.of();
  }

  @Override
  public List<Alert> listByUser(int userNo, int pageNo, int pageCount) throws Exception {
    return alertDao.listByUser(userNo, pageNo, pageCount);
  }

  @Override
  public Alert get(int no) throws Exception {
    return null;
  }

  @Override
  public boolean updateRead(int no) throws Exception {
    return alertDao.updateRead(no);
  }

  @Override
  public boolean delete(int no) throws Exception {
    return alertDao.delete(no);
  }
}
