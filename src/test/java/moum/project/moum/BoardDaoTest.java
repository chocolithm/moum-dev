//package moum.project.moum;
//
//import moum.project.dao.BoardDao;
//import moum.project.dao.CollectionDao;
//import moum.project.vo.Board;
//import moum.project.vo.Collection;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.List;
//
//@SpringBootTest
//public class BoardDaoTest {
//
//    @Autowired
//    private BoardDao boardDao;  // CollectionDao를 Autowired로 주입
//
//    @Test
//    public void testList() throws Exception {
//        // 리스트 조회 테스트
//        List<Board> boards = boardDao.list();
//        assertNotNull(boards);  // 조회된 리스트가 null이 아닌지 확인
//        assertFalse(boards.isEmpty());  // 리스트가 비어있지 않은지 확인
//
//        // 출력하여 확인
//        boards.forEach(System.out::println);
//    }
//}
