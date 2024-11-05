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
  public List<Alert> listByUser(int userNo, int pageNo, int pageCount) throws Exception {
    return alertDao.listByUser(userNo, pageNo, pageCount);
  }

  @Override
  public boolean updateTime(int no) throws Exception {
    return alertDao.updateTime(no);
  }

  @Override
  public boolean updateRead(int no) throws Exception {
    return alertDao.updateRead(no);
  }

  @Override
  public boolean updateReadByCategoryAndUser(Alert alert) throws Exception {
    return alertDao.updateReadByCategoryAndUser(alert);
  }

  @Override
  public boolean delete(int no) throws Exception {
    return alertDao.delete(no);
  }

  @Override
  public int exists(Alert alert) throws Exception {
    return alertDao.exists(alert);
  }

  @Override
  public int countUnreadByUser(int no) throws Exception {
    return alertDao.countUnreadByUser(no);
  }
}
