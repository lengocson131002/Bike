package com.swd.bike.handler.index;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.ListResponse;
import com.swd.bike.dto.index.GetStationsRequest;
import com.swd.bike.dto.userPost.response.StationResponse;
import com.swd.bike.entity.Station;
import com.swd.bike.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetStationHandler extends RequestHandler<GetStationsRequest, ListResponse<StationResponse>> {

    private final StationRepository stationRepository;

    @Override
    public ListResponse<StationResponse> handle(GetStationsRequest request) {
        List<Station> stations = stationRepository.findAll(request.getSpecifications());
        ListResponse<StationResponse> response = new ListResponse<>();
        response.setItems(
                stations.stream()
                        .map(StationResponse::new)
                        .collect(Collectors.toList())
        );
        return response;
    }
}
