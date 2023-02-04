package com.swd.bike.dto.userTrip.response;

import com.swd.bike.core.BaseResponseData;
import com.swd.bike.entity.Station;
import com.swd.bike.entity.Trip;
import com.swd.bike.enums.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TripResponse extends BaseResponseData {
    private Long id;
    private String title;
    private TripStatus status;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime startAt;
    private LocalDateTime finishAt;
    private LocalDateTime cancelAt;
    private Float feedbackPoint;
    private String feedbackContent;
    private Long startStationId;
    private String startStation;
    private Long endStationId;
    private String endStation;

    public TripResponse(Trip trip)  {
        assert trip != null;
        this.id = trip.getId();
        this.title = trip.getTitle();
        this.status = trip.getStatus();
        this.description = trip.getDescription();
        this.createdAt = trip.getCreatedAt();
        this.startAt = trip.getStartAt();
        this.finishAt = trip.getFinishAt();
        this.cancelAt = trip.getCancelAt();
        this.feedbackPoint = trip.getFeedbackPoint();
        this.feedbackContent = trip.getFeedbackContent();

        Station sStation = trip.getStartStation();
        if (sStation != null) {
            startStationId = sStation.getId();
            startStation = sStation.getName();
        }

        Station eStation = trip.getEndStation();
        if (eStation != null) {
            endStationId = eStation.getId();
            endStation = eStation.getName();
        }
    }
}
