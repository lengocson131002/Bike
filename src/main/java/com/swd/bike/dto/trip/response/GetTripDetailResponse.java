package com.swd.bike.dto.trip.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swd.bike.core.BaseResponseData;
import com.swd.bike.enums.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class GetTripDetailResponse extends BaseResponseData {
    public Long id;
    public String passengerId;
    public String passengerName;
    public String grabberId;
    public String grabberName;
    public String startTime;

    public String endTime;
    public String cancelTime;
    public Float feedbackPoint;
    public String feedbackContent;
    public TripStatus status;
    public Long startStationId;
    public String startStationName;
    public Long endStationId;
    public String endStationName;
}
