package com.swd.bike.service.interfaces;

import com.swd.bike.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

public interface IAccountService {
    Account findAccount(String id);

    Account save(Account account);

    Account getById(String id);

    Account getDetailById(String id);

    Page<Account> getAccountsByFilter(Specification<Account> specification, Pageable pageable);

}
