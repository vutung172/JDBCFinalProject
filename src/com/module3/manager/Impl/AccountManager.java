package com.module3.manager.Impl;


import com.module3.manager.Manager;
import com.module3.model.Message;
import com.module3.model.WarningMess;
import com.module3.service.Impl.AccountServiceImpl;
import com.module3.util.Console;

public class AccountManager implements Manager {
    private AccountServiceImpl accountService;

    public AccountManager() {
        this.accountService = new AccountServiceImpl();
    }

    @Override
    public void display() {
        try{
            do {
                WarningMess.welcome();
                System.out.println("******************ACCOUNT MANAGEMENT****************");
                System.out.println("1. Danh sách tài khoản");
                System.out.println("2. Tạo tài khoản mới");
                System.out.println("3. Cập nhật trạng thái tài khoản");
                System.out.println("4. Tìm kiếm tài khoản");
                System.out.println("5. Thoát");
                System.out.println(Message.choice);
                int choice = Integer.parseInt(Console.scanner.nextLine());
                switch (choice){
                    case 1:
                        accountService.listAll();
                        break;
                    case 2:
                        accountService.create();
                        break;
                    case 3:
                        accountService.update();
                        break;
                    case 4:
                        System.out.println("Nhập vào tên tài khoản hoặc tên nhân viên muốn tìm: ");
                        String key = Console.scanner.nextLine();
                        accountService.search(key);
                        break;
                    case 5:
                        return;
                    default:
                        WarningMess.choiceFailure();
                }
            }while (true);
        }catch (NumberFormatException nfe){
            nfe.printStackTrace();
        }
    }
}
