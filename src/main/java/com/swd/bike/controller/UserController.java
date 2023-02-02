package com.swd.bike.controller;

import com.swd.bike.controller.interfaces.IUserController;
import com.swd.bike.core.BaseController;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.auth.response.AccessTokenResponseCustom;
import com.swd.bike.dto.user.request.GetUserInfoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends BaseController implements IUserController {

    @Override
    public ResponseEntity<ResponseBase<AccessTokenResponseCustom>> getUserInfo() {
        GetUserInfoRequest request = new GetUserInfoRequest();
        request.setUserId(this.getUserId());
        return this.execute(request);
    }

}
