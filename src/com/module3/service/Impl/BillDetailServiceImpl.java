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
    public BillDetail create(Boolean billType) {
        try {
            boolean stop = false;
            System.out.println("Nhập");
            do {

            } while (stop);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BillDetail create(Bill bill) {
        try (Connection connection = new MySQLConnect().getConnection()) {
            connection.setAutoCommit(false);
            boolean stop = false;
            BillDetail billDetail = new BillDetail();
            do {
                System.out.println("Nhập vào mã sản phẩm: ");
                String productId = Console.scanner.nextLine();
                Product product = productRepository.findId(Product.class, productId);
                if (product != null) {
                    billDetail.setProductId(product.getProductId());
                    System.out.println("Nhập vào số lượng: ");
                    int quantity = Integer.parseInt(Console.scanner.nextLine());
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
            billDetail.setBillId(bill.getBillId());
            if (billDetailRepository.add(billDetail) != null) {
                if (billRepository.findId(Bill.class, bill.getBillId()) == null) {
                    billRepository.add(bill);
                }
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
    public boolean update(Boolean billType) {
        try {
            System.out.println("Nhập mã phiếu chi tiết muốn cập nhật: ");
            long billDetailId = Long.parseLong(Console.scanner.nextLine());
            BillDetail updateBillDetail = billDetailRepository.findId(BillDetail.class, billDetailId);
            if (updateBillDetail != null) {
                System.out.println("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateFromBill(Bill bill) {
        List<BillDetail> billDetailList;
        try {
            if (bill != null) {
                billDetailList = billDetailRepository.findByIndexesInView(BillDetail.class, String.valueOf(bill.getBillId()), View.billToBillDetail);
                if (!billDetailList.isEmpty()) {
                    System.out.printf("Tổng: %s chi tiết phiếu \n", billDetailList.size());
                    Header.billDetails();
                    billDetailList.forEach(bd -> System.out.printf(TableForm.billDetails.column,
                            bd.getBillId(),
                            bd.getBillDetailId(),
                            bd.getProductId(),
                            productRepository.findId(Product.class, bd.getQuantity()).getProductName(),
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
    public List<BillDetail> search(Boolean billType, String any) {
        List<BillDetail> billDetails = new ArrayList<>();
        try {
            billDetails = billDetailRepository.findByIndexes(BillDetail.class, any);
            if (!billDetails.isEmpty()) {
                System.out.printf("Tổng: %s tải khoản \n", billDetails.size());
                Header.billDetails();
                billDetails.forEach(bd -> System.out.printf(TableForm.billDetails.column,
                        bd.getBillId(),
                        bd.getBillDetailId(),
                        bd.getProductId(),
                        productRepository.findId(Product.class, bd.getQuantity()).getProductName(),
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
