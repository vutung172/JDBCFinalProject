package com.module3.manager;

import com.module3.entity.Product;
import com.module3.model.ConstStatus;
import com.module3.model.Mess;
import com.module3.model.WarningMess;
import com.module3.repository.Impl.RepositoryImpl;
import com.module3.util.Console;
import com.module3.util.Font.PrintForm;

import java.util.List;

public class ProductManager implements Manager{
    @Override
    public void display() {
        try{
            do {
                Mess.welcome();
                System.out.println("******************PRODUCT MANAGEMENT****************");
                System.out.println("1. Danh sách sản phẩm");
                System.out.println("2. Thêm mới sản phẩmn");
                System.out.println("3. Cập nhật sản phẩm");
                System.out.println("4. Tìm kiếm sản phẩm");
                System.out.println("5. Cập nhật trạng thái sản phẩm");
                System.out.println("6. Thoát");
                Mess.choice();
                int choice = Integer.parseInt(Console.scanner.nextLine());
                switch (choice){
                    case 1:
                        RepositoryImpl<Product> productRepository = new RepositoryImpl<>();
                        List<Product> productList = productRepository.fìndAllByPagination(Product.class,10);
                        productList.stream().forEach(p -> System.out.printf("%s | %s | %s | %s | %s | %s | %s \n",p.getProductId(),p.getProductName(),p.getManufacturer(),p.getCreated(),p.getBatch(),p.getQuantity(),p.getProductStatus() == ConstStatus.ProductStt.ACTIVE ? "Hoạt động":"Không hoạt động"));
                        System.out.printf("");
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
