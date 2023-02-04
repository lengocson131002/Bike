package com.swd.bike.service.interfaces;

import com.swd.bike.entity.Station;

public interface IStationService {
    boolean isActiveStation(Station station);
    Station findStation(Long id);
}
