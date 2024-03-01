package com.module3.service.Impl;

import com.module3.entity.Employee;
import com.module3.entity.Product;
import com.module3.model.*;
import com.module3.repository.Impl.RepositoryImpl;
import com.module3.service.GeneralService;
import com.module3.util.Console;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class EmployeeServiceImpl implements GeneralService<Employee> {
    private RepositoryImpl<Employee> employeeRepository;

    public EmployeeServiceImpl() {
        this.employeeRepository = new RepositoryImpl<>();
    }

    @Override
    public List<Employee> listAll() {
        List<Employee> employeeList = employeeRepository.findAll(Employee.class);
        if (!employeeList.isEmpty()) {
            int maxIndex = employeeList.size() / 10;
            int choice;
            int index = 1;
            try {
                do {
                    List<Employee> employeeListPagination = employeeRepository.findAllByPagination(Employee.class, index);
                    if (!employeeListPagination.isEmpty()) {
                        System.out.printf("Tổng %s nhân viên \n",employeeList.size());
                        Header.employees();
                        employeeListPagination.stream().forEach(e -> System.out.printf(TableForm.employees.column,
                                e.getEmployeeId(),
                                e.getEmployeeName(),
                                e.getDateOfBirth(),
                                e.getEmail(),
                                e.getPhone(),
                                e.getAddress(),
                                e.getEmployeeStatus().equals(ConstStatus.EmpStt.ACTIVE) ? "Hoạt động" : e.getEmployeeStatus().equals(ConstStatus.EmpStt.SLEEP) ? "Nghỉ chế độ" : "Nghỉ việc"));
                        System.out.println("1.<Trang trước> | 2.<Trang sau> | 3.<Thoát>");
                        System.out.println(Message.choice);
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
                                    if (employeeList.size() % 10 == 0) {
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
        return employeeList;
    }

    @Override
    public Employee create() {
        try {
            boolean stop;
            do {
                Employee employee = new Employee();
                System.out.println("Mời nhập mã nhân: ");
                employee.setEmployeeId(Console.scanner.nextLine());
                System.out.println("Mời bạn nhập tên nhân viên: ");
                employee.setEmployeeName(Console.scanner.nextLine());
                System.out.println("Ngày tháng năm sinh (dd-mm-yyy): ");
                SimpleDateFormat simpleDateFormats = new SimpleDateFormat("dd-MM-yyyy");
                employee.setDateOfBirth(simpleDateFormats.parse(Console.scanner.nextLine()));
                System.out.println("Mời nhập địa chỉ e-mail: ");
                employee.setEmail(Console.scanner.nextLine());
                System.out.println("Mời nhập số điện thoại: ");
                employee.setPhone(Console.scanner.nextLine());
                System.out.println("Mời nhập địa chỉ: ");
                employee.setAddress(Console.scanner.nextLine());
                System.out.println("Mời nhập trạng thái nhân viên: ");
                System.out.println("1.Hoạt động");
                System.out.println("2. Nghỉ chế độ");
                System.out.println("3. Nghỉ việc");
                System.out.print(Message.choice);
                int status = Integer.parseInt(Console.scanner.nextLine());
                switch (status) {
                    case 1:
                        employee.setEmployeeStatus(ConstStatus.EmpStt.ACTIVE);
                        break;
                    case 2:
                        employee.setEmployeeStatus(ConstStatus.EmpStt.SLEEP);
                        break;
                    case 3:
                        employee.setEmployeeStatus(ConstStatus.EmpStt.QUIT);
                        break;
                    default:
                        WarningMess.choiceFailure();
                }
                if (employeeRepository.add(employee) != null) {
                    WarningMess.createdSuccess();
                    return employee;
                } else {
                    WarningMess.createdFailure();
                }
                System.out.println(Message.continuous);
                String confirm = Console.scanner.nextLine();
                stop = confirm.contains("y");
            } while (stop);
        } catch (NumberFormatException | ParseException nfe) {
            nfe.printStackTrace();
        }
        return null;
    }

    @Override
    public Employee update() {
        try {
            boolean stop = false;
            do {
                System.out.println("Nhập vào mã nhân viên muốn cập nhật thông tin: ");
                String key = Console.scanner.nextLine();
                Employee updateEmployee = employeeRepository.findId(Employee.class, key);
                if (updateEmployee != null) {
                    Header.employees();
                    System.out.printf(TableForm.employees.column,
                            updateEmployee.getEmployeeId(),
                            updateEmployee.getEmployeeName(),
                            updateEmployee.getDateOfBirth(),
                            updateEmployee.getEmail(),
                            updateEmployee.getPhone(),
                            updateEmployee.getAddress(),
                            updateEmployee.getEmployeeStatus().equals(ConstStatus.EmpStt.ACTIVE) ? "Hoạt động" : updateEmployee.getEmployeeStatus().equals(ConstStatus.EmpStt.SLEEP) ? "Nghỉ chế độ" : "Nghỉ việc");

                    System.out.println("Cập nhật tên nhân viên: ");
                    updateEmployee.setEmployeeName(Console.scanner.nextLine());
                    System.out.println("Cập nhật ngày tháng năm sinh (dd-mm-yyy): ");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");
                    updateEmployee.setDateOfBirth(simpleDateFormat.parse(Console.scanner.nextLine()));
                    System.out.println("Cập nhật địa chỉ e-mail: ");
                    updateEmployee.setEmail(Console.scanner.nextLine());
                    System.out.println("Cập nhật số điện thoại: ");
                    updateEmployee.setPhone(Console.scanner.nextLine());
                    System.out.println("Cập nhật địa chỉ: ");
                    updateEmployee.setAddress(Console.scanner.nextLine());
                    if (employeeRepository.edit(updateEmployee) != null) {
                        WarningMess.updateSuccess();
                        return updateEmployee;
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
                System.out.println("Nhập vào mã sản phẩm muốn cập nhật; ");
                String key = Console.scanner.nextLine();
                Employee updateStatusEmployee = employeeRepository.findId(Employee.class, key);
                if (updateStatusEmployee != null) {
                    Header.employees();
                    System.out.printf(TableForm.employees.column,
                            updateStatusEmployee.getEmployeeId(),
                            updateStatusEmployee.getEmployeeName(),
                            updateStatusEmployee.getDateOfBirth(),
                            updateStatusEmployee.getEmail(),
                            updateStatusEmployee.getPhone(),
                            updateStatusEmployee.getAddress(),
                            updateStatusEmployee.getEmployeeStatus().equals(ConstStatus.EmpStt.ACTIVE) ? "Hoạt động" : updateStatusEmployee.getEmployeeStatus().equals(ConstStatus.EmpStt.SLEEP) ? "Nghỉ chế độ" : "Nghỉ việc");
                    System.out.println("Cập nhật trạng thái sản phẩm: ");
                    System.out.println("1. Hoạt động");
                    System.out.println("2. Nghỉ chế độ");
                    System.out.println("3. Nghỉ việc");
                    System.out.print(Message.choice);
                    int set = Integer.parseInt(Console.scanner.nextLine());
                    switch (set) {
                        case 1:
                            updateStatusEmployee.setEmployeeStatus(ConstStatus.EmpStt.ACTIVE);
                            employeeRepository.edit(updateStatusEmployee);
                            break;
                        case 2:
                            updateStatusEmployee.setEmployeeStatus(ConstStatus.EmpStt.SLEEP);
                            employeeRepository.edit(updateStatusEmployee);
                            break;
                        case 3:
                            updateStatusEmployee.setEmployeeStatus(ConstStatus.EmpStt.QUIT);
                            employeeRepository.edit(updateStatusEmployee);
                            break;
                        default:
                    }
                    if (set == 1 || set == 2) {

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
    public List<Employee> search(String any) {
        List<Employee> employeeList = employeeRepository.findByIndexes(Employee.class, any);
        if (!employeeList.isEmpty()) {
            int maxPage = employeeList.size() / 10;
            int choice;
            int page = 1;
            try {
                do {
                    List<Employee> employeeListPagination = employeeRepository.findByIndexesPagination(Employee.class, any, page);
                    if (!employeeListPagination.isEmpty()) {
                        System.out.printf("Tổng: %s nhân viên",employeeList.size());
                        Header.products();
                        employeeListPagination.forEach(e -> System.out.printf(TableForm.employees.column,
                                e.getEmployeeId(),
                                e.getEmployeeName(),
                                e.getDateOfBirth(),
                                e.getEmail(),
                                e.getPhone(),
                                e.getAddress(),
                                e.getEmployeeStatus().equals(ConstStatus.EmpStt.ACTIVE) ? "Hoạt động" : e.getEmployeeStatus().equals(ConstStatus.EmpStt.SLEEP) ? "Nghỉ chế độ" : "Nghỉ việc"));
                        System.out.println("1.<Trang trước> | 2.<Trang sau> | 3.<Thoát>");
                        System.out.println(Message.choice);
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
                                    if (employeeList.size() % 10 == 0) {
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
                        WarningMess.dataNotFound();
                        choice = 3;
                    }
                } while (choice != 3);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        } else {
            WarningMess.listEmpty();
        }
        return employeeList;
    }
}
