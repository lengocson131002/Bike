package com.swd.bike.dto.account.request;

import com.swd.bike.core.BaseRequestData;
import com.swd.bike.enums.AccountStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor()
public class UpdateAccountStatusRequest extends BaseRequestData {
    private String id;
    private AccountStatus status;
}
