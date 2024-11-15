package moum.project.dao;

import java.util.List;
import moum.project.vo.Alert;
import moum.project.vo.Report;
import moum.project.vo.ReportCategory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReportDao {
  boolean insert(Report Report) throws Exception;

  List<Report> list() throws Exception;

  List<ReportCategory> listReportCategories() throws Exception;

  Alert findBy(int no) throws Exception;

  boolean update(Report report) throws Exception;

  boolean delete(int no) throws Exception;
}
