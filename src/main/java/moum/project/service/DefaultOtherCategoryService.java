package moum.project.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moum.project.dao.OtherCategoryDao;
import moum.project.vo.OtherCategory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultOtherCategoryService implements OtherCategoryService {

  private final OtherCategoryDao otherCategoryDao;

  @Override
  public boolean add(OtherCategory otherCategory) throws Exception {
    return otherCategoryDao.insert(otherCategory);
  }

  @Override
  public List<OtherCategory> list() throws Exception {
    return List.of();
  }

  @Override
  public OtherCategory get(int no) throws Exception {
    return null;
  }

  @Override
  public boolean update(OtherCategory otherCategory) throws Exception {
    return false;
  }

  @Override
  public void delete(int no) throws Exception {

  }
}
