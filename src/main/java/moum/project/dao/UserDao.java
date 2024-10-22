package moum.project.dao;


import moum.project.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {

    void insert(User user) throws Exception;

    List<User> list() throws Exception;

    User findBy(int no) throws Exception;

    User findByEmailAndPassword(@Param("email") String email, @Param("password") String password) throws Exception;

    boolean update(User user) throws Exception;

    boolean delete(int no) throws Exception;

    User findByEmail(String email);
}
