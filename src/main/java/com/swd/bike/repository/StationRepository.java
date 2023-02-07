package com.swd.bike.repository;

import com.swd.bike.entity.Station;
import com.swd.bike.enums.StationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Long>, JpaSpecificationExecutor<Station> {
    Page<Station> findAll(Specification<Station> specification, Pageable pageable);
    Optional<Station> findStationByIdAndStatus(Long id, StationStatus status);

    @Query("SELECT endStations " +
            "FROM Station station " +
            "JOIN station.nextStation endStations " +
            "WHERE station.id = ?1")
    List<Station> findByFromStationId(Long fromStationId);

}
