package com.module3.util.MySqlConnect;

import com.module3.util.Font.PrintForm;
import java.sql.Connection;
import java.sql.*;

public class MySQLConnect {
    private Connection connection;

    public MySQLConnect() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); // xác nhận driver dùng cho MySQL
            String url = "jdbc:mysql://localhost:3306/warehouse_manager";
            String user = "root";
            String password = "Vtt$88842648864";
            this.connection = DriverManager.getConnection(url,user,password);//connect tới database sử dụng
        } catch (Exception e){
            e.printStackTrace();
            PrintForm.warning("kết nối thất bại");
        }
    }

    public Connection getConnection() {
        return this.connection;
    }
    public static void close(Connection connection) {
        try{
            if (connection != null)
                connection.close();
        } catch (SQLException se){
            se.printStackTrace();
        }
    }

}
