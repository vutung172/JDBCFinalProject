package com.module3.service.Impl;

import com.module3.entity.Account;
import com.module3.repository.AuthRepository;
import com.module3.repository.Impl.AuthRepositoryImpl;
import com.module3.repository.Impl.RepositoryImpl;
import com.module3.service.AccountService;

import java.util.List;

public class AccountServiceImpl implements AccountService {
    private RepositoryImpl<Account> accountRepository;
    private AuthRepository authRepository;

    public AccountServiceImpl() {
        this.accountRepository = new RepositoryImpl<>();
        this.authRepository = new AuthRepositoryImpl();
    }

    @Override
    public Account login(String userName, String password) {
       return authRepository.login(userName,password);
    }

    @Override
    public List<Account> showAllAccount() {
        return null;
    }

    @Override
    public Account createNewAccount(Account newAccount) {
        return null;
    }

    @Override
    public Account updateAccount(Account currAccount, Account updateAccount) {
        return null;
    }

    @Override
    public List<Account> searchByName(String userName) {
        return null;
    }
}
