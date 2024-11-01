package moum.project.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moum.project.dao.ReportDao;
import moum.project.vo.Alert;
import moum.project.vo.Report;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultReportService implements ReportService {

  private final ReportDao reportDao;

  @Override
  public boolean add(Report report) throws Exception {
    return false;
  }

  @Override
  public List<Alert> list(int no) throws Exception {
    return List.of();
  }

  @Override
  public Alert get(int no) throws Exception {
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
