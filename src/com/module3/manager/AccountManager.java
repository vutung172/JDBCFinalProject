package com.module3.manager;


import com.module3.model.Mess;
import com.module3.model.WarningMess;
import com.module3.util.Console;
import com.module3.util.Font.PrintForm;

public class AccountManager implements Manager{
    @Override
    public void display() {
        try{
            do {
                Mess.welcome();
                System.out.println("******************ACCOUNT MANAGEMENT****************");
                System.out.println("1. Danh sách tài khoản");
                System.out.println("2. Tạo tài khoản mới");
                System.out.println("3. Cập nhật trạng thái tài khoản");
                System.out.println("4. Tìm kiếm tài khoản");
                System.out.println("5. Thoát");
                Mess.choice();
                int choice = Integer.parseInt(Console.scanner.nextLine());
                switch (choice){
                    case 1:
                        ProductManager productManager = new ProductManager();
                        productManager.display();
                        break;
                    case 2:
                        EmployeeManager employeeManager = new EmployeeManager();
                        employeeManager.display();
                        break;
                    case 3:
                        AccountManager accountManager = new AccountManager();
                        accountManager.display();
                        break;
                    case 4:
                        ReceiptManager receiptManager = new ReceiptManager();
                        receiptManager.display();
                        break;
                    case 5:
                        return;
                    default:
                        PrintForm.warning(WarningMess.choice.failure);
                }
            }while (true);
        }catch (NumberFormatException nfe){
            nfe.printStackTrace();
        }
    }
}
