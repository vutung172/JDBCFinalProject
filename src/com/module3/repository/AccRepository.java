package com.module3.repository;

import com.module3.entity.Account;

import java.util.List;

public interface AccRepository {
    List<Account> findByIndex(String any);
}
