package com.module3.service;

import com.module3.entity.Account;

import java.util.List;

public interface AccountService {
    Account login(String userName, String password);
    List<Account> showAllAccount();
    Account createNewAccount(Account newAccount);
    Account updateAccount(Account currAccount,Account updateAccount);
    List<Account> searchByName(String userName);
}
