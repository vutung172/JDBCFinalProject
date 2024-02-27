package com.module3.main;

import com.module3.entity.Account;
import com.module3.manager.WarehouseManager;
import com.module3.manager.WarehouseUserManager;
import com.module3.model.Mess;
import com.module3.model.PermissionType;
import com.module3.repository.Impl.RepositoryImpl;
import com.module3.service.AccountService;
import com.module3.service.Impl.AccountServiceImpl;
import com.module3.util.Console;
import com.module3.util.Font.PrintForm;
import com.module3.util.Storage.UserStorage;

import java.text.MessageFormat;

public class main {
    public static void main(String[] args) {

        do {
            AccountService accountService = new AccountServiceImpl();
            RepositoryImpl<Account> accountRepository = new RepositoryImpl<>();
            System.out.println("Nhập vào tên đăng nhập");
            String userName = Console.scanner.nextLine();
            System.out.println("Nhập vào mã đăng nhập");
            String password = Console.scanner.nextLine();
            /*Account loginAcc = accountService.login(userName,password);*/
            Account loginAcc = accountRepository.findId(Account.class,userName,password);
            if (loginAcc != null){
                UserStorage.accountId = loginAcc.getAccId();
                UserStorage.currentUserName = loginAcc.getUserName();
                UserStorage.currentPermission = loginAcc.getPermission();
                UserStorage.employeeId = loginAcc.getEmployeeId();
                UserStorage.accStatus = loginAcc.getAccountStatus();
                if (loginAcc.getPermission() == PermissionType.ADMIN){
                    roleAdmin();
                } else if (loginAcc.getPermission() == PermissionType.USER) {
                    roleUser();
                } else {
                    PrintForm.warning("Tài khoản chưa được phân quyền truy cập!");
                }
            } else {
                PrintForm.warning("Tên đăng nhập hoặc mật khẩu không phù hợp, mời bạn nhập lại");
            }
        } while (true);
    }

    public static void roleAdmin(){
        WarehouseManager warehouseManager = new WarehouseManager();
        warehouseManager.display();
    }

    public static void roleUser(){
        WarehouseUserManager warehouseUserManager = new WarehouseUserManager();
        warehouseUserManager.display();
    }
}
