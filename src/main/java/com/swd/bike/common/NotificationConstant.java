package com.swd.bike.common;

import org.springframework.stereotype.Component;

@Component
public class NotificationConstant {

    public class Title {
        public static final String VEHICLE_REGISTRATION_CREATE = "Vehicle registration";
        public static final String VEHICLE_REGISTRATION_APPROVE = "Your vehicle registration has been approved";
        public static final String VEHICLE_REGISTRATION_DENIED = "Your vehicle registration has been denied";
    }

    public class Body {
        public static final String VEHICLE_REGISTRATION_CREATE = "User %s {ID: %s} has registered their vehicle";
        public static final String VEHICLE_REGISTRATION_APPROVE = "Your vehicle %s {ID: %s} has been approved";
        public static final String VEHICLE_REGISTRATION_DENIED = "Your vehicle %s {ID: %s} has been denied";
    }
}
