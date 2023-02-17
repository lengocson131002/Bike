package com.swd.bike.service;

import com.swd.bike.entity.Account;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.repository.AccountRepository;
import com.swd.bike.service.interfaces.IAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public Account getDetailById(String id) {
        return accountRepository.findById(id).orElseThrow(() -> new InternalException(ResponseCode.ACCOUNT_NOT_FOUND));
    }

    @Override
    public String getIdBySubjectId(String subjectId) {
        return accountRepository.findIdBySubjectId(subjectId).orElse(null);
    }

    @Override
    public Account getBySubjectId(String subjectId) {
        return accountRepository.findBySubjectId(subjectId);
    }

    @Override
    public Optional<String> getIdBySubjectIdOpt(String subjectId) {
        return accountRepository.findIdBySubjectId(subjectId);
    }

    @Override
    public Page<Account> getAccountsByFilter(Specification<Account> specification, Pageable pageable) {
        return accountRepository.findAll(specification, pageable);
    }
}