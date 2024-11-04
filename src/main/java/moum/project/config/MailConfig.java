package moum.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * packageName    : moum.project.config
 * fileName       : MailConfig
 * author         : narilee
 * date           : 24. 11. 4.
 * description    : 메일 발송을 위한 JavaMailSender 설정 클래스입니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 11. 4.        narilee       최초 생성
 */
@Configuration
@PropertySource("classpath:config/mail.properties")
public class MailConfig {

  /**
   *  Gmail 계정 이메일 주소
   */
  @Value("${email.username}")
  private String emailUsername;

  /**
   * Gmail 계정 앱 비밀번호
   */
  @Value("${email.password}")
  private String emailPassword;

  /**
   * JavaMailSender 빈을 생성하고 설정합니다.
   *
   * @return 설정이 완료된 JavaMailSender 인스턴스
   */
  @Bean
  public JavaMailSender javaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.gmail.com");
    mailSender.setPort(587);
    mailSender.setUsername(emailUsername);
    mailSender.setPassword(emailPassword);

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "true");

    return mailSender;
  }
}
