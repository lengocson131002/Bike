package com.swd.bike.handler.vehicle;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.vehicle.request.RegisterVehicleRequest;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Vehicle;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.enums.VehicleStatus;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.IAccountService;
import com.swd.bike.service.interfaces.IVehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegisterVehicleHandler extends RequestHandler<RegisterVehicleRequest, StatusResponse> {

    private final IVehicleService vehicleService;
    private final IAccountService accountService;

    @Override
    @Transactional
    public StatusResponse handle(RegisterVehicleRequest request) {
        if (vehicleService.getByOwnerId(request.getUserId()) != null) {
            throw new InternalException(ResponseCode.USER_HAS_REGISTERED_VEHICLE);
        }
        Account account = accountService.getById(request.getUserId());
        if (account == null) {
            throw new InternalException(ResponseCode.ACCOUNT_NOT_FOUND);
        }

        Vehicle vehicle = new Vehicle()
                .setBrand(request.getBrand())
                .setLicencePlate(request.getLicencePlate())
                .setColor(request.getColor())
                .setImage(request.getImage())
                .setDescription(request.getDescription())
                .setType(request.getType())
                .setStatus(VehicleStatus.WAITING)
                .setOwner(account);

        // TODO: Notify to admin
        return new StatusResponse(vehicleService.save(vehicle) != null);
    }
}
