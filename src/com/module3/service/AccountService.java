package com.module3.service;

import com.module3.entity.Account;

import java.util.List;

public interface AccountService {
    Account login(String userName, String password);
}
