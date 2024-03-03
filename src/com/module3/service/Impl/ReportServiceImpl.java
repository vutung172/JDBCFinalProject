package com.module3.service.Impl;

import com.module3.entity.Product;
import com.module3.model.*;
import com.module3.repository.Impl.RepositoryImpl;
import com.module3.repository.Impl.StatisticRepositoryImpl;
import com.module3.repository.StatisticRepository;
import com.module3.service.ReportService;
import com.module3.util.Console;

import javax.sound.midi.MidiFileFormat;

public class ReportServiceImpl implements ReportService, DateTimeFormat {
    private StatisticRepository statisticRepository;
    private RepositoryImpl<Product> productRepository;

    public ReportServiceImpl() {
        this.statisticRepository = new StatisticRepositoryImpl();
        this.productRepository = new RepositoryImpl<>();
    }

    @Override
    public void statisticByTime(boolean billType) {
        try {
            System.out.println("Mục cần thống kê");
            System.out.println("1. Theo ngày");
            System.out.println("2. Theo tháng");
            System.out.println("3. Theo năm");
            System.out.print(Message.choice);
            int choice = Integer.parseInt(Console.scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Nhập ngày cần thống kê (dd-mm-yyyy)");
                    String date = Console.scanner.nextLine();
                    if (DateTimeFormat.super.dateFormater(date).equals(date)) {
                        float sum = statisticRepository.statisticByDate(billType, date);
                        System.out.printf("%s ngày %s là : %s \n", billType ? "Chi phí" : "Doanh thu", date, sum);
                    } else {
                        WarningMess.dateFormatFailure();
                    }
                    break;
                case 2:
                    System.out.println("Nhập tháng cần thống kê :");
                    int month = Integer.parseInt(Console.scanner.nextLine());
                    System.out.println("Nhập năm cần thống kê :");
                    int year = Integer.parseInt(Console.scanner.nextLine());
                    float sum = statisticRepository.statisticByMonth(billType, String.valueOf(month), String.valueOf(year));
                    System.out.printf("%s tháng %s năm %s là : %s \n", billType ? "Chi phí" : "Doanh thu", month, year, sum);
                    break;
                case 3:
                    System.out.println("Nhập năm cần thống kê :");
                    year = Integer.parseInt(Console.scanner.nextLine());
                    sum = statisticRepository.statisticByYear(billType, String.valueOf(year));
                    System.out.printf("%s năm %s là : %s \n", billType ? "Chi phí" : "Doanh thu", year, sum);
                    break;
                default:
                    WarningMess.choiceFailure();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void statisticByPeriod(boolean billType) {
        try {
            System.out.println("Nhập ngày bắt đầu cần thống kê (dd-mm-yyyy)");
            String startDate = Console.scanner.nextLine();
            System.out.println("Nhập ngày kết thúc cần thống kê (dd-mm-yyyy)");
            String endDate = Console.scanner.nextLine();
            if (DateTimeFormat.super.dateFormater(startDate).equals(startDate) && DateTimeFormat.super.dateFormater(endDate).equals(endDate)) {
                float sum = statisticRepository.statisticByPeriod(billType, startDate, endDate);
                System.out.printf("%s từ ngày %s đến ngày là : %s \n", billType ? "Chi phí" : "Doanh thu", startDate, endDate, sum);
            } else {
                WarningMess.dateFormatFailure();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void statisticProduct(boolean billType, String sortType) {
        try {
            System.out.println("Nhập ngày bắt đầu cần thống kê (dd-mm-yyyy)");
            String startDate = Console.scanner.nextLine();
            System.out.println("Nhập ngày kết thúc cần thống kê (dd-mm-yyyy)");
            String endDate = Console.scanner.nextLine();
            if (DateTimeFormat.super.dateFormater(startDate).equals(startDate) && DateTimeFormat.super.dateFormater(endDate).equals(endDate)) {
                String productId = statisticRepository.statisticProduct(billType, sortType, startDate, endDate);
                Product product = productRepository.findId(Product.class, productId);
                if (product != null) {
                    System.out.printf("Sản phẩm có số lượng %s %s trong khoảng thời gian từ %s đến %s là: %s \n",
                            billType ? "nhập" : "xuất",
                            sortType.equals(GeneralType.sort.increase) ? "ít nhất" : "nhiều nhất",
                            startDate,
                            endDate,
                            product.getProductName());
                }
            } else {
                WarningMess.dateFormatFailure();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void statisticEmployee() {
        try {
            System.out.println("Chọn trạng thái nhân viên muốn thống kê");
            System.out.println("1. Hoạt động");
            System.out.println("2. Nghỉ chế độ");
            System.out.println("3. Nghỉ việc");
            System.out.print(Message.choice);
            int choice = Integer.parseInt(Console.scanner.nextLine());
            switch (choice){
                case 1:
                    int countActive = statisticRepository.statisticEmployees(ConstStatus.EmpStt.ACTIVE);
                    System.out.printf("Số lượng nhân viên đang hoạt động là %s nhân viên \n", countActive);
                    break;
                case 2:
                    int countSleep = statisticRepository.statisticEmployees(ConstStatus.EmpStt.SLEEP);
                    System.out.printf("Số lượng nhân viên đang nghỉ chế độ là %s nhân viên \n", countSleep);
                    break;
                case 3:
                    int countQuit = statisticRepository.statisticEmployees(ConstStatus.EmpStt.QUIT);
                    System.out.printf("Số lượng nhân viên đã nghỉ việc là %s nhân viên \n", countQuit);
                    break;
                default:
                    WarningMess.choiceFailure();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
