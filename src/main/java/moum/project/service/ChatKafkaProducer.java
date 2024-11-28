package moum.project.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import moum.project.vo.Chat;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatKafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public ChatKafkaProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

//    public void sendMessage(String topic, String roomNo, String message) {
    public void sendMessage(String topic, String roomNo, Chat chat) {
        try {
            // Kafka 메시지에 roomNo 포함
            String payload = objectMapper.writeValueAsString(new KafkaMessage(roomNo, chat));
            kafkaTemplate.send(topic, payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// Kafka 메시지 DTO
class KafkaMessage {
    private String roomNo;
    private Chat message;

    public KafkaMessage(String roomNo, Chat message) {
        this.roomNo = roomNo;
        this.message = message;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public Chat getMessage() {
        return message;
    }
}
