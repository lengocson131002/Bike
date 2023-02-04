package com.swd.bike.handler.userTrip;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.userTrip.request.FeedbackTripRequest;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Trip;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.enums.TripStatus;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.ContextService;
import com.swd.bike.service.interfaces.IAccountService;
import com.swd.bike.service.interfaces.ITripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FeedbackTripHandler extends RequestHandler<FeedbackTripRequest, StatusResponse> {

    private final ITripService tripService;
    private final ContextService contextService;
    private final IAccountService accountService;

    @Override
    @Transactional
    public StatusResponse handle(FeedbackTripRequest request) {
        Trip trip = tripService.getTrip(request.getId());
        if (trip == null) {
            throw new InternalException(ResponseCode.TRIP_ERROR_NOT_FOUND);
        }

        String currentUserId = contextService
                .getLoginUserId()
                .orElseThrow(() -> new InternalException(ResponseCode.UNAUTHORIZED_REQUEST));

        if (!Objects.equals(trip.getPassenger().getId(), currentUserId)) {
            throw new InternalException(ResponseCode.TRIP_ERROR_INVALID_ACCESS);
        }

        if (!TripStatus.FINISHED.equals(trip.getStatus())) {
            throw new InternalException(ResponseCode.TRIP_ERROR_INVALID_STATUS);
        }

        if (trip.getFeedbackPoint() != null) {
            throw new InternalException(ResponseCode.TRIP_ERROR_EXISTED_FEEDBACK);
        }

        Float point = request.getPoint();
        String content = request.getContent();

        // Update average point
        Account grabber = trip.getGrabber();
        Float currentAvgPoint = grabber.getAveragePoint();
        if (currentAvgPoint == null) {
            currentAvgPoint = 0f;
        }

        Integer noFeedbackedTrip = tripService.countFeedbackedTrip(grabber);
        Float newAvgPoint = Math.round(((currentAvgPoint * noFeedbackedTrip + point) / (noFeedbackedTrip + 1)) * 10) / 10.0f;

        grabber.setAveragePoint(newAvgPoint);
        accountService.save(grabber);

        trip.setFeedbackPoint(point);
        trip.setFeedbackContent(content);
        Trip savedTrip = tripService.save(trip);

        return new StatusResponse(savedTrip != null);
    }
}
