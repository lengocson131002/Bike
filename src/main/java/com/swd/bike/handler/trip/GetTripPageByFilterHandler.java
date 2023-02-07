package com.swd.bike.handler.trip;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.dto.trip.request.GetPageByFilterRequest;
import com.swd.bike.dto.trip.response.GetTripPageResponse;
import com.swd.bike.entity.Trip;
import com.swd.bike.service.interfaces.ITripService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetTripPageByFilterHandler extends RequestHandler<GetPageByFilterRequest, PageResponse<GetTripPageResponse>> {
    private final ITripService tripService;
    @Override
    public PageResponse<GetTripPageResponse> handle(GetPageByFilterRequest request) {
        Page<Trip> trips = tripService.getTripPageByFilter(request.getSpecification(),request.getPageable());

        PageResponse<GetTripPageResponse> tripPageResponse = new PageResponse<>(trips);

        tripPageResponse.setItems(trips.getContent().stream()
                .map(trip -> this.convertEntityToDTO(trip))
                .collect(Collectors.toList()));

        return tripPageResponse;
    }

    private GetTripPageResponse convertEntityToDTO(Trip trip) {
        return GetTripPageResponse.builder()
                .id(trip.getId())
                .passengerName(trip.getPassenger().getName())
                .grabberName(trip.getGrabber().getName())
                .startTime(OffsetDateTime.of(trip.getStartAt(), ZoneOffset.UTC).toString())
                .endTime(OffsetDateTime.of(trip.getFinishAt(), ZoneOffset.UTC).toString())
                .feedbackPoint(trip.getFeedbackPoint())
                .status(trip.getStatus())
                .startStationName(trip.getStartStation().getName())
                .endStationName(trip.getEndStation().getName())
                .build();
    }
}
