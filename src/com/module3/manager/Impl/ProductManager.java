package com.module3.manager.Impl;

import com.module3.manager.Manager;
import com.module3.model.Message;
import com.module3.model.WarningMess;
import com.module3.service.Impl.ProductServiceImpl;
import com.module3.util.Console;
import com.module3.util.Font.PrintForm;

public class ProductManager implements Manager {
    private ProductServiceImpl productService;

    public ProductManager() {
        this.productService = new ProductServiceImpl();
    }

    @Override
    public void display() {
        try{
            do {
                WarningMess.welcome();
                System.out.println("******************QUẢN LÝ SẢN PHẨM****************");
                System.out.println("1. Danh sách sản phẩm");
                System.out.println("2. Thêm mới sản phẩmn");
                System.out.println("3. Cập nhật sản phẩm");
                System.out.println("4. Tìm kiếm sản phẩm");
                System.out.println("5. Cập nhật trạng thái sản phẩm");
                System.out.println("6. Thoát");
                System.out.println(Message.choice);
                int choice = Integer.parseInt(Console.scanner.nextLine());
                switch (choice){
                    case 1:
                        productService.listAll();
                        break;
                    case 2:
                        productService.create();
                        break;
                    case 3:
                        productService.update();
                        break;
                    case 4:
                        System.out.println("Nhập vào sản phẩm muốn tìm");
                        String key = Console.scanner.nextLine();
                        productService.search(key);
                        break;
                    case 5:
                        productService.updateStatus();
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
