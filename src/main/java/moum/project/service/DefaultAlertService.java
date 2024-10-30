package moum.project.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moum.project.vo.Alert;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultAlertService implements AlertService {


  @Override
  public boolean add(Alert alert) throws Exception {
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
  public boolean update(Alert alert) throws Exception {
    return false;
  }

  @Override
  public boolean delete(int no) throws Exception {
    return false;
  }
}
