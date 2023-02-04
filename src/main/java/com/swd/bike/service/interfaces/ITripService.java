package com.swd.bike.service.interfaces;

import com.swd.bike.entity.Account;
import com.swd.bike.entity.Trip;
import com.swd.bike.enums.TripRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface ITripService {
    Page<Trip> getAllTrip(Specification<Trip> spec, Pageable pageable);

    Trip getCurrentTrip(Account account);

    Trip save(Trip trip);

    Trip getTrip(Long id);

    Integer countFeedbackedTrip(Account account);
}
