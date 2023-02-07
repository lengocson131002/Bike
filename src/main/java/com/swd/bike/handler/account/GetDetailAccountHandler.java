package com.swd.bike.handler.account;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.account.request.GetDetailRequest;
import com.swd.bike.dto.account.response.GetDetailResponse;
import com.swd.bike.dto.trip.QueryTripModel;
import com.swd.bike.dto.trip.TripModel;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Trip;
import com.swd.bike.service.AccountService;
import com.swd.bike.service.interfaces.ITripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetDetailAccountHandler extends RequestHandler<GetDetailRequest, GetDetailResponse> {
    private final ITripService tripService;
    private final AccountService accountService;
    @Override
    public GetDetailResponse handle(GetDetailRequest request) {
        // call service method get account role USER
        Account account = accountService.getDetailById(request.getId());
        // call service method get trip
        QueryTripModel queryTripModel = QueryTripModel.builder()
                .grabberId(account.getId())
                .passengerId(account.getId())
                .build();
        List<Trip> grabberTrips = tripService.getTripByMemberId(account.getId(), true);
        List<Trip> passengerTrips = tripService.getTripByMemberId(account.getId(), false);

        return GetDetailResponse.builder()
                .id(account.getId())
                .name(account.getName())
                .email(account.getEmail())
                .card(account.getCard())
                .phone(account.getPhone())
                .avatar(account.getAvatar())
                .averagePoint(account.getAveragePoint())
                .createdAt(account.getCreatedAt())
                .modifiedAt(account.getModifiedAt())
                .passengerOfTrips(passengerTrips.stream()
                        .map(trip -> convertTripToTripModel(trip))
                        .collect(Collectors.toList()))
                .grabberOfTrips(grabberTrips.stream()
                        .map(trip -> convertTripToTripModel(trip))
                        .collect(Collectors.toList()))
                .build();
    }

    private TripModel convertTripToTripModel(Trip trip) {
        return TripModel.builder()
                .id(trip.getId())
                .startStationId(trip.getStartStation().getId())
                .startStationName(trip.getStartStation().getName())
                .endStationId(trip.getEndStation().getId())
                .endStationName(trip.getEndStation().getName())
                .feedbackContent(trip.getFeedbackContent())
                .feedbackPoint(trip.getFeedbackPoint())
                .build();
    }
}
