package com.swd.bike.entity;

import javax.persistence.Embeddable;

/**
 * Contains station data clone from the post
 */
@Embeddable
public class TripStation {
    private String id;
    private String name;
    private String address;
    private Float longitude;
    private Float latitude;
}
