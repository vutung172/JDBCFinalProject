package com.module3.repository;

import com.module3.entity.BillDetail;

import java.util.List;

public interface BillRepository {
    List<BillDetail> findByBillId(long billId);
}
