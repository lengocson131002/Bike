package com.swd.bike.service;

import com.swd.bike.entity.Account;
import com.swd.bike.entity.Trip;
import com.swd.bike.enums.TripRole;
import com.swd.bike.enums.TripStatus;
import com.swd.bike.repository.TripRepository;
import com.swd.bike.service.interfaces.ITripService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class TripService implements ITripService {

    private final TripRepository tripRepository;

    @Override
    public Page<Trip> getAllTrip(Specification<Trip> spec, Pageable pageable) {
        if (pageable == null) {
            return new PageImpl<>(new ArrayList<>());
        }
        return tripRepository.findAll(spec, pageable);
    }

    @Override
    public Trip getCurrentTrip(Account account) {
        if (account == null) {
            return null;
        }
        return tripRepository
                .findFirstByStatus(account.getId(), TripStatus.ON_GOING)
                .orElse(null);
    }

    @Override
    public Trip save(Trip trip) {
        return tripRepository.save(trip);
    }

    @Override
    public Trip getTrip(Long id) {
        if (id == null) {
            return null;
        }
        return tripRepository.findById(id).orElse(null);
    }

    @Override
    public Integer countFeedbackedTrip(Account account) {
        if (account == null) {
            return 0;
        }
        return tripRepository.countByGrabberIdAndFeedbackPointNotNull(account.getId());
    }
}
