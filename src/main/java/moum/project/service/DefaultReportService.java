package moum.project.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moum.project.dao.ReportDao;
import moum.project.vo.Alert;
import moum.project.vo.Report;
import org.springframework.stereotype.Service;

@Service
public class DefaultReportService implements ReportService {

  private final ReportDao reportDao;

  public DefaultReportService(ReportDao reportDao) {
    this.reportDao = reportDao;
  }

  public List<Report> list() throws Exception {
    return reportDao.list();
  }


  @Override
  public boolean add(Report report) throws Exception {
    return false;
  }


  @Override
  public Report get(int no) throws Exception {
    return null;
  }

  @Override
  public boolean update(Report report) throws Exception {
    return false;
  }

  @Override
  public boolean delete(int no) throws Exception {
    return false;

  }
}
