package com.swd.bike.service;

import com.swd.bike.entity.Account;
import com.swd.bike.repository.AccountRepository;
import com.swd.bike.service.interfaces.IAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;

    @Override
    public Account findAccount(String id) {
        if (id == null) {
            return null;
        }
        return accountRepository
                .findById(id)
                .orElse(null);
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account getById(String id) {
        return accountRepository.findById(id).orElse(null);
    }
}
