package com.module3.service.Impl;

import com.module3.entity.Account;
import com.module3.entity.Employee;
import com.module3.model.*;
import com.module3.repository.AccRepository;
import com.module3.repository.Impl.AccRepositoryImpl;
import com.module3.repository.Impl.RepositoryImpl;
import com.module3.service.AccountService;
import com.module3.service.GeneralService;
import com.module3.util.Console;

import java.util.List;

public class AccountServiceImpl implements AccountService, GeneralService<Account> {
    private RepositoryImpl<Account> accountRepository;
    private RepositoryImpl<Employee> employeeRepository;
    private AccRepository accRepository;

    public AccountServiceImpl() {
        this.accountRepository = new RepositoryImpl<>();
        this.employeeRepository = new RepositoryImpl<>();
        this.accRepository = new AccRepositoryImpl();
    }

    @Override
    public Account login(String userName, String password) {
       return accountRepository.authenticator(Account.class,userName,password);
    }

    @Override
    public List<Account> listAll() {
        List<Account> accountList = accountRepository.findAll(Account.class);
        if (!accountList.isEmpty()) {
            int maxIndex = accountList.size() / 10;
            int choice;
            int index = 1;
            try {
                do {
                    List<Account> accountListPagination = accountRepository.findAllByPagination(Account.class, index);
                    if (!accountListPagination.isEmpty()) {
                        System.out.printf("Tổng: %s tải khoản \n",accountList.size());
                        Header.accounts();
                        accountListPagination.stream().forEach(a -> System.out.printf(TableForm.accounts.column,
                                a.getAccId(),
                                a.getUserName(),
                                a.getPassword(),
                                a.getPermission().equals(PermissionType.USER) ? "User":"Admin",
                                a.getEmployeeId(),
                                a.getAccountStatus().equals(ConstStatus.AccountStt.ACTIVE)?"Hoạt động":"Không hoạt động"));
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
                                    if (accountList.size() % 10 == 0) {
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
        return accountList;
    }

    @Override
    public Account create() {
        try {
            boolean stop;
            do {
                stop = false;
                Account account = new Account();
                System.out.println("Mời bạn nhập tên đăng nhập: ");
                account.setUserName(Console.scanner.nextLine());
                System.out.println("Mời bạn nhập mật khẩu: ");
                account.setPassword(Console.scanner.nextLine());
                System.out.println("Mời chọn quyền hạn: ");
                System.out.println("1. Admin ");
                System.out.println("2. User ");
                System.out.print(Message.choice);
                int choice = Integer.parseInt(Console.scanner.nextLine());
                if (choice == 1) {
                    account.setPermission(PermissionType.ADMIN);
                } else {
                    account.setPermission(PermissionType.USER);
                }
                do {
                    System.out.println("Mời bạn nhập mã nhân viên: ");
                    String empId = Console.scanner.nextLine();
                    Employee employee = employeeRepository.findId(Employee.class,empId);
                    if (employee != null){
                        account.setEmployeeId(empId);
                        stop = true;
                    } else {
                        WarningMess.objectNotExist();
                    }
                } while (!stop);
                System.out.println("Mời chọn trạng thái của tài khoản: ");
                System.out.println("1. Hoạt động");
                System.out.println("2. Không hoạt động");
                System.out.print(Message.choice);
                int choice1 = Integer.parseInt(Console.scanner.nextLine());
                if (choice1 == 2){
                    account.setAccountStatus(ConstStatus.AccountStt.BLOCK);
                } else {
                    account.setAccountStatus(ConstStatus.AccountStt.ACTIVE);
                }
                if (accountRepository.add(account) != null) {
                    WarningMess.createdSuccess();
                    return  account;
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
    public Account update() {
        try {
            boolean stop = false;
            do {
                System.out.println("Nhập vào mã tài khoản muốn cập nhật: ");
                String key = Console.scanner.nextLine();
                Account updateStatusAccount = accountRepository.findId(Account.class, key);
                if (updateStatusAccount != null) {
                    Header.accounts();
                    System.out.printf(TableForm.accounts.column,
                            updateStatusAccount.getAccId(),
                            updateStatusAccount.getUserName(),
                            updateStatusAccount.getPassword(),
                            updateStatusAccount.getPermission().equals(PermissionType.USER) ? "User":"Admin",
                            updateStatusAccount.getEmployeeId(),
                            updateStatusAccount.getAccountStatus().equals(ConstStatus.AccountStt.ACTIVE)?"Hoạt động":"Không hoạt động");
                    System.out.println("Cập nhật trạng thái sản phẩm: ");
                    System.out.println("1. Hoạt động");
                    System.out.println("2. Không hoạt động");
                    System.out.print(Message.choice);
                    int set = Integer.parseInt(Console.scanner.nextLine());
                    if (set == 1)
                        updateStatusAccount.setAccountStatus(true);
                    else if (set == 2)
                        updateStatusAccount.setAccountStatus(false);
                    else
                        WarningMess.choiceFailure();
                    if (accountRepository.edit(updateStatusAccount) != null) {
                        WarningMess.updateSuccess();
                        return updateStatusAccount;
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

    @Override
    public List<Account> search(String any) {
        try {
            List<Account> accountList = accRepository.findByIndex(any);
            if (!accountList.isEmpty()){
                System.out.printf("Tổng: %s tải khoản \n",accountList.size());
                Header.accounts();
                 accountList.stream().forEach(a -> System.out.printf(TableForm.accounts.column,
                        a.getAccId(),
                        a.getUserName(),
                        a.getPassword(),
                        a.getPermission().equals(PermissionType.USER) ? "User":"Admin",
                        a.getEmployeeId(),
                        a.getAccountStatus().equals(ConstStatus.AccountStt.ACTIVE)?"Hoạt động":"Không hoạt động"));
                System.out.println("1. Cập nhật trạng thái | 2. <Thoát>");
                System.out.print(Message.choice);
                int choice = Integer.parseInt(Console.scanner.nextLine());
                switch (choice) {
                    case 1:
                        update();
                        break;
                    case 2:
                        break;
                    default:
                        WarningMess.choiceFailure();
                }
            }
            return accountList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
