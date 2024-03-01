package com.module3.service.Impl;

import com.module3.entity.Product;
import com.module3.model.*;
import com.module3.repository.Impl.RepositoryImpl;
import com.module3.service.GeneralService;
import com.module3.util.Console;
import com.module3.util.Font.PrintForm;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ProductServiceImpl implements GeneralService<Product> {
    private RepositoryImpl<Product> productRepository;

    public ProductServiceImpl() {
        this.productRepository = new RepositoryImpl<>();
    }

    @Override
    public List<Product> listAll() {
        List<Product> productList = productRepository.findAll(Product.class);
        if (!productList.isEmpty()) {
            int maxIndex = productList.size() / 10;
            int choice;
            int index = 1;
            try {
                do {
                    List<Product> productListPagination = productRepository.findAllByPagination(Product.class, index);
                    if (!productListPagination.isEmpty()) {
                        System.out.printf("Tổng: %s sản phẩm\n",productList.size());
                        Header.products();
                        productListPagination.stream().forEach(p -> System.out.printf(TableForm.products.column,
                                p.getProductId(),
                                p.getProductName(),
                                p.getManufacturer(),
                                p.getCreated(),
                                p.getBatch(),
                                p.getQuantity(),
                                p.getProductStatus() ? "Hoạt Động" : "Không hoạt động"));
                        System.out.println("1.<Trang trước> | 2.<Trang sau> | 3.<Thoát>");
                        System.out.print(Message.choice);
                        choice = Integer.parseInt(Console.scanner.nextLine());
                        switch (choice) {
                            case 1:
                                index--;
                                if (index <= 0)
                                    index = 1;
                                break;
                            case 2:
                                index++;
                                if (index > maxIndex) {
                                    if (productList.size() % 10 == 0) {
                                        index = maxIndex;
                                    } else {
                                        index = maxIndex + 1;
                                    }
                                }
                                break;
                            case 3:
                                break;
                            default:
                                WarningMess.choiceFailure();
                        }
                    } else {
                        WarningMess.listEmpty();
                        choice = 3;
                    }
                } while (choice != 3);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        } else {
            WarningMess.listEmpty();
        }
        return productList;
    }

    @Override
    public Product create() {
        try {
            boolean stop;
            do {
                Product product = new Product();
                System.out.println("Mời nhập mã sản phẩm: ");
                product.setProductId(Console.scanner.nextLine());
                System.out.println("Mời bạn nhập tên sản phẩm: ");
                product.setProductName(Console.scanner.nextLine());
                System.out.println("Mời bạn nhập nhà sản xuất: ");
                product.setManufacturer(Console.scanner.nextLine());
                System.out.println("Mời nhập lô chứa sản phẩm: ");
                product.setBatch(Integer.parseInt(Console.scanner.nextLine()));
                product.setCreated(Date.from(Instant.now()));
                product.setQuantity(0);
                product.setProductStatus(ConstStatus.ProductStt.ACTIVE);
                if (productRepository.add(product) != null) {
                    WarningMess.createdSuccess();
                    return product;
                } else {
                    WarningMess.createdFailure();
                }
                System.out.println(Message.continuous);
                String confirm = Console.scanner.nextLine();
                stop = confirm.contains("y");
            } while (stop);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        return null;
    }

    @Override
    public Product update() {
        try {
            boolean stop = false;
            do {
                System.out.println("Nhập vào mã sản phẩm muốn cập nhật: ");
                String key = Console.scanner.nextLine();
                Product updateProduct = productRepository.findId(Product.class, key);
                if (updateProduct != null) {
                    Header.products();
                    System.out.printf(TableForm.products.column,
                            updateProduct.getProductId(),
                            updateProduct.getProductName(),
                            updateProduct.getManufacturer(),
                            updateProduct.getCreated(),
                            updateProduct.getBatch(),
                            updateProduct.getQuantity(),
                            updateProduct.getProductStatus().equals(ConstStatus.ProductStt.ACTIVE) ? "Hoạt động" : "Không hoạt động");
                    System.out.println("Cập nhật tên sản phẩm: ");
                    updateProduct.setProductName(Console.scanner.nextLine());
                    System.out.println("Cập nhật tên nhà sản xuất: ");
                    updateProduct.setManufacturer(Console.scanner.nextLine());
                    System.out.println("Cập nhật lô: ");
                    updateProduct.setBatch(Integer.parseInt(Console.scanner.nextLine()));
                    updateProduct.setCreated(Date.from(Instant.now()));
                    if (productRepository.edit(updateProduct) != null) {
                        WarningMess.updateSuccess();
                        return updateProduct;
                    } else {
                        WarningMess.updateFailure();
                    }
                    System.out.println(Message.continuous);
                    String confirm = Console.scanner.nextLine();
                    stop = confirm.contains("y");
                } else {
                    WarningMess.objectNotExist();
                }
            } while (stop);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateStatus() {
        try {
            boolean stop = false;
            do {
                System.out.println("Nhập vào mã sản phẩm muốn cập nhật: ");
                String key = Console.scanner.nextLine();
                Product updateStatusProduct = productRepository.findId(Product.class, key);
                if (updateStatusProduct != null) {
                    Header.products();
                    System.out.printf(TableForm.products.column,
                            updateStatusProduct.getProductId(),
                            updateStatusProduct.getProductName(),
                            updateStatusProduct.getManufacturer(),
                            updateStatusProduct.getCreated(),
                            updateStatusProduct.getBatch(),
                            updateStatusProduct.getQuantity(),
                            updateStatusProduct.getProductStatus().equals(ConstStatus.ProductStt.ACTIVE) ? "Hoạt động" : "Không hoạt động");
                    System.out.println("Cập nhật trạng thái sản phẩm: ");
                    System.out.println("1. Hoạt động");
                    System.out.println("2. Không hoạt động");
                    System.out.print(Message.choice);
                    int set = Integer.parseInt(Console.scanner.nextLine());
                    if (set == 1)
                        updateStatusProduct.setProductStatus(true);
                    else if (set == 2)
                        updateStatusProduct.setProductStatus(false);
                    else
                        WarningMess.choiceFailure();
                    if (productRepository.edit(updateStatusProduct) != null) {
                        WarningMess.updateSuccess();
                    } else {
                        WarningMess.updateFailure();
                    }
                    System.out.println(Message.continuous);
                    String confirm = Console.scanner.nextLine();
                    stop = confirm.contains("y");
                } else {
                    WarningMess.objectNotExist();
                }
            } while (stop);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> search(String any) {
        List<Product> productList = productRepository.findByIndexes(Product.class, any);
        if (!productList.isEmpty()) {
            int maxPage = productList.size() / 10;
            int choice;
            int page = 1;
            try {
                do {
                    List<Product> productListPagination = productRepository.findByIndexesPagination(Product.class, any, page);
                    if (!productListPagination.isEmpty()){
                        System.out.printf("Tổng: %s sản phẩm \n",productList.size());
                        Header.products();
                        productListPagination.forEach(p -> System.out.printf(TableForm.products.column,
                                p.getProductId(),
                                p.getProductName(),
                                p.getManufacturer(),
                                p.getCreated(),
                                p.getBatch(),
                                p.getQuantity(),
                                p.getProductStatus() ? "Hoạt Động" : "Không hoạt động"));
                        System.out.println("1.<Trang trước> | 2.<Trang sau> | 3.<Thoát>");
                        System.out.print(Message.choice);
                        choice = Integer.parseInt(Console.scanner.nextLine());
                        switch (choice) {
                            case 1:
                                page--;
                                if (page <= 0)
                                    page = 1;
                                break;
                            case 2:
                                page++;
                                if (page > maxPage) {
                                    if (productList.size() % 10 == 0) {
                                        page = maxPage;
                                    } else {
                                        page = maxPage + 1;
                                    }
                                }
                                break;
                            case 3:
                                break;
                            default:
                                WarningMess.choiceFailure();
                        }
                    } else {
                        WarningMess.listEmpty();
                        choice = 3;
                    }
                } while (choice != 3);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        } else {
            WarningMess.listEmpty();
        }
        return productList;
    }
}
