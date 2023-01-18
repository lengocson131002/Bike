package com.swd.bike.handler.health;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.health.HealthCheckRequest;
import org.springframework.stereotype.Component;

@Component
public class CheckHealthHandler extends RequestHandler<HealthCheckRequest, StatusResponse> {
    @Override
    public StatusResponse handle(HealthCheckRequest request) {
        return new StatusResponse(true);
    }
}
