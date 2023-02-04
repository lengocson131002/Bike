package com.swd.bike.handler.userTrip;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.userTrip.request.FinishTripRequest;
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
public class FinishTripHandler extends RequestHandler<FinishTripRequest, StatusResponse> {

    private final ITripService tripService;
    private final ContextService contextService;

    @Override
    @Transactional
    public StatusResponse handle(FinishTripRequest request) {
        Trip trip = tripService.getTrip(request.getId());
        if (trip == null) {
            throw new InternalException(ResponseCode.TRIP_ERROR_NOT_FOUND);
        }

        String currentUserId = contextService
                .getLoginUserId()
                .orElseThrow(() -> new InternalException(ResponseCode.UNAUTHORIZED_REQUEST));

        Account grabber = trip.getGrabber();
        Account passenger = trip.getPassenger();
        if (!Objects.equals(grabber.getId(), currentUserId) && !Objects.equals(passenger.getId(), currentUserId)) {
            throw new InternalException(ResponseCode.TRIP_ERROR_INVALID_ACCESS);
        }

        if (!TripStatus.ON_GOING.equals(trip.getStatus())) {
            throw new InternalException(ResponseCode.TRIP_ERROR_INVALID_STATUS);
        }

        trip.setStatus(TripStatus.FINISHED);
        trip.setFinishAt(LocalDateTime.now());

        Trip savedTrip = tripService.save(trip);

        // Todo notify

        return new StatusResponse(savedTrip != null);
    }
}
