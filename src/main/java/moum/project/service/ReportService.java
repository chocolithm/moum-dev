package moum.project.service;

import java.util.List;
import moum.project.vo.Report;
import moum.project.vo.ReportCategory;
import moum.project.vo.ReportResultCategory;

public interface ReportService {
  boolean add(Report report) throws Exception;

  List<Report> list() throws Exception;

  List<Report> listByPage(Report report, int pageNo, int pageCount) throws Exception;

  List<ReportCategory> listReportCategories() throws Exception;

  List<ReportResultCategory> listResultCategories() throws Exception;

  Report get(int no) throws Exception;

  boolean update(Report report) throws Exception;

  boolean updateResult(Report report) throws Exception;

  boolean delete(int no) throws Exception;

  int count(Report report) throws Exception;
}
