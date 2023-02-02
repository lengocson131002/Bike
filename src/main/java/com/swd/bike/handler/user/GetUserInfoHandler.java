package com.swd.bike.handler.user;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.user.request.GetUserInfoRequest;
import com.swd.bike.dto.user.response.UserResponse;
import com.swd.bike.entity.Account;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUserInfoHandler extends RequestHandler<GetUserInfoRequest, UserResponse> {
    private final IAccountService accountService;

    @Override
    public UserResponse handle(GetUserInfoRequest request) {
        Account account = accountService.getById(request.getUserId());
        if (account == null) {
            throw new InternalException(ResponseCode.USER_NOT_FOUND);
        }
        return UserResponse.convert(account);
    }
}
