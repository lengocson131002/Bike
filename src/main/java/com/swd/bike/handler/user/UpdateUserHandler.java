package com.swd.bike.handler.user;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.user.request.UpdateUserRequest;
import com.swd.bike.entity.Account;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUserHandler extends RequestHandler<UpdateUserRequest, StatusResponse> {
    private final IAccountService accountService;

    @Override
    public StatusResponse handle(UpdateUserRequest request) {
        Account account = accountService.getById(request.getUserId());
        if (account == null) {
            throw new InternalException(ResponseCode.USER_NOT_FOUND);
        }
        if (request.getName() != null) {
            account.setName(request.getName());
        }
        if (request.getPhone() != null) {
            account.setPhone(request.getPhone());
        }
        if (request.getAvatar() != null) {
            account.setAvatar(request.getAvatar());
        }
        if (request.getCard() != null) {
            account.setCard(request.getCard());
        }
        account.setIsUpdated(true);
        accountService.save(account);
        return new StatusResponse();
    }
}
