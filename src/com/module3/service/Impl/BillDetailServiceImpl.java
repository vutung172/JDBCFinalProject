package com.module3.service.Impl;

import com.module3.MySql.Views.View;
import com.module3.entity.Bill;
import com.module3.entity.BillDetail;
import com.module3.entity.Product;
import com.module3.model.*;
import com.module3.repository.AccRepository;
import com.module3.repository.Impl.BillRepositoryImpl;
import com.module3.repository.Impl.RepositoryImpl;
import com.module3.service.BillService;
import com.module3.service.GeneralService;
import com.module3.util.Console;
import com.module3.util.MySqlConnect.MySQLConnect;
import com.module3.util.Storage.UserStorage;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class BillDetailServiceImpl implements BillService<BillDetail> {
    private RepositoryImpl<BillDetail> billDetailRepository;
    private RepositoryImpl<Bill> billRepository;
    private RepositoryImpl<Product> productRepository;

    public BillDetailServiceImpl() {
        this.billDetailRepository = new RepositoryImpl<>();
        this.billRepository = new RepositoryImpl<>();
        this.productRepository = new RepositoryImpl<>();
    }

    @Override
    public List<BillDetail> listAll(Boolean billType) {
        return null;
    }

    @Override
    public List<BillDetail> listAllByStatus(Boolean billType, Boolean permissionType) {
        return null;
    }

    @Override
    public BillDetail create(Boolean billType) {
        return null;
    }

    public BillDetail create(Bill bill) {
        try (Connection connection = new MySQLConnect().getConnection()) {
            connection.setAutoCommit(false);
            boolean stop = false;
            BillDetail billDetail = new BillDetail();
            connection.setSavepoint();
            Product product;
            int quantity = 0;
            do {
                System.out.println("Nhập vào mã sản phẩm: ");
                String productId = Console.scanner.nextLine();
                product = productRepository.findId(Product.class, productId);
                if (product != null) {
                    billDetail.setProductId(product.getProductId());
                    System.out.println("Nhập vào số lượng: ");
                    quantity = Integer.parseInt(Console.scanner.nextLine());
                    if (bill.getBillType().equals(BillType.EXPORT)) {
                        if (quantity > product.getQuantity()) {
                            WarningMess.outOfRange();
                        } else {
                            billDetail.setQuantity(quantity);
                            stop = true;
                        }
                    }else {
                        billDetail.setQuantity(quantity);
                        stop = true;
                    }
                } else {
                    WarningMess.objectNotExist();
                }
            } while (!stop);
            System.out.println("Nhập vào giá: ");
            billDetail.setPrice(Float.parseFloat(Console.scanner.nextLine()));
            Bill bill1 = billRepository.addIgnoreId(bill);
            billDetail.setBillId(bill1.getBillId());
            BillDetail billDetailSuccess = billDetailRepository.add(billDetail);
            if (billDetailSuccess != null) {
                /*if (billDetailRepository.findAbsoluteByIndexes(BillDetail.class,bill.getBillId(),product.getProductId()).size() != 1){
                    BillDetail updateBillDetail = billDetailRepository.findId(BillDetail.class,billDetail.getBillDetailId());
                    updateBillDetail.setQuantity(updateBillDetail.getQuantity()+quantity);
                    billDetailRepository.edit(updateBillDetail);
                }*/
                connection.commit();
                return billDetail;
            } else {
                WarningMess.createdFailure();
                connection.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Boolean billType, Boolean permissionType) {
        try{
            System.out.println("Nhập vào mã phiếu chi tiết muốn cập nhật");
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateFromBill(Bill bill,Boolean permissionType) {
        List<BillDetail> billDetailList;
        try {
            if (permissionType.equals(PermissionType.USER)){
                if (!bill.getEmployeeIdCreated().equals(UserStorage.employeeId)){
                    WarningMess.objectNotExist();
                    return false;
                }
            }
            if (bill != null) {
                billDetailList = billDetailRepository.findAbsoluteByIndexes(BillDetail.class, String.valueOf(bill.getBillId()),String.valueOf(bill.getBillId()));
                if (!billDetailList.isEmpty()) {
                    System.out.printf("Tổng: %s chi tiết phiếu \n", billDetailList.size());
                    Header.billDetails();
                    billDetailList.forEach(bd -> System.out.printf(TableForm.billDetails.column,
                            bd.getBillId(),
                            bd.getBillDetailId(),
                            productRepository.findId(Product.class, bd.getProductId()).getProductName(),
                            bd.getQuantity(),
                            bd.getPrice()));
                    System.out.println("1. Cập nhật chi tiết phiếu");
                    System.out.println("2. Thoát");
                    System.out.print(Message.choice);
                    int choice = Integer.parseInt(Console.scanner.nextLine());
                    switch (choice) {
                        case 1:
                            boolean stop = false;
                            System.out.println("Nhập mã phiếu chi tiết: ");
                            long id = Long.parseLong(Console.scanner.nextLine());
                            BillDetail updateBillDetail = billDetailRepository.findId(BillDetail.class, id);
                            do {
                                System.out.println("Nhập mã sản phẩm: ");
                                String productId = Console.scanner.nextLine();
                                if (productRepository.findId(Product.class, productId) != null) {
                                    updateBillDetail.setProductId(productId);
                                    stop = true;
                                } else {
                                    WarningMess.objectNotExist();
                                }
                            } while (!stop);
                            System.out.println("Mời cập nhật số lượng: ");
                            updateBillDetail.setQuantity(Integer.parseInt(Console.scanner.nextLine()));
                            System.out.println("Mời cập nhật giá");
                            updateBillDetail.setPrice(Float.parseFloat(Console.scanner.nextLine()));
                            BillDetail updateDetail = billDetailRepository.edit(updateBillDetail);
                            if (updateDetail != null) {
                                WarningMess.updateSuccess();
                                return true;
                            } else {
                                WarningMess.updateFailure();
                                return false;
                            }
                        case 2:
                            break;
                        default:
                            WarningMess.choiceFailure();
                    }
                } else {
                    WarningMess.dataNotFound();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<BillDetail> search(Boolean billType,Boolean permissionType, String any) {
        List<BillDetail> billDetails = new ArrayList<>();
        try {
            billDetails = billDetailRepository.findRelativeByIndexes(BillDetail.class,any,any);
            if (!billDetails.isEmpty()) {
                System.out.printf("Tổng: %s tải khoản \n", billDetails.size());
                Header.billDetails();
                billDetails.forEach(bd -> System.out.printf(TableForm.billDetails.column,
                        bd.getBillId(),
                        bd.getBillDetailId(),
                        productRepository.findId(Product.class, bd.getProductId()).getProductName(),
                        bd.getQuantity(),
                        bd.getPrice()));
            } else {
                WarningMess.dataNotFound();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return billDetails;
    }

    @Override
    public List<BillDetail> billDetail(Boolean billType) {
        return null;
    }

    @Override
    public BillDetail billApproval(Boolean billType) {
        return null;
    }
}
