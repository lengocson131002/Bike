package com.swd.bike.controller;

import com.swd.bike.controller.interfaces.ITestController;
import com.swd.bike.core.BaseController;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.notification.request.SendNotificationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController extends BaseController implements ITestController {

    @Override
    public ResponseEntity<ResponseBase<StatusResponse>> send(SendNotificationRequest request) {
        return this.execute(request);
    }
}
