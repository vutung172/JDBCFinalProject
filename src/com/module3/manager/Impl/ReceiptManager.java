package com.module3.manager.Impl;

import com.module3.manager.Manager;
import com.module3.model.BillType;
import com.module3.model.Message;
import com.module3.model.WarningMess;
import com.module3.service.Impl.BillServiceImpl;
import com.module3.util.Console;
import com.module3.util.Font.PrintForm;

public class ReceiptManager implements Manager {
    private BillServiceImpl billService;

    public ReceiptManager() {
        this.billService = new BillServiceImpl();
    }

    @Override
    public void display() {
        try{
            do {
                WarningMess.welcome();
                System.out.println("******************QUẢN LÝ PHIẾU NHẬP****************");
                System.out.println("1. Danh sách phiếu nhập");
                System.out.println("2. Tạo phiếu nhập");
                System.out.println("3. Cập nhật thông tin phiếu nhập");
                System.out.println("4. Chi tiết phiếu nhập");
                System.out.println("5. Duyệt phiếu nhập");
                System.out.println("6. Tìm kiếm phiếu nhập");
                System.out.println("7. Thoát");
                System.out.println(Message.choice);
                int choice = Integer.parseInt(Console.scanner.nextLine());
                switch (choice){
                    case 1:
                        billService.listAll(BillType.IMPORT);
                        break;
                    case 2:
                        billService.create(BillType.IMPORT);
                        break;
                    case 3:
                        billService.update(BillType.IMPORT);
                        break;
                    case 4:
                        billService.billDetail(BillType.IMPORT);
                        break;
                    case 5:
                        billService.billApproval(BillType.IMPORT);
                        break;
                    case 6:
                        System.out.println("Nhập vào mã phiếu hoặc mã code cần tìm");
                        String key = Console.scanner.nextLine();
                        billService.search(BillType.IMPORT,key);
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
