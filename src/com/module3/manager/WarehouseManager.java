package com.module3.manager;


import com.module3.model.Mess;
import com.module3.model.WarningMess;
import com.module3.util.Console;
import com.module3.util.Font.PrintForm;

public class WarehouseManager implements Manager{
    @Override
    public void display() {
        try{
            do {
                Mess.welcome();
                System.out.println("******************WAREHOUSE MANAGEMENT****************");
                System.out.println("1. Quản lý sản phẩm");
                System.out.println("2. Quản lý nhân viên");
                System.out.println("3. Quản lý tài khoản");
                System.out.println("4. Quản lý phiếu nhập");
                System.out.println("5. Quản lý phiếu xuất");
                System.out.println("6. Quản lý báo cáo");
                System.out.println("7. Thoát");
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
                        BillManager billManager = new BillManager();
                        billManager.display();
                        break;
                    case 6:
                        ReportManager reportManager = new ReportManager();
                        reportManager.display();
                        break;
                    case 7:
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
