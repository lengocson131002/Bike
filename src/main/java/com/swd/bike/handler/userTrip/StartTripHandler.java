package com.swd.bike.handler.userTrip;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.userTrip.request.StartTripRequest;
import com.swd.bike.dto.userTrip.response.TripResponse;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Trip;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.enums.TripStatus;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.ContextService;
import com.swd.bike.service.interfaces.ITripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class StartTripHandler extends RequestHandler<StartTripRequest, StatusResponse> {

    private final ITripService tripService;

    private final ContextService contextService;

    @Override
    @Transactional
    public StatusResponse handle(StartTripRequest request) {
        Trip trip = tripService.getTrip(request.getId());
        if (trip == null) {
            throw new InternalException(ResponseCode.TRIP_ERROR_NOT_FOUND);
        }

        Account currentUser = contextService.getLoggedInUser();

        Account grabber = trip.getGrabber();
        Account passenger = trip.getPassenger();
        if (!Objects.equals(grabber.getId(), currentUser.getId()) && !Objects.equals(passenger.getId(), currentUser.getId())) {
            throw new InternalException(ResponseCode.TRIP_ERROR_INVALID_ACCESS);
        }

        if (!TripStatus.CREATED.equals(trip.getStatus())) {
            throw new InternalException(ResponseCode.TRIP_ERROR_INVALID_STATUS);
        }

        // Check current trip
        Trip onGoingTrip = tripService.getCurrentTrip(currentUser);
        if (onGoingTrip !=  null) {
            throw new InternalException(ResponseCode.TRIP_ERROR_ON_GOING_TRIP);
        }

        trip.setStatus(TripStatus.ON_GOING);
        trip.setStartAt(LocalDateTime.now());

        Trip savedTrip = tripService.save(trip);

        return new StatusResponse(savedTrip != null);
    }
}
