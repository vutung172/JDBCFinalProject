package com.module3.manager;

import com.module3.model.Mess;
import com.module3.model.WarningMess;
import com.module3.util.Console;
import com.module3.util.Font.PrintForm;

public class EmployeeManager implements Manager{
    @Override
    public void display() {
        try{
            do {
                Mess.welcome();
                System.out.println("******************EMPLOYEE MANAGEMENT****************");
                System.out.println("1. Danh sách nhân viên");
                System.out.println("2. Thêm mới nhân viên");
                System.out.println("3. Cập nhật thông tin nhân viên");
                System.out.println("4. Cập nhật trạng thái nhân viên");
                System.out.println("5. Quản lý phiếu xuất");
                System.out.println("5. Tìm kiếm nhân viên");
                System.out.println("6. Thoát");
                Mess.choice();
                int choice = Integer.parseInt(Console.scanner.nextLine());
                switch (choice){
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:
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
