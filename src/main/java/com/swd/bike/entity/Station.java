package com.swd.bike.entity;

import com.swd.bike.enums.StationStatus;
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
@Table(name = Station.COLLECTION_NAME)
public class Station extends Auditable<String> {
    public static final String COLLECTION_NAME = "station";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String description;
    private Float longitude;
    private Float latitude;
    @Enumerated(EnumType.STRING)
    private StationStatus status;
}
