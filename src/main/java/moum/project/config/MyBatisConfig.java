package moum.project.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("moum.project.dao")  // 매퍼 인터페이스가 위치한 패키지
public class MyBatisConfig {
}
