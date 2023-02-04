package com.swd.bike.handler.userTrip;

import com.swd.bike.common.BaseConstant;
import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.userTrip.request.CancelTripRequest;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Trip;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.enums.TripStatus;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.ContextService;
import com.swd.bike.service.interfaces.ITripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class CancelTripHandler extends RequestHandler<CancelTripRequest, StatusResponse> {

    private final ITripService tripService;
    private final ContextService contextService;

    @Override
    @Transactional
    public StatusResponse handle(CancelTripRequest request) {
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

        if (!TripStatus.CREATED.equals(trip.getStatus())) {
            throw new InternalException(ResponseCode.TRIP_ERROR_INVALID_STATUS);
        }

        LocalDateTime startTime = trip.getPost().getStartTime();
        if (Duration.between(LocalDateTime.now(), startTime).toMinutes() < BaseConstant.TRIP_CANCEL_THRESHOLD_IN_MINUTES) {
            throw new InternalException(ResponseCode.TRIP_ERROR_CAN_NOT_CANCEL_TRIP);
        }

        trip.setStatus(TripStatus.CANCELED);
        trip.setCancelAt(LocalDateTime.now());

        // Todo notify for partner

        Trip savedTrip = tripService.save(trip);

        return new StatusResponse(savedTrip != null);
    }
}
