package com.swd.bike.dto.trip.response;

import com.swd.bike.core.BaseResponseData;
import com.swd.bike.entity.Trip;
import com.swd.bike.enums.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder(toBuilder = true)
public class GetTripPageResponse extends BaseResponseData {
    public Long id;
    public String passengerName;
    public String grabberName;
    public String startTime;
    public String endTime;
    public Float feedbackPoint;
    public TripStatus status;
    public String startStationName;
    public String endStationName;

}
