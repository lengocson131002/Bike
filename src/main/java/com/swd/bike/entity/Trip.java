package com.swd.bike.entity;


import com.swd.bike.enums.TripRole;
import com.swd.bike.enums.TripStatus;
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
@Table(name = Trip.COLLECTION_NAME)
public class Trip extends Auditable<String> {
    public static final String COLLECTION_NAME = "trip";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rider_id")
    private Account rider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grabber_id")
    private Account grabber;

    private TripStatus status = TripStatus.CREATED;

    private LocalDateTime startAt;

    private LocalDateTime finishAt;

    private Float feedbackPoint;

    private String feedbackContent;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "id", column = @Column(name = "start_station_id")),
            @AttributeOverride( name = "name", column = @Column(name = "start_station_name")),
            @AttributeOverride( name = "address", column = @Column(name = "start_station_address")),
            @AttributeOverride( name = "longitude", column = @Column(name = "start_station_longitude")),
            @AttributeOverride( name = "latitude", column = @Column(name = "start_station_latitude"))
    })
    private TripStation startStation;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "id", column = @Column(name = "end_station_id")),
            @AttributeOverride( name = "name", column = @Column(name = "end_station_name")),
            @AttributeOverride( name = "address", column = @Column(name = "end_station_address")),
            @AttributeOverride( name = "longitude", column = @Column(name = "end_station_longitude")),
            @AttributeOverride( name = "latitude", column = @Column(name = "end_station_latitude"))
    })
    private TripStation endStation;

}
