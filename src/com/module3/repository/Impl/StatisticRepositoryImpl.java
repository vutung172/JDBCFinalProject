package com.module3.repository.Impl;

import com.module3.entity.Product;
import com.module3.model.DateTimeFormat;
import com.module3.repository.StatisticRepository;
import com.module3.util.MySqlConnect.MySQLConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.Date;

public class StatisticRepositoryImpl implements StatisticRepository,DateTimeFormat {
    @Override
    public float statisticByDate(boolean billType, String date) {
        float sum = 0;
        try  (Connection conn = new MySQLConnect().getConnection()) {
            String sql = MessageFormat.format(
                    "SELECT sum(bill_details.Price*bill_details.Quantity) FROM bills\n" +
                            "    JOIN bill_details ON bills.Bill_id = bill_details.Bill_Id\n" +
                            "    WHERE Bill_Type = {0} AND Bill_Status = 2\n" +
                            "    GROUP BY bills.Auth_date\n" +
                            "    HAVING DATE(bills.Auth_date) = ?", billType);

            PreparedStatement ps = conn.prepareStatement(sql);
            Date dateConfirmed = DateTimeFormat.super.checkerDateFormater(date);
            if (dateConfirmed != null){
                ps.setObject(1,DateTimeFormat.super.dateTransferToDB(dateConfirmed));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sum = sum +rs.getFloat(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }

    @Override
    public float statisticByMonth(boolean billType, String month, String year) {
        float sum = 0;
        try  (Connection conn = new MySQLConnect().getConnection()) {
            String sql = MessageFormat.format(
                    "SELECT sum(bill_details.Price*bill_details.Quantity) FROM bills\n" +
                    " JOIN bill_details ON bills.Bill_id = bill_details.Bill_Id\n" +
                    " WHERE Bill_Type = {0} AND Bill_Status = 2\n" +
                    " GROUP BY bills.Auth_Created\n" +
                    " HAVING MONTH(bills.Auth_date) = ? AND YEAR(bills.Auth_date) = ?", billType);

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1,month);
            ps.setObject(2,year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sum = sum +rs.getFloat(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }

    @Override
    public float statisticByYear(boolean billType, String year) {
        float sum = 0;
        try  (Connection conn = new MySQLConnect().getConnection()) {
            String sql = MessageFormat.format(
                    "SELECT sum(bill_details.Price*bill_details.Quantity) FROM bills\n" +
                    " JOIN bill_details ON bills.Bill_id = bill_details.Bill_Id\n" +
                    " WHERE Bill_Type = {0} AND Bill_Status = 2\n" +
                    " GROUP BY bills.Auth_date\n" +
                    " HAVING YEAR(bills.Auth_date) = ?", billType);

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1,year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sum = sum +rs.getFloat(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }

    @Override
    public float statisticByPeriod(boolean billType, String startDate, String endDate) {
        float sum = 0;
        try  (Connection conn = new MySQLConnect().getConnection()) {
            String sql = MessageFormat.format(
                    "SELECT sum(bill_details.Price*bill_details.Quantity) FROM bills\n" +
                            " JOIN bill_details ON bills.Bill_id = bill_details.Bill_Id\n" +
                            " WHERE Bill_Type = {0} AND Bill_Status = 2\n" +
                            " GROUP BY bills.Auth_date\n" +
                            " HAVING DATE(bills.Auth_date) BETWEEN  ?  AND  ? ", billType);

            PreparedStatement ps = conn.prepareStatement(sql);
            Date start = DateTimeFormat.super.checkerDateFormater(startDate);
            Date end = DateTimeFormat.super.checkerDateFormater(endDate);
            if (start != null && end != null) {
                ps.setObject(1, DateTimeFormat.super.dateTransferToDB(start));
                ps.setObject(2, DateTimeFormat.super.dateTransferToDB(end));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sum = sum +rs.getFloat(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }

    @Override
    public String statisticProduct(boolean billType,String sortType, String startDate, String endDate) {
        try  (Connection conn = new MySQLConnect().getConnection()) {
            String sql = MessageFormat.format(
                    "SELECT Product_Id,sum(bill_details.Quantity) AS total FROM bills " +
                            "    JOIN bill_details ON bills.Bill_id = bill_details.Bill_Id " +
                            "    WHERE Bill_Type = {0} AND Bill_Status = 2 " +
                            "    GROUP BY bill_details.Product_Id,bills.Auth_date " +
                            "    HAVING DATE(bills.Auth_date) BETWEEN ? AND ? " +
                            "    Order BY total {1} " +
                            "    LIMIT 1",billType,sortType);

            PreparedStatement ps = conn.prepareStatement(sql);
            Date start = DateTimeFormat.super.checkerDateFormater(startDate);
            Date end = DateTimeFormat.super.checkerDateFormater(endDate);
            if (start != null && end != null) {
                ps.setObject(1, DateTimeFormat.super.dateTransferToDB(start));
                ps.setObject(2, DateTimeFormat.super.dateTransferToDB(end));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int statisticEmployees(int employeeStatus) {
        int count = 0;
        try  (Connection conn = new MySQLConnect().getConnection()) {
            String sql = MessageFormat.format(
                    "SELECT count(Emp_Id) FROM employees " +
                    "WHERE Emp_Status = {0}",employeeStatus);
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
