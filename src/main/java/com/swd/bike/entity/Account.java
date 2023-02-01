package com.swd.bike.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    private Boolean isUpdated = false;
}
