package moum.project.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * packageName    : moum.project.service
 * fileName       : MailService
 * author         : narilee
 * date           : 24. 10. 31.
 * description    : 이메일 인증 관련 서비스를 제공하는 클래스입니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 10. 31.        narilee       최초 생성
 */
@Service
@RequiredArgsConstructor
public class MailService {

  /**
   * 메일 발송을 위한 JavaMailSender 인스턴스
   */
  private final JavaMailSender javaMailSender;

  /**
   * 발신자 이메일 주소
   */
  @Value("${email.username}")
  private String senderEmail;

  /**
   * 8자리 랜덤 인증 코드를 생성합니다.
   *
   * @return 생성된 8자리 인증 코드
   */
  public String createNumber() {
    Random random = new Random();
    StringBuilder key = new StringBuilder();

    for (int i = 0; i < 8; i++) { // 인증 코드 8자리
      int index = random.nextInt(3); // 0~2까지 랜덤, 랜덤값으로 switch문 실행

      switch (index) {
        case 0 -> key.append((char) (random.nextInt(26) + 97)); // 소문자
        case 1 -> key.append((char) (random.nextInt(26) + 65)); // 대문자
        case 2 -> key.append(random.nextInt(10)); // 숫자
      }
    }
    return key.toString();
  }

  /**
   * 인증 메일 메시지를 생성합니다.
   *
   * @param mail 수신자 이메일 주소
   * @param number 인증 번호
   * @return 생선된 MailMessage 객체
   * @throws MessagingException 메일 메시지 생성 중 오류 발생 시
   */
  public MimeMessage createMail(String mail, String number) throws MessagingException {
    MimeMessage message = javaMailSender.createMimeMessage();

    message.setFrom(senderEmail);
    message.setRecipients(MimeMessage.RecipientType.TO, mail);
    message.setSubject("moum의 이메일 인증");
    String body = "";
    body += "<h3>요청하신 인증 번호입니다.</h3>";
    body += "<h1>" + number + "</h1>";
    body += "<h3>인증 번호는 3분간 유효합니다.</h3>";
    message.setText(body, "UTF-8", "html");

    return message;
  }

  /**
   * 인증 메일을 생성하고 발송합니다.
   *
   * @param sendEmail 수신자 이메일 주소
   * @return 생성된 인증 번호
   * @throws MessagingException 메일 메시지 생성 중 오류 발생 시
   */
  public String sendSimpleMessage(String sendEmail) throws MessagingException {
    String number = createNumber(); // 랜덤 인증번호 생성

    MimeMessage message = createMail(sendEmail, number); // 메일 생성
    try {
      javaMailSender.send(message); // 메일 발송
    } catch (MailException e) {
      e.printStackTrace();
      throw new IllegalArgumentException("메일 발송 중 오류가 발생했습니다.");
    }

    return number; // 생성된 인증번호 반환
  }
}
