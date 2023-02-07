package com.swd.bike.dto.station.reponse;

import com.swd.bike.core.BaseResponseData;
import com.swd.bike.dto.station.StationModel;
import com.swd.bike.dto.trip.TripModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public class GetDetailResponse extends BaseResponseData {
    private Long id;
    private String name;
    private String address;
    private String description;
    private Float longitude;
    private Float latitude;
    private List<StationModel> nextStations;
}
