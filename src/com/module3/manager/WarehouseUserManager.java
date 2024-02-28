package com.module3.manager;

import com.module3.model.Message;
import com.module3.model.WarningMess;
import com.module3.util.Console;
import com.module3.util.Font.PrintForm;

public class WarehouseUserManager implements Manager{
    @Override
    public void display() {
        try{
            do {
                WarningMess.welcome();
                System.out.println("******************QUẢN LÝ KHO****************");
                System.out.println("1. Danh sách phiếu nhập theo trạng thái");
                System.out.println("2. Tạo phiếu nhập");
                System.out.println("3. Cập nhật phiếu nhập");
                System.out.println("4. Tìm kiếm phiếu nhập");
                System.out.println("5. Danh sách phiếu xuất theo trạng thái");
                System.out.println("6. Tạo phiếu xuất");
                System.out.println("7. Cập nhật phiếu xuất");
                System.out.println("8. Tìm kiếm phiếu xuất");
                System.out.println("9. Thoát");
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
                        break;
                    case 8:
                        break;
                    case 9:
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
