package moum.project.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatKafkaConsumer {
    private final SimpMessagingTemplate messagingTemplate;

    public ChatKafkaConsumer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "chat-topic", groupId = "chat-group")
    public void consumeMessage(String message) {
        // WebSocket 클라이언트로 메시지 전달
        messagingTemplate.convertAndSend("/receive/chat", message);
    }
}
