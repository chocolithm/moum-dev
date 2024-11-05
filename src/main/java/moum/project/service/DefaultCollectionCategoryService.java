package moum.project.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moum.project.dao.CollectionCategoryDao;
import moum.project.vo.Maincategory;
import moum.project.vo.Subcategory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultCollectionCategoryService implements CollectionCategoryService {

  private final CollectionCategoryDao collectionCategoryDao;

  @Override
  public List<Subcategory> list(int maincategoryNo) throws Exception {
    return collectionCategoryDao.list(maincategoryNo);
  }

  @Override
  public List<Maincategory> list() throws Exception {
    return List.of();
  }
}
