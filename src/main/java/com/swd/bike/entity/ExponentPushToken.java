package com.swd.bike.entity;

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
@Table(name = ExponentPushToken.COLLECTION_NAME)
public class ExponentPushToken {

    public static final String COLLECTION_NAME = "expo_token";
    @Id
    private String token;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

}
