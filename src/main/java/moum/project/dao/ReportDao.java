package moum.project.dao;

import java.util.List;
import moum.project.vo.Report;
import moum.project.vo.ReportCategory;
import moum.project.vo.ReportResultCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReportDao {
  boolean insert(Report Report) throws Exception;

  List<Report> list() throws Exception;

  List<Report> listByPage(@Param("pageNo") int pageNo, @Param("pageCount") int pageCount) throws Exception;

  List<ReportCategory> listReportCategories() throws Exception;

  List<ReportResultCategory> listResultCategories() throws Exception;

  Report findBy(int no) throws Exception;

  boolean update(Report report) throws Exception;

  boolean updateResult(Report report) throws Exception;

  boolean delete(int no) throws Exception;

  int count() throws Exception;
}
