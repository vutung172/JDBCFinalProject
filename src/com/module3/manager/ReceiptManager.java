package com.module3.manager;

import com.module3.model.Mess;
import com.module3.model.WarningMess;
import com.module3.util.Console;
import com.module3.util.Font.PrintForm;

public class ReceiptManager implements Manager{
    @Override
    public void display() {
        try{
            do {
                Mess.welcome();
                System.out.println("******************QUẢN LÝ PHIẾU NHẬP****************");
                System.out.println("1. Danh sách phiếu nhập");
                System.out.println("2. Tạo phiếu nhập");
                System.out.println("3. Cập nhật thông tin phiếu nhập");
                System.out.println("4. Chi tiết phiếu nhập");
                System.out.println("5. Duyệt phiếu nhập");
                System.out.println("6. Tìm kiếm phiếu nhập");
                System.out.println("7. Thoát");
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
