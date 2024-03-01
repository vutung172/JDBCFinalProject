package com.module3.manager.Impl;

import com.module3.manager.Manager;
import com.module3.model.Message;
import com.module3.model.WarningMess;
import com.module3.service.EmployeeService;
import com.module3.service.Impl.EmployeeServiceImpl;
import com.module3.util.Console;
import com.module3.util.Font.PrintForm;

public class EmployeeManager implements Manager {
    private EmployeeServiceImpl employeeService;

    public EmployeeManager() {
        this.employeeService = new EmployeeServiceImpl();
    }

    @Override
    public void display() {
        try{
            do {
                WarningMess.welcome();
                System.out.println("******************QUẢN LÝ NHÂN VIÊN****************");
                System.out.println("1. Danh sách nhân viên");
                System.out.println("2. Thêm mới nhân viên");
                System.out.println("3. Cập nhật thông tin nhân viên");
                System.out.println("4. Cập nhật trạng thái nhân viên");
                System.out.println("5. Tìm kiếm nhân viên");
                System.out.println("6. Thoát");
                System.out.println(Message.choice);
                int choice = Integer.parseInt(Console.scanner.nextLine());
                switch (choice){
                    case 1:
                        employeeService.listAll();
                        break;
                    case 2:
                        employeeService.create();
                        break;
                    case 3:
                        employeeService.update();
                        break;
                    case 4:
                        employeeService.updateStatus();
                        break;
                    case 5:
                        System.out.println("Nhập vào tên hoặc mã nhân viên muốn tìm");
                        String key = Console.scanner.nextLine();
                        employeeService.search(key);
                        break;
                    case 6:
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
