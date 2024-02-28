package com.module3.manager;

import com.module3.model.Message;
import com.module3.model.WarningMess;
import com.module3.util.Console;
import com.module3.util.Font.PrintForm;

public class BillManager implements Manager{
    @Override
    public void display() {
        try{
            do {
                WarningMess.welcome();
                System.out.println("******************QUẢN LÝ PHIẾU XUẤT****************");
                System.out.println("1. Danh sách phiếu xuất");
                System.out.println("2. Tạo phiếu xuất");
                System.out.println("3. Cập nhật thông tin phiếu xuất");
                System.out.println("4. Chi tiết phiếu xuất");
                System.out.println("5. Duyệt phiếu xuất");
                System.out.println("6. Tìm kiếm phiếu xuất");
                System.out.println("7. Thoát");
                System.out.println(Message.choice);
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
                        WarningMess.choiceFailure();
                }
            }while (true);
        }catch (NumberFormatException nfe){
            nfe.printStackTrace();
        }
    }
}
