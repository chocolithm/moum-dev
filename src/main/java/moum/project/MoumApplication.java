package moum.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("moum.project.dao")
//@PropertySource("classpath:/config/ncp.properties")
@SpringBootApplication
public class MoumApplication {

  public MoumApplication() {
    // AWS 관련 경고 제거
    System.setProperty("aws.java.v1.disableDeprecationAnnouncement", "true");
  }

  public static void main(String[] args) {
    SpringApplication.run(MoumApplication.class, args);
  }

}
