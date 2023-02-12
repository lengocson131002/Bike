package com.swd.bike.kafka;


import com.swd.bike.common.BaseConstant;
import com.swd.bike.dto.notification.dtos.NotificationDto;
import com.swd.bike.service.ExpoService;
import com.swd.bike.service.ExponentPushTokenService;
import com.swd.bike.service.interfaces.INotificationService;
import com.swd.bike.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    private final SimpMessagingTemplate template;
    private final ExponentPushTokenService exponentPushTokenService;
    private final ExpoService expoService;
    private final INotificationService notificationService;

    @KafkaListener(topics = BaseConstant.KAFKA_CHANNEL_PUBLIC, groupId = "bike-app")
    public void consume(String message) {
        NotificationDto notification = JsonUtil.INSTANCE.getObject(message, NotificationDto.class);
        if (notification == null) {
            log.error("Send notification failed.");
            return;
        }
        log.info("Received public message: " + message);

        // Push notification to public
        this.pushPublic(notification);
    }

    @KafkaListener(topicPattern = BaseConstant.KAFKA_CHANNEL_USER_PATTERN, groupId = "bike-app")
    public void consume(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        NotificationDto notification = JsonUtil.INSTANCE.getObject(message, NotificationDto.class);
        if (notification == null) {
            log.error("Send notification failed.");
            return;
        }
        log.info("Received user message: " + message);

        // Push notification to user
        this.pushUser(this.getAccountId(topic), notification);
    }

    @Async
    void pushPublic(NotificationDto notificationDto) {
        // Send web socket
        template.convertAndSend("/all/notifications", notificationDto);

        // Send expo
        expoService.sendList(exponentPushTokenService.getAllTokens(), notificationDto);

        // Save notification
        notificationService.savePublic(notificationDto);
    }

    @Async
    void pushUser(String accountId, NotificationDto notificationDto) {
        // Send web socket
        template.convertAndSendToUser(accountId, "notifications", notificationDto);

        // Send expo
        expoService.sendList(exponentPushTokenService.getAllTokensByAccountId(accountId), notificationDto);

        // Save notification
        notificationService.saveUser(accountId, notificationDto);
    }

    private String getAccountId(String topic) {
        return topic.replace(BaseConstant.KAFKA_CHANNEL_USER_PREFIX, "");
    }
}

