package com.swd.bike.service.interfaces;

import com.swd.bike.entity.Account;
import org.springframework.stereotype.Service;

public interface IAccountService {

    Account save(Account account);

    Account getById(String id);
}
