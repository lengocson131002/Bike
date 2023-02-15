package com.swd.bike.handler.account;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.account.request.UpdateAccountStatusRequest;
import com.swd.bike.dto.account.response.UpdateAccountStatusResponse;
import com.swd.bike.entity.Account;
import com.swd.bike.service.AccountService;
import com.swd.bike.service.interfaces.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateAccountStatusHandler extends RequestHandler<UpdateAccountStatusRequest, UpdateAccountStatusResponse> {
    private final IAccountService accountService;
    @Override
    public UpdateAccountStatusResponse handle(UpdateAccountStatusRequest request) {
        Account account = accountService.getDetailById(request.getId());
        account.setStatus(request.getStatus());
        accountService.save(account);
        return UpdateAccountStatusResponse.builder().success(true).build();
    }
}
