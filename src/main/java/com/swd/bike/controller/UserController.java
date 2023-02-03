package com.swd.bike.controller;

import com.swd.bike.controller.interfaces.IUserController;
import com.swd.bike.core.BaseController;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.user.request.GetUserInfoRequest;
import com.swd.bike.dto.user.request.UpdateUserRequest;
import com.swd.bike.dto.user.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends BaseController implements IUserController {

    @Override
    public ResponseEntity<ResponseBase<UserResponse>> getUserInfo() {
        GetUserInfoRequest request = new GetUserInfoRequest();
        request.setUserId(this.getUserId());
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<StatusResponse>> update(UpdateUserRequest request) {
        request.setUserId(this.getUserId());
        return this.execute(request);
    }

}
