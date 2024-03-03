package com.module3.service;

public interface ReportService {
    void statisticByTime(boolean billType);
    void statisticByPeriod(boolean billType);
    void statisticProduct(boolean billType,String sortType);
    void statisticEmployee();
}
