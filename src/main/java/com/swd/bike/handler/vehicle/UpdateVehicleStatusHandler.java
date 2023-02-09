package com.swd.bike.handler.vehicle;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.vehicle.request.UpdateVehicleStatusRequest;
import com.swd.bike.entity.Vehicle;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.enums.VehicleStatus;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.IVehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateVehicleStatusHandler extends RequestHandler<UpdateVehicleStatusRequest, StatusResponse> {

    private final IVehicleService vehicleService;

    @Override
    public StatusResponse handle(UpdateVehicleStatusRequest request) {
        Vehicle vehicle = vehicleService.getById(request.getId());
        if (vehicle == null) {
            throw new InternalException(ResponseCode.VEHICLE_NOT_FOUND);
        }
        vehicle.setStatus(request.isApproved() ? VehicleStatus.APPROVED : VehicleStatus.DENY);
        // TODO: Notify to owner
        return new StatusResponse(vehicleService.save(vehicle) != null);
    }
}
