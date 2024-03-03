package com.module3.manager.Impl;

import com.module3.manager.Manager;
import com.module3.model.BillType;
import com.module3.model.GeneralType;
import com.module3.model.Message;
import com.module3.model.WarningMess;
import com.module3.service.Impl.ReportServiceImpl;
import com.module3.service.ReportService;
import com.module3.util.Console;
import com.module3.util.Font.PrintForm;

public class ReportManager implements Manager {
    private ReportServiceImpl reportService;

    public ReportManager() {
        this.reportService = new ReportServiceImpl();
    }

    @Override
    public void display() {
        try{
            do {
                WarningMess.welcome();
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
                System.out.println(Message.choice);
                int choice = Integer.parseInt(Console.scanner.nextLine());
                switch (choice){
                    case 1:
                        reportService.statisticByTime(BillType.IMPORT);
                        break;
                    case 2:
                        reportService.statisticByPeriod(BillType.IMPORT);
                        break;
                    case 3:
                        reportService.statisticByTime(BillType.EXPORT);
                        break;
                    case 4:
                        reportService.statisticByPeriod(BillType.EXPORT);
                        break;
                    case 5:
                        reportService.statisticEmployee();
                        break;
                    case 6:
                        reportService.statisticProduct(BillType.IMPORT, GeneralType.sort.decrease);
                        break;
                    case 7:
                        reportService.statisticProduct(BillType.IMPORT, GeneralType.sort.increase);
                        break;
                    case 8:
                        reportService.statisticProduct(BillType.EXPORT, GeneralType.sort.decrease);
                        break;
                    case 9:
                        reportService.statisticProduct(BillType.EXPORT, GeneralType.sort.increase);
                        break;
                    case 10:
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
