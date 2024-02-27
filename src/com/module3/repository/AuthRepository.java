package com.module3.repository;

import com.module3.entity.Account;

public interface AuthRepository {
    Account login(String user, String pass);
}
