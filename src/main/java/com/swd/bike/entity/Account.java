package com.swd.bike.entity;

import com.swd.bike.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = Account.COLLECTION_NAME)
public class Account {
    public static final String COLLECTION_NAME = "account";

    @Id
    private String id;
    private String email;
    private String name;
    private String phone;
    private String avatar;
    private String card;
    private Float averagePoint;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private Boolean isUpdated = false;
}
