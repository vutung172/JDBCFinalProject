package com.module3.repository;

import com.module3.entity.Product;

public interface StatisticRepository {
    float statisticByDate(boolean billType,String date);
    float statisticByMonth(boolean billType,String month,String year);
    float statisticByYear(boolean billType,String year);
    float statisticByPeriod(boolean billType,String startDate, String endDate);
    String statisticProduct(boolean billType,String sortType,String startDate, String endDate);
    int statisticEmployees(int employeeStatus);
}
