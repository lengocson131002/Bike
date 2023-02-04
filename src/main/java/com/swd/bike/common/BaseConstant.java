package com.swd.bike.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BaseConstant {

    public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

    public static final String DEFAULT_DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

    public static Long POST_THRESHOLD_IN_MINUTES;
    @Value("${post-threshold:30}")
    public void setPostThresholdInMinutes(Long noMinutes) {
        POST_THRESHOLD_IN_MINUTES = noMinutes;
    }

    public static Long TRIP_CANCEL_THRESHOLD_IN_MINUTES;
    @Value("${trip_cancel_threshold:30}")
    public void setTripCancelThresholdInMinutes(Long noMinutes) {
        TRIP_CANCEL_THRESHOLD_IN_MINUTES = noMinutes;
    }
}
