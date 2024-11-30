package moum.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class KafkaToWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;

    public KafkaToWebSocketService(SimpMessagingTemplate messagingTemplate, ObjectMapper objectMapper) {
        this.messagingTemplate = messagingTemplate;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "chat-topic", groupId = "chat-group")
    public void consumeMessage(String payload) {
        try {
            // Kafka 메시지 파싱
            KafkaMessage message = objectMapper.readValue(payload, KafkaMessage.class);

            // WebSocket 경로로 메시지 전달
            messagingTemplate.convertAndSend("/receive/chat/" + message.getRoomNo(), message.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

