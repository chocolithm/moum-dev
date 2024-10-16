package moum.project.moum;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("moum.project.dao")
@SpringBootApplication
public class MoumApplication {

  public static void main(String[] args) {
    SpringApplication.run(MoumApplication.class, args);
  }

}
