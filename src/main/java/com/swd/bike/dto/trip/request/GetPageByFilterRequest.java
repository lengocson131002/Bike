package com.swd.bike.dto.trip.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swd.bike.core.BasePagingAndSortingRequestData;
import com.swd.bike.entity.Post;
import com.swd.bike.entity.Trip;
import com.swd.bike.enums.TripStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class GetPageByFilterRequest extends BasePagingAndSortingRequestData {
    private String passengerInfo;
    private String grabberInfo;
    private TripStatus status;
    private Long startStationId;
    private Long endStationId;

    private Specification<Trip> getByPassengerInfo() {
        return (root, cQuery, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.like(root.join("passenger").get("name"), "%" + passengerInfo + "%"));
            predicates.add(builder.like(root.join("passenger").get("phone"),"%" + passengerInfo + "%"));
            predicates.add(builder.like(root.join("passenger").get("email"),"%" + passengerInfo + "%"));
            return builder.or(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    private Specification<Trip> getByGrabberInfo() {
        return (root, cQuery, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.like(root.join("grabber").get("name"), "%" + passengerInfo + "%"));
            predicates.add(builder.like(root.join("grabber").get("phone"),"%" + passengerInfo + "%"));
            predicates.add(builder.like(root.join("grabber").get("email"),"%" + passengerInfo + "%"));
            return builder.or(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Specification<Trip> getByTripInfo() {
        return (root, cQuery, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (status != null) {
                predicates.add(builder.equal(root.get("status"), status));
            }
            if (startStationId != null) {
                predicates.add(builder.equal(root.get("startStation").get("id"), startStationId));
            }
            if (endStationId != null) {
                predicates.add(builder.equal(root.get("endStation").get("id"), endStationId));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Specification<Trip> getSpecification() {
        if (passengerInfo == null && grabberInfo == null  && endStationId == null && startStationId == null && status == null) {
            return (root, cQuery, builder) -> {
                List<Predicate> predicates = new ArrayList<>();
                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            };
        }
        return Specification.where(getByPassengerInfo()).and(getByGrabberInfo()).and(getByTripInfo());
    }
}
