package com.swd.bike.handler.station;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.post.QueryPostModel;
import com.swd.bike.dto.station.reponse.CreateStationResponse;
import com.swd.bike.dto.station.reponse.UpdateStationResponse;
import com.swd.bike.dto.station.request.CreateStationRequest;
import com.swd.bike.dto.station.request.UpdateStationRequest;
import com.swd.bike.dto.trip.QueryTripModel;
import com.swd.bike.entity.Station;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.enums.StationStatus;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.IPostService;
import com.swd.bike.service.interfaces.IStationService;
import com.swd.bike.service.interfaces.ITripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateStationHandler extends RequestHandler<UpdateStationRequest, UpdateStationResponse> {
    private final IStationService stationService;
    private final ITripService tripService;
    private final IPostService postService;
    private final CreateStationHandler createStationHandler;

    @Override
    public UpdateStationResponse handle(UpdateStationRequest request) {
        boolean isUsed = tripService.checkExists(QueryTripModel.builder()
                .startStationId(request.getId())
                .endStationId(request.getId())
                .build()
                .getTripByNotDoneStatusAndStartStationIdOrEndStationId())
                && postService.isExistWithActiveStation(QueryPostModel.builder()
                .endStationId(request.getId())
                .startStationId(request.getId())
                .build()
                .getPostByNotDoneStatusAndStartStationIdOrEndStationId());

        if (isUsed) {
            throw new InternalException(ResponseCode.STATION_IS_USED);
        }
        Station updatedStation = stationService.getStationById(request.getId());
        if (updatedStation.getStatus().equals(StationStatus.INACTIVE) || (request.getNextStationIds().size() > 0 && !stationService.checkStationsActive(request.getNextStationIds()))) {
            throw new InternalException(ResponseCode.STATION_IS_INACTIVE);
        }
        updatedStation.setStatus(StationStatus.INACTIVE);
        stationService.createOrUpdate(updatedStation);

        CreateStationRequest createStationRequest = new CreateStationRequest();

        createStationRequest.setAddress(request.getAddress());
        createStationRequest.setName(request.getName());
        createStationRequest.setLongitude(request.getLongitude());
        createStationRequest.setLatitude(request.getLatitude());
        createStationRequest.setDescription(request.getDescription());
        createStationRequest.setNextStationIds(request.getNextStationIds());
        CreateStationResponse station = createStationHandler.handle(createStationRequest);

        UpdateStationResponse response = UpdateStationResponse.builder()
                .id(station.getId())
                .name(station.getName())
                .address(station.getAddress())
                .longitude(station.getLongitude())
                .latitude(station.getLatitude())
                .description(station.getDescription())
                .nextStationIds(request.getNextStationIds())
                .build();
        return response;
    }
}
