package com.swd.bike.handler.trip;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.trip.request.GetTripDetailRequest;
import com.swd.bike.dto.trip.response.GetTripDetailResponse;
import com.swd.bike.entity.Trip;
import com.swd.bike.service.interfaces.ITripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetDetailTripHandler extends RequestHandler<GetTripDetailRequest, GetTripDetailResponse> {
    private final ITripService tripService;
    @Override
    public GetTripDetailResponse handle(GetTripDetailRequest request) {
        Trip trip = tripService.getDetailById(request.getId());
        return GetTripDetailResponse.builder()
                .id(trip.getId())
                .passengerName(trip.getPassenger().getName())
                .grabberName(trip.getGrabber().getName())
                .startTime(OffsetDateTime.of(trip.getStartAt(), ZoneOffset.UTC).toString())
                .endTime(OffsetDateTime.of(trip.getFinishAt(), ZoneOffset.UTC).toString())
                .feedbackContent(trip.getFeedbackContent())
                .feedbackPoint(trip.getFeedbackPoint())
                .status(trip.getStatus())
                .startStationName(trip.getStartStation().getName())
                .endStationName(trip.getEndStation().getName())
                .build();
    }
}
