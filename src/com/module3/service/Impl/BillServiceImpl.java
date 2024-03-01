package com.module3.service.Impl;

import com.module3.MySql.Views.View;
import com.module3.entity.*;
import com.module3.entity.Bill;
import com.module3.entity.Bill;
import com.module3.model.*;
import com.module3.repository.Impl.RepositoryImpl;
import com.module3.service.BillService;
import com.module3.service.GeneralService;
import com.module3.util.Console;
import com.module3.util.Font.PrintForm;
import com.module3.util.MySqlConnect.MySQLConnect;
import com.module3.util.Storage.UserStorage;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class BillServiceImpl implements BillService<Bill> {
    private RepositoryImpl<Bill> billRepository;
    private RepositoryImpl<BillDetail> billDetailRepository;
    private RepositoryImpl<Product> productRepository;
    private RepositoryImpl<Employee> employeeRepository;
    private BillDetailServiceImpl billDetailService;

    public BillServiceImpl() {
        this.billRepository = new RepositoryImpl<>();
        this.employeeRepository = new RepositoryImpl<>();
        this.productRepository = new RepositoryImpl<>();
        this.billDetailService = new BillDetailServiceImpl();
        this.billDetailRepository = new RepositoryImpl<>();
    }


    @Override
    public List<Bill> listAll(Boolean billType) {
        List<Bill> billList = billRepository.findAll(Bill.class);
        if (!billList.isEmpty()) {
            int maxIndex = billList.size() / 10;
            int choice;
            int index = 1;
            try {
                do {
                    List<Bill> updateBill = billRepository.findAllByPagination(Bill.class, index);
                    if (!updateBill.isEmpty()) {
                        System.out.printf("Tổng: %s tải khoản \n", billList.size());
                        Header.bills();
                        updateBill.stream().filter(b -> b.getBillType().equals(billType)).forEach(b -> System.out.printf(TableForm.bills.column,
                                b.getBillId(),
                                b.getBillCode(),
                                b.getBillType().equals(BillType.IMPORT) ? "Phiếu nhập" : "Phiếu xuất",
                                b.getEmployeeIdCreated(),
                                b.getCreated(),
                                b.getEmployeeIdAuth(),
                                b.getAuthDate(),
                                b.getBillStatus().equals(ConstStatus.BillStt.CREATE) ? "Tạo" : b.getBillStatus().equals(ConstStatus.BillStt.APPROVAL) ? "Duyệt" : "Hủy"));
                        System.out.println("1.<<Trang trước | 2.Trang sau>> | 3.<Thoát>");
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
                                    if (billList.size() % 10 == 0) {
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
        return billList;
    }

    @Override
    public Bill create(Boolean billType) {
        try (Connection connection = new MySQLConnect().getConnection()) {
            connection.setAutoCommit(false);
            boolean stop;
            do {
                stop = false;
                Bill bill = new Bill();
                bill.setBillCode(("HD" + System.currentTimeMillis()).substring(0, 9));
                bill.setBillType(billType);
                bill.setEmployeeIdCreated(UserStorage.employeeId);
                bill.setCreated(new Date());
                do {
                    System.out.println("Mời bạn nhập mã nhân viên duyệt phiếu: ");
                    String authEmp = Console.scanner.nextLine();
                    Employee employee = employeeRepository.findId(Employee.class, authEmp);
                    if (employee != null) {
                        bill.setEmployeeIdAuth(authEmp);
                        stop = true;
                    } else {
                        WarningMess.objectNotExist();
                    }
                } while (!stop);
                bill.setAuthDate(new Date());
                bill.setBillStatus(ConstStatus.BillStt.CREATE);
                Bill billSuccess = billRepository.add(bill);
                if (billSuccess != null) {
                    BillDetail billDetail;
                    do {
                        billDetail = billDetailService.create(billSuccess);
                        System.out.println(Message.continuous);
                        String confirm = Console.scanner.nextLine();
                        stop = confirm.contains("y");
                    } while (stop);
                    if (billDetail != null) {
                        if (billRepository.findId(Bill.class, bill.getBillId()) == null) {
                            billRepository.add(bill);
                        }
                        connection.commit();
                        return bill;
                    } else {
                        connection.rollback();
                    }
                }
                System.out.println(Message.continuous);
                String confirm = Console.scanner.nextLine();
                stop = confirm.contains("y");
            } while (stop);
            connection.commit();
        } catch (NumberFormatException | SQLException nfe) {
            nfe.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Boolean billType) {
        try {
            boolean stop = false;
            do {
                System.out.println("Nhập vào mã phiếu hoặc mã code muốn cập nhật: ");
                String key = Console.scanner.nextLine();
                List<Bill> updateBill = billRepository.findByIndexes(Bill.class, key);
                if (!updateBill.isEmpty()) {
                    Header.bills();
                    updateBill.stream().filter(b -> b.getBillType().equals(billType) && !b.getBillStatus().equals(ConstStatus.BillStt.APPROVAL)).forEach(b -> System.out.printf(TableForm.bills.column,
                            b.getBillId(),
                            b.getBillCode(),
                            b.getBillType().equals(BillType.IMPORT) ? "Phiếu nhập" : "Phiếu xuất",
                            b.getEmployeeIdCreated(),
                            b.getCreated(),
                            b.getEmployeeIdAuth(),
                            b.getAuthDate(),
                            b.getBillStatus().equals(ConstStatus.BillStt.CREATE) ? "Tạo" : b.getBillStatus().equals(ConstStatus.BillStt.APPROVAL) ? "Duyệt" : "Hủy"));
                    System.out.println("1. Cập nhật phiếu ");
                    System.out.println("2. Cập nhật chi tiết phiếu ");
                    System.out.println("3. Thêm chi tiết phiếu ");
                    System.out.println("4. Thoát ");
                    System.out.print(Message.choice);
                    int choice = Integer.parseInt(Console.scanner.nextLine());
                    if (updateBill.size() == 1) {
                        Bill bill = updateBill.stream().findFirst().orElse(null);
                        switch (choice) {
                            case 1:
                                updateById(billType, bill.getBillId());
                                break;
                            case 2:
                                billDetailService.updateFromBill(bill);
                                break;
                            case 3:
                                billDetailService.create(bill);
                                break;
                            case 4:
                                break;
                            default:
                                WarningMess.choiceFailure();
                        }
                    } else {
                        switch (choice) {
                            case 1:
                                System.out.println("Nhập mã của phiếu muốn cập nhật: ");
                                long id = Long.parseLong(Console.scanner.nextLine());
                                Bill update = updateById(billType, id);
                                break;
                            case 2:
                                System.out.println("Nhập mã của phiếu muốn cập nhật: ");
                                long id1 = Long.parseLong(Console.scanner.nextLine());
                                Bill updateDetail = updateBill.stream().filter(b -> b.getBillId().equals(id1)).findFirst().orElse(null);
                                if (updateDetail != null) {
                                    billDetailService.updateFromBill(updateDetail);
                                } else {
                                    WarningMess.objectNotExist();
                                }
                                break;
                            case 3:
                                break;
                            default:
                                WarningMess.choiceFailure();
                        }
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
        return false;
    }

    public Bill updateById(Boolean billType, long billId) {
        try {
            Bill updateBill = billRepository.findId(Bill.class, billId);
            if (updateBill != null) {
                boolean stop = false;
                System.out.println("Cập nhật mã nhân viên duyệt");
                do {
                    System.out.println("Mời bạn nhập mã nhân viên duyệt phiếu: ");
                    String authEmp = Console.scanner.nextLine();
                    Employee employee = employeeRepository.findId(Employee.class, authEmp);
                    if (employee != null) {
                        updateBill.setEmployeeIdAuth(authEmp);
                        stop = true;
                    } else {
                        WarningMess.objectNotExist();
                    }
                } while (!stop);
                System.out.println("Cập nhật trạng thái phiếu: ");
                System.out.println("1. Tạo ");
                System.out.println("2. Hủy ");
                System.out.println("3. Duyệt ");
                System.out.print(Message.choice);
                int choice = Integer.parseInt(Console.scanner.nextLine());
                switch (choice) {
                    case 1:
                        updateBill.setBillStatus(ConstStatus.BillStt.CREATE);
                        billRepository.edit(updateBill);
                        break;
                    case 2:
                        updateBill.setBillStatus(ConstStatus.BillStt.CANCEL);
                        billRepository.edit(updateBill);
                        break;
                    case 3:
                        if (updateBill.getEmployeeIdAuth().equals(UserStorage.employeeId)) {
                            updateBill.setBillStatus(ConstStatus.BillStt.APPROVAL);
                            updateBill.setEmployeeIdAuth(UserStorage.employeeId);
                            updateBill.setAuthDate(new Date());
                            billRepository.edit(updateBill);
                        } else {
                            PrintForm.warning("Người duyệt không phù hợp");
                        }
                        break;
                    default:
                        WarningMess.choiceFailure();
                }
                return updateBill;
            } else {
                WarningMess.objectNotExist();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Bill> search(Boolean billType, String any) {
        try {
            List<Bill> billList = billRepository.findByIndexes(Bill.class, any);
            if (!billList.isEmpty()) {
                System.out.printf("Tổng: %s tải khoản \n", billList.size());
                Header.bills();
                billList.stream().filter(b -> b.getBillType().equals(billType)).forEach(b -> System.out.printf(TableForm.bills.column,
                        b.getBillId(),
                        b.getBillCode(),
                        b.getBillType().equals(BillType.IMPORT) ? "Phiếu nhập" : "Phiếu xuất",
                        b.getEmployeeIdCreated(),
                        b.getCreated(),
                        b.getEmployeeIdAuth(),
                        b.getAuthDate(),
                        b.getBillStatus().equals(ConstStatus.BillStt.CREATE) ? "Tạo" : b.getBillStatus().equals(ConstStatus.BillStt.APPROVAL) ? "Duyệt" : "Hủy"));
                System.out.println("1. Cập nhật | 2. Duyệt phiếu | 3. <Thoát>");
                System.out.print(Message.choice);
                int choice = Integer.parseInt(Console.scanner.nextLine());
                switch (choice) {
                    case 1:
                        update(billType);
                        break;
                    case 2:
                        billApproval(billType);
                        break;
                    case 3:
                        break;
                    default:
                        WarningMess.choiceFailure();
                }
                return billList;
            } else {
                WarningMess.dataNotFound();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Bill> billDetail(Boolean billType) {
        List<BillDetail> billDetailList;
        List<Bill> billList = new ArrayList<>();
        try {
            boolean stop = false;
            do {
                System.out.println("Mời bạn nhập mã phiếu hoặc mã code");
                long billId = Long.parseLong(Console.scanner.nextLine());
                billList = billRepository.findByIndexes(Bill.class, String.valueOf(billId));
                if (!billList.isEmpty()) {
                    Header.billDetails();
                    for (Bill bill : billList) {
                        if (bill.getBillType().equals(billType)) {
                            billDetailService.search(billType, String.valueOf(bill.getBillId()));
                        } else {
                            WarningMess.objectNotExist();
                        }
                    }
                    System.out.println("1. Cập nhật chi tiết phiếu");
                    System.out.println("2. Thoát");
                    int choice = Integer.parseInt(Console.scanner.nextLine());
                    switch (choice) {
                        case 1:
                            billDetailService.update(billType);
                            break;
                        case 2:
                            break;
                        default:
                            WarningMess.choiceFailure();
                    }


                } else {
                    WarningMess.objectNotExist();
                }
            } while (stop);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return billList;
    }

    @Override
    public Bill billApproval(Boolean billType) {
        try {
            boolean stop = false;
            do {
                System.out.println("Nhập vào mã phiếu hoặc mã code muốn duyệt: ");
                String key = Console.scanner.nextLine();
                List<Bill> updateBills = billRepository.findByIndexes(Bill.class, key);
                if (!updateBills.isEmpty()) {
                    Header.bills();
                    updateBills.stream().filter(b -> b.getBillType().equals(billType) && !b.getBillStatus().equals(ConstStatus.BillStt.APPROVAL)).forEach(b -> System.out.printf(TableForm.bills.column,
                            b.getBillId(),
                            b.getBillCode(),
                            b.getBillType().equals(BillType.IMPORT) ? "Phiếu nhập" : "Phiếu xuất",
                            b.getEmployeeIdCreated(),
                            b.getCreated(),
                            b.getEmployeeIdAuth(),
                            b.getAuthDate(),
                            b.getBillStatus().equals(ConstStatus.BillStt.CREATE) ? "Tạo" : b.getBillStatus().equals(ConstStatus.BillStt.APPROVAL) ? "Duyệt" : "Hủy"));
                    System.out.println("1. Duyệt phiếu");
                    System.out.println("2. Thoát");
                    System.out.print(Message.choice);
                    int choice = Integer.parseInt(Console.scanner.nextLine());
                    Bill updateBill;
                    if (updateBills.size() == 1) {
                        updateBill = updateBills.stream().findFirst().orElse(null);
                        switch (choice) {
                            case 1:
                                updateBill.setBillStatus(ConstStatus.BillStt.APPROVAL);
                                if (billType.equals(BillType.IMPORT)) {
                                    List<BillDetail> billDetails = billDetailService.search(BillType.IMPORT, String.valueOf(updateBill.getBillId()));
                                    billDetails.forEach(bd -> {
                                        Product updateProduct = productRepository.findId(Product.class, bd.getProductId());
                                        updateProduct.setQuantity(updateProduct.getQuantity() + bd.getQuantity());
                                    });
                                } else {
                                    List<BillDetail> billDetails = billDetailService.search(BillType.EXPORT, String.valueOf(updateBill.getBillId()));
                                    billDetails.forEach(bd -> {
                                        Product updateProduct = productRepository.findId(Product.class, bd.getProductId());
                                        updateProduct.setQuantity(updateProduct.getQuantity() - bd.getQuantity());
                                    });
                                }
                                break;
                            case 2:
                                break;
                            default:
                                WarningMess.choiceFailure();
                        }
                    } else {
                        switch (choice) {
                            case 1:
                                System.out.println("Nhập mã của phiếu muốn duyệt: ");
                                long id = Long.parseLong(Console.scanner.nextLine());
                                updateBill = billRepository.findId(Bill.class, id);
                                if (updateBill != null) {
                                    updateBill.setBillStatus(ConstStatus.BillStt.APPROVAL);
                                } else {
                                    WarningMess.objectNotExist();
                                }
                                break;
                            case 2:
                                break;
                            default:
                                WarningMess.choiceFailure();
                        }
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

}
