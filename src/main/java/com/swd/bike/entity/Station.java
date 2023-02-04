package com.swd.bike.entity;

import com.swd.bike.enums.StationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

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
    private StationStatus status = StationStatus.ACTIVE;

    @ManyToMany
    @JoinTable(
            name = "station_next_station",
            joinColumns = @JoinColumn(name = "station_id"),
            inverseJoinColumns = @JoinColumn(name = "next_station_id"))
    private List<Station> nextStation;

}
