package com.swd.bike.service;

import com.swd.bike.entity.Station;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.enums.StationStatus;
import com.swd.bike.exception.InternalException;
import com.swd.bike.repository.StationRepository;
import com.swd.bike.service.interfaces.IStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class StationService implements IStationService {
    private final StationRepository stationRepository;

    @Override
    public Station createOrUpdate(Station station) {
        return stationRepository.save(station);
    }

    @Override
    public Station getRefById(Long id) {
        return stationRepository.getReferenceById(id);
    }

    @Override
    public Station getStationById(Long id) {
        return stationRepository.findById(id).orElseThrow(() -> new InternalException(ResponseCode.STATION_NOT_FOUND));
    }

    @Override
    public boolean checkStationActive(Long id) {
        Station station = stationRepository.findStationByIdAndStatus(id, StationStatus.ACTIVE).orElse(null);
        return station != null;
    }

    @Override
    public boolean checkStationsActive(List<Long> ids) {
        List<Station> stations = stationRepository.findAllById(ids);
        if (stations == null || stations.isEmpty()) {
            throw new InternalException(ResponseCode.STATION_NOT_FOUND);
        }
        return stations != null && !stations.isEmpty() && stations.stream().allMatch(station -> station.getStatus().equals(StationStatus.ACTIVE));
    }

    @Override
    public Page<Station> getStationPage(Specification<Station> specification, Pageable pageable) {
        return stationRepository.findAll(specification, pageable);
    }

    @Override
    public List<Station> findAllByIds(List<Long> ids) {
        List<Station> result = stationRepository.findAllById(ids);
        return result == null ? new ArrayList<>() : result;
    }

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

    @Override
    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    @Override
    public List<Station> getAllStations(Long fromStationId) {
        if (fromStationId == null) {
            return getAllStations();
        }
        return stationRepository.findByFromStationId(fromStationId);
    }

    @Override
    public List<Station> getAllActiveStations() {
        return stationRepository.findAllByStatus(StationStatus.ACTIVE);
    }

    @Override
    public List<Station> getAllActiveStations(Long fromStationId) {
        if (fromStationId == null) {
            return getAllActiveStations();
        }
        return stationRepository.findAllByFromStationIdAndStatus(fromStationId, StationStatus.ACTIVE);
    }
}
