package moum.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatKafkaConsumer {
    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;

    public ChatKafkaConsumer(SimpMessagingTemplate messagingTemplate, ObjectMapper objectMapper) {
        this.messagingTemplate = messagingTemplate;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "chat-topic", groupId = "chat-group")
    public void consumeMessage(String message) {
        try {
            // Kafka 메시지 파싱
            KafkaMessage kafkaMessage = objectMapper.readValue(message, KafkaMessage.class);

            // 특정 roomNo에 메시지 전달
            messagingTemplate.convertAndSend(
                "/receive/chat/" + kafkaMessage.getRoomNo(),
                kafkaMessage.getMessage()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
