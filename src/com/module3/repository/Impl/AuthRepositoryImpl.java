package com.module3.repository.Impl;

import com.module3.entity.Account;
import com.module3.model.ConstStatus;
import com.module3.model.PermissionType;
import com.module3.repository.AuthRepository;
import com.module3.util.MySqlConnect.MySQLConnect;

import java.sql.*;

public class AuthRepositoryImpl implements AuthRepository {
    @Override
    public Account login(String user, String pass) {
        try  (Connection conn = new MySQLConnect().getConnection()) {
            String sql = "{call account_authenticator(?,?,?,?,?,?,?,?)}";
            System.out.println(sql);
            CallableStatement cst = conn.prepareCall(sql);
            cst.registerOutParameter(3, Types.INTEGER);
            cst.registerOutParameter(4, Types.VARCHAR);
            cst.registerOutParameter(5, Types.VARCHAR);
            cst.registerOutParameter(6, Types.CHAR);
            cst.registerOutParameter(7, Types.BIT);
            cst.registerOutParameter(8, Types.BIT);
            cst.setString(1,user);
            cst.setString(2,pass);
            cst.execute();
            Account loginAccount = null;
            if (cst.getInt(3) != 0){
                loginAccount = new Account();
                loginAccount.setAccId(cst.getInt(3));
                loginAccount.setUserName(cst.getString(4));
                loginAccount.setEmployeeId(cst.getString(5));
                loginAccount.setEmployeeId(cst.getString(6));
                loginAccount.setPermission(cst.getBoolean(7));
                loginAccount.setAccountStatus(cst.getBoolean(8));
            }
            return loginAccount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
