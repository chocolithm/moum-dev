package moum.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("moum.project.dao")
@SpringBootApplication
public class MoumApplication {

  public static void main(String[] args) {
    SpringApplication.run(MoumApplication.class, args);
  }

}
