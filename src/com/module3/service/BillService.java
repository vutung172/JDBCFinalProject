package com.module3.service;

import com.module3.entity.Bill;
import com.module3.entity.BillDetail;

import java.lang.Boolean;
import java.util.List;

public interface BillService<T> {
    List<T> listAll(Boolean billType);
    T create(Boolean billType);
    boolean update(Boolean billType);
    List<T> search(Boolean billType,String any);
    List<T> billDetail(Boolean billType);
    T billApproval(Boolean billType);
}
