package com.swd.bike.entity;

import com.swd.bike.enums.NotificationStatus;
import com.swd.bike.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = Notification.COLLECTION_NAME)
public class Notification {
    public static final String COLLECTION_NAME = "notification";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private NotificationType type;

    private String content;

    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    private NotificationStatus status = NotificationStatus.UN_READ;
}
