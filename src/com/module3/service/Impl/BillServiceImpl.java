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
import java.sql.Savepoint;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BillServiceImpl implements BillService<Bill>,Message,DateTimeFormat {
    private RepositoryImpl<Bill> billRepository;
    private RepositoryImpl<BillDetail> billDetailRepository;
    private RepositoryImpl<Product> productRepository;
    private RepositoryImpl<Employee> employeeRepository;
    private BillDetailServiceImpl billDetailService;

    public BillServiceImpl() {
        this.billRepository = new RepositoryImpl<>();
        this.billDetailRepository = new RepositoryImpl<>();
        this.employeeRepository = new RepositoryImpl<>();
        this.productRepository = new RepositoryImpl<>();
        this.billDetailService = new BillDetailServiceImpl();
    }

    @Override
    public List<Bill> listAll(Boolean billType) {
        List<Bill> billList = billRepository.findAll(Bill.class).stream().filter(b -> b.getBillType().equals(billType)).toList();
        if (!billList.isEmpty()) {
            int maxIndex = billList.size() / 10;
            int choice;
            int index = 1;
            try {
                do {
                    List<Bill> updateBill = billRepository.findAllByPagination(Bill.class, index).stream().filter(b -> b.getBillType().equals(billType)).toList();
                    if (!updateBill.isEmpty()) {
                        System.out.printf("Tổng: %s phiếu\n", billList.size());
                        Header.bills();
                        updateBill.stream().filter(b -> b.getBillType().equals(billType)).forEach(b -> System.out.printf(TableForm.bills.column,
                                b.getBillId(),
                                b.getBillCode(),
                                b.getBillType().equals(BillType.IMPORT) ? "Phiếu nhập" : "Phiếu xuất",
                                b.getEmployeeIdCreated(),
                                DateTimeFormat.super.dateTransfer(b.getCreated()),
                                b.getEmployeeIdAuth(),
                                DateTimeFormat.super.dateTransfer(b.getAuthDate()),
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
    public List<Bill> listAllByStatus(Boolean billType, Boolean permissionType) {
        List<Bill> billList = new ArrayList<>();
        try {
            boolean stop = false;
            do {
                System.out.println("Nhập vào trạng thái phiếu muốn xem: ");
                System.out.println("1. Tạo");
                System.out.println("2. Hủy");
                System.out.println("3. Duyệt");
                System.out.println("4. Thoát");
                System.out.print(Message.choice);
                int choice = Integer.parseInt(Console.scanner.nextLine());
                switch (choice){
                    case 1:
                        billList = billRepository.findByMark(Bill.class,ConstStatus.BillStt.CREATE);
                        break;
                    case 2:
                        billList = billRepository.findByMark(Bill.class,ConstStatus.BillStt.CANCEL);
                        break;
                    case 3:
                        billList = billRepository.findByMark(Bill.class,ConstStatus.BillStt.APPROVAL);
                        break;
                    case 4:
                        stop = true;
                        break;
                    default:
                        WarningMess.choiceFailure();
                }
                if (!billList.isEmpty()){
                    if (permissionType.equals(PermissionType.ADMIN)){
                        Header.bills();
                        billList.stream().filter(b -> b.getBillType().equals(billType)).forEach(b -> System.out.printf(TableForm.bills.column,
                                b.getBillId(),
                                b.getBillCode(),
                                b.getBillType().equals(BillType.IMPORT) ? "Phiếu nhập" : "Phiếu xuất",
                                b.getEmployeeIdCreated(),
                                DateTimeFormat.super.dateTransfer(b.getCreated()),
                                b.getEmployeeIdAuth(),
                                DateTimeFormat.super.dateTransfer(b.getAuthDate()),
                                b.getBillStatus().equals(ConstStatus.BillStt.CREATE) ? "Tạo" : b.getBillStatus().equals(ConstStatus.BillStt.APPROVAL) ? "Duyệt" : "Hủy"));
                    } else {
                        Header.bills();
                        billList.stream().filter(b -> b.getBillType().equals(billType)).filter(b -> b.getEmployeeIdCreated().equals(UserStorage.employeeId)).forEach(b -> System.out.printf(TableForm.bills.column,
                                b.getBillId(),
                                b.getBillCode(),
                                b.getBillType().equals(BillType.IMPORT) ? "Phiếu nhập" : "Phiếu xuất",
                                b.getEmployeeIdCreated(),
                                DateTimeFormat.super.dateTransfer(b.getCreated()),
                                b.getEmployeeIdAuth(),
                                DateTimeFormat.super.dateTransfer(b.getAuthDate()),
                                b.getBillStatus().equals(ConstStatus.BillStt.CREATE) ? "Tạo" : b.getBillStatus().equals(ConstStatus.BillStt.APPROVAL) ? "Duyệt" : "Hủy"));
                    }
                    stop = !Message.super.confirm();
                }
            }while (!stop);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
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
                connection.setSavepoint();
                if (bill != null) {
                    BillDetail billDetail;
                    do {
                        billDetail = billDetailService.create(bill);
                        stop = Message.super.confirm();
                    } while (stop);
                    if (billDetail != null) {
                        connection.commit();
                        return bill;
                    } else {
                        connection.rollback();
                    }
                } else {
                    connection.rollback();
                }
                stop = Message.super.confirm();
            } while (stop);
        } catch (NumberFormatException | SQLException nfe) {
            nfe.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Boolean billType,Boolean permissionType) {
        List<Bill> updateBills = new ArrayList<>();
        try {
            boolean stop = false;
            do {
                System.out.println("Nhập vào mã phiếu hoặc mã code muốn cập nhật: ");
                String key = Console.scanner.nextLine();
                if (permissionType.equals(PermissionType.USER)) {
                    updateBills = billRepository.findAbsoluteByIndexes(Bill.class, key, key).stream()
                            .filter(b -> b.getBillType().equals(billType))
                            .filter(b -> b.getEmployeeIdCreated().equals(UserStorage.employeeId))
                            .toList();
                } else {
                    updateBills = billRepository.findAbsoluteByIndexes(Bill.class, key,key).stream()
                            .filter(b -> b.getBillType().equals(billType))
                            .toList();
                }
                if (!updateBills.isEmpty()) {
                    Header.bills();
                    updateBills.stream()
                            .filter(b -> b.getBillType().equals(billType))
                            .forEach(b -> System.out.printf(TableForm.bills.column,
                            b.getBillId(),
                            b.getBillCode(),
                            b.getBillType().equals(BillType.IMPORT) ? "Phiếu nhập" : "Phiếu xuất",
                            b.getEmployeeIdCreated(),
                            DateTimeFormat.super.dateTransfer(b.getCreated()),
                            b.getEmployeeIdAuth(),
                            DateTimeFormat.super.dateTransfer(b.getAuthDate()),
                            b.getBillStatus().equals(ConstStatus.BillStt.CREATE) ? "Tạo" : b.getBillStatus().equals(ConstStatus.BillStt.APPROVAL) ? "Duyệt" : "Hủy"));
                    System.out.println("1. Cập nhật phiếu ");
                    System.out.println("2. Cập nhật chi tiết phiếu ");
                    System.out.println("3. Thêm chi tiết phiếu ");
                    System.out.println("4. Thoát ");
                    System.out.print(Message.choice);
                    int choice = Integer.parseInt(Console.scanner.nextLine());
                    if (updateBills.size() == 1) {
                        Bill bill = updateBills.stream()
                                .findFirst()
                                .orElse(null);
                        if (!bill.getBillStatus().equals(ConstStatus.BillStt.APPROVAL)) {
                            switch (choice) {
                                case 1:
                                    updateById(billType, bill.getBillId());
                                    break;
                                case 2:
                                    billDetailService.updateFromBill(bill,UserStorage.currentPermission);
                                    break;
                                case 3:
                                    do {
                                        billDetailService.create(bill);
                                        stop = Message.super.confirm();
                                    } while (stop);
                                    break;
                                case 4:
                                    break;
                                default:
                                    WarningMess.choiceFailure();
                            }
                        } else {
                            PrintForm.warning("Phiếu đã được duyệt");
                        }
                    } else {
                        System.out.println("Nhập mã của phiếu muốn cập nhật: ");
                        long id = Long.parseLong(Console.scanner.nextLine());
                        Bill bill = updateBills.stream()
                                .filter(b -> b.getBillId().equals(id))
                                .filter(b -> b.getBillType().equals(billType))
                                .findFirst()
                                .orElse(null);
                        switch (choice) {
                            case 1:
                                if (bill != null) {
                                    if (!bill.getBillStatus().equals(ConstStatus.BillStt.APPROVAL)) {
                                        updateById(billType, id);
                                    } else {
                                        PrintForm.warning("Phiếu đã được duyệt");
                                    }
                                } else {
                                    WarningMess.objectNotExist();
                                }
                                break;
                            case 2:
                                if (bill != null) {
                                    if (!bill.getBillStatus().equals(ConstStatus.BillStt.APPROVAL)) {
                                        billDetailService.updateFromBill(bill,UserStorage.currentPermission);
                                    } else {
                                        PrintForm.warning("Phiếu đã được duyệt");
                                    }

                                } else {
                                    WarningMess.objectNotExist();
                                }
                                break;
                            case 3:
                                if (bill != null) {
                                    if (!bill.getBillStatus().equals(ConstStatus.BillStt.APPROVAL)) {
                                        do {
                                            billDetailService.create(bill);
                                            stop = Message.super.confirm();
                                        } while (stop);
                                    } else {
                                        PrintForm.warning("Phiếu đã được duyệt");
                                    }
                                } else {
                                    WarningMess.objectNotExist();
                                }
                                break;
                            default:
                                WarningMess.choiceFailure();
                        }
                    }
                    stop = Message.super.confirm();
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
    public List<Bill> search(Boolean billType,Boolean permissionType, String any) {
        try {
            List<Bill> billList = billRepository.findRelativeByIndexes(Bill.class, any,any).stream()
                    .filter(b -> b.getBillType().equals(billType))
                    .toList();
            if (!billList.isEmpty()) {
                System.out.printf("Tổng: %s tải khoản \n", billList.size());
                Header.bills();
                billList.stream().filter(b -> b.getBillType().equals(billType)).forEach(b -> System.out.printf(TableForm.bills.column,
                        b.getBillId(),
                        b.getBillCode(),
                        b.getBillType().equals(BillType.IMPORT) ? "Phiếu nhập" : "Phiếu xuất",
                        b.getEmployeeIdCreated(),
                        DateTimeFormat.super.dateTransfer(b.getCreated()),
                        b.getEmployeeIdAuth(),
                        DateTimeFormat.super.dateTransfer(b.getAuthDate()),
                        b.getBillStatus().equals(ConstStatus.BillStt.CREATE) ? "Tạo" : b.getBillStatus().equals(ConstStatus.BillStt.APPROVAL) ? "Duyệt" : "Hủy"));
                System.out.println("1. Cập nhật | 2. Duyệt phiếu | 3. <Thoát>");
                System.out.print(Message.choice);
                int choice = Integer.parseInt(Console.scanner.nextLine());
                switch (choice) {
                    case 1:
                        update(billType,UserStorage.currentPermission);
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
        List<BillDetail> billDetailList = new ArrayList<>();
        List<Bill> billList = new ArrayList<>();
        try {
            boolean stop = false;
            do {
                System.out.println("Mời bạn nhập mã phiếu hoặc mã code");
                long billId = Long.parseLong(Console.scanner.nextLine());
                billList = billRepository.findAbsoluteByIndexes(Bill.class, String.valueOf(billId),String.valueOf(billId)).stream()
                        .filter(b -> b.getBillType().equals(billType))
                        .toList();
                if (!billList.isEmpty()) {
                    Header.billDetails();
                    for (Bill bill : billList) {
                        if (bill.getBillType().equals(billType)) {
                            billDetailList = billDetailRepository.findAbsoluteByIndexes(BillDetail.class, String.valueOf(bill.getBillId()),String.valueOf(bill.getBillId()));
                            billDetailList.stream().forEach(bd -> System.out.printf(TableForm.billDetails.column,
                                    bd.getBillId(),
                                    bd.getBillDetailId(),
                                    productRepository.findId(Product.class, bd.getProductId()).getProductName(),
                                    bd.getQuantity(),
                                    bd.getPrice()));
                        } else {
                            WarningMess.objectNotExist();
                        }
                    }
                    System.out.println("1. Cập nhật chi tiết phiếu");
                    System.out.println("2. Thoát");
                    System.out.print(Message.choice);
                    int choice = Integer.parseInt(Console.scanner.nextLine());
                    switch (choice) {
                        case 1:
                            billDetailService.update(billType,UserStorage.currentPermission);
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
                List<Bill> updateBills = billRepository.findAbsoluteByIndexes(Bill.class, key, key).stream()
                        .filter(b -> b.getBillType().equals(billType))
                        .toList();
                if (!updateBills.isEmpty()) {
                    System.out.printf("Tổng %s phiếu",updateBills.size());
                    Header.bills();
                    updateBills.stream().filter(b -> b.getBillType().equals(billType)).forEach(b -> System.out.printf(TableForm.bills.column,
                            b.getBillId(),
                            b.getBillCode(),
                            b.getBillType().equals(BillType.IMPORT) ? "Phiếu nhập" : "Phiếu xuất",
                            b.getEmployeeIdCreated(),
                            DateTimeFormat.super.dateTransfer(b.getCreated()),
                            b.getEmployeeIdAuth(),
                            DateTimeFormat.super.dateTransfer(b.getAuthDate()),
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
                                if (!updateBill.getBillStatus().equals(ConstStatus.BillStt.APPROVAL)){
                                    updateBill.setBillStatus(ConstStatus.BillStt.APPROVAL);
                                    if (billType.equals(BillType.IMPORT)) {
                                        List<BillDetail> billDetails = billDetailService.search(BillType.IMPORT,UserStorage.currentPermission, String.valueOf(updateBill.getBillId()));
                                        billDetails.forEach(bd -> {
                                            Product updateProduct = productRepository.findId(Product.class, bd.getProductId());
                                            updateProduct.setQuantity(updateProduct.getQuantity() + bd.getQuantity());
                                            productRepository.edit(updateProduct);
                                        });
                                    } else {
                                        List<BillDetail> billDetails = billDetailService.search(BillType.EXPORT,UserStorage.currentPermission, String.valueOf(updateBill.getBillId()));
                                        billDetails.forEach(bd -> {
                                            Product updateProduct = productRepository.findId(Product.class, bd.getProductId());
                                            updateProduct.setQuantity(updateProduct.getQuantity() - bd.getQuantity());
                                            productRepository.edit(updateProduct);
                                        });
                                    }
                                    billRepository.edit(updateBill);
                                    WarningMess.updateSuccess();
                                } else {
                                    PrintForm.warning("Phiếu đã được duyệt");
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
                                    if (!updateBill.getBillStatus().equals(ConstStatus.BillStt.APPROVAL)){
                                        updateBill.setBillStatus(ConstStatus.BillStt.APPROVAL);
                                        if (billType.equals(BillType.IMPORT)) {
                                            List<BillDetail> billDetails = billDetailService.search(BillType.IMPORT,UserStorage.currentPermission, String.valueOf(updateBill.getBillId()));
                                            billDetails.forEach(bd -> {
                                                Product updateProduct = productRepository.findId(Product.class, bd.getProductId());
                                                updateProduct.setQuantity(updateProduct.getQuantity() + bd.getQuantity());
                                                productRepository.edit(updateProduct);
                                            });
                                        } else {
                                            List<BillDetail> billDetails = billDetailService.search(BillType.EXPORT,UserStorage.currentPermission, String.valueOf(updateBill.getBillId()));
                                            billDetails.forEach(bd -> {
                                                Product updateProduct = productRepository.findId(Product.class, bd.getProductId());
                                                updateProduct.setQuantity(updateProduct.getQuantity() - bd.getQuantity());
                                                productRepository.edit(updateProduct);
                                            });
                                        }
                                        billRepository.edit(updateBill);
                                        WarningMess.updateSuccess();
                                    } else {
                                        PrintForm.warning("Phiếu nhập đã được duyệt");
                                    }
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
                    stop = Message.super.confirm();
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
