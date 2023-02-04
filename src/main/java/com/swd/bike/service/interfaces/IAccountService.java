package com.swd.bike.service.interfaces;

import com.swd.bike.entity.Account;

public interface IAccountService {
    Account findAccount(String id);

    Account save(Account account);

    Account getById(String id);
}
