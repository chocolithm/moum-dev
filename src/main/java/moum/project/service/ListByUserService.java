package moum.project.service;

import moum.project.vo.Achievement;

import java.util.List;

public interface ListByUserService {
    static void add(Achievement achievement) throws Exception;

    List<Achievement> list() throws Exception;

    static Achievement get(String id) throws Exception;

    static boolean update(Achievement achievement) throws Exception;

    static void delete(String id) throws Exception;
}
