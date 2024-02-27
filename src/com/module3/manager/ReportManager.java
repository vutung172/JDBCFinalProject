package com.module3.manager;

import com.module3.model.Mess;
import com.module3.model.WarningMess;
import com.module3.util.Console;
import com.module3.util.Font.PrintForm;

public class ReportManager implements Manager{
    @Override
    public void display() {
        try{
            do {
                Mess.welcome();
                System.out.println("******************QUẢN LÝ BÁO CÁO****************");
                System.out.println("1. Thống kê chi phí theo ngày, tháng, năm");
                System.out.println("2. Thống kê chi phí theo khoảng thời gian");
                System.out.println("3. Thống kê doanh thu theo ngày, tháng, năm");
                System.out.println("4. Thống kê doanh thu theo khoảng thời gian");
                System.out.println("5. Thống kê số nhân viên theo từng trạng thái");
                System.out.println("6. Thống kê sản phẩm nhập nhiều nhất trong khoảng thời gian");
                System.out.println("7. Thống kê sản phẩm nhập ít nhất trong khoảng thời gian");
                System.out.println("8. Thống kê sản phẩm xuất nhiều nhất trong khoảng thời gian");
                System.out.println("9. Thống kê sản phẩm xuất ít nhất trong khoảng thời gian");
                System.out.println("10. Thoát");
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
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
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
