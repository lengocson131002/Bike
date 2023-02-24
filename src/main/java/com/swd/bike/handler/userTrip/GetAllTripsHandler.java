package com.swd.bike.handler.userTrip;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.dto.userTrip.request.GetAllTripsRequest;
import com.swd.bike.dto.userTrip.response.TripResponse;
import com.swd.bike.entity.Trip;
import com.swd.bike.service.interfaces.ITripService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetAllTripsHandler extends RequestHandler<GetAllTripsRequest, PageResponse<TripResponse>> {

    private final ITripService tripService;

    @Override
    @Transactional
    public PageResponse<TripResponse> handle(GetAllTripsRequest request) {
        Page<Trip> pageResult = tripService.getAllTrip(request.getSpecification(), request.getPageable());
        PageResponse<TripResponse> response = new PageResponse<>(pageResult);
        response.setItems(pageResult.getContent()
                .stream()
                .map(TripResponse::new)
                .collect(Collectors.toList()));
        return response;
    }
}
