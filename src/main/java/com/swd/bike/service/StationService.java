package com.swd.bike.service;

import com.swd.bike.entity.Station;
import com.swd.bike.enums.StationStatus;
import com.swd.bike.repository.StationRepository;
import com.swd.bike.service.interfaces.IStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StationService implements IStationService {
    private final StationRepository stationRepository;

    @Override
    public boolean isActiveStation(Station station) {
        return station != null && StationStatus.ACTIVE.equals(station.getStatus());
    }

    @Override
    public Station findStation(Long id) {
        if (id == null) {
            return null;
        }
        return stationRepository.findById(id)
                .orElse(null);
    }
}
