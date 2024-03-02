package com.module3.manager.Impl;

import com.module3.manager.Manager;
import com.module3.model.BillType;
import com.module3.model.ConstStatus;
import com.module3.model.Message;
import com.module3.model.WarningMess;
import com.module3.service.Impl.BillServiceImpl;
import com.module3.util.Console;
import com.module3.util.Font.PrintForm;
import com.module3.util.Storage.UserStorage;

public class WarehouseUserManager implements Manager {
    private BillServiceImpl billService;

    public WarehouseUserManager() {
        this.billService = new BillServiceImpl();
    }

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
                        billService.listAllByStatus(BillType.IMPORT,UserStorage.currentPermission);
                        break;
                    case 2:
                        billService.create(BillType.IMPORT);
                        break;
                    case 3:
                        billService.update(BillType.IMPORT, UserStorage.currentPermission);
                        break;
                    case 4:
                        System.out.println("Mời nhập vào mã phiếu hoặc mã code cần tìm: ");
                        String any = Console.scanner.nextLine();
                        billService.search(BillType.IMPORT,UserStorage.currentPermission,any);
                        break;
                    case 5:
                        billService.listAllByStatus(BillType.IMPORT,UserStorage.currentPermission);
                        break;
                    case 6:
                        billService.create(BillType.EXPORT);
                        break;
                    case 7:
                        billService.update(BillType.EXPORT, UserStorage.currentPermission);
                        break;
                    case 8:
                        System.out.println("Mời nhập vào mã phiếu hoặc mã code cần tìm: ");
                        String key = Console.scanner.nextLine();
                        billService.search(BillType.EXPORT,UserStorage.currentPermission,key);
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
