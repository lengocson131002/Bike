package com.swd.bike.dto.account.response;

import com.swd.bike.core.BaseResponseData;
import com.swd.bike.dto.trip.TripModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public class GetDetailResponse extends BaseResponseData {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String avatar;
    private String card;
    private Float averagePoint;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<TripModel> grabberOfTrips;
    private List<TripModel> passengerOfTrips;
}
