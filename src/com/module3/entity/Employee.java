package com.module3.entity;

import com.module3.model.ConstStatus;
import com.module3.util.Annotation.Column;
import com.module3.util.Annotation.Id;
import com.module3.util.Annotation.Table;

import java.util.Date;

@Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "Emp_Id")
    private String employeeId;
    @Column(name = "Emp_Name")
    private String employeeName;
    @Column(name = "Birth_Of_Date")
    private Date dateOfBirth;
    @Column(name = "Email")
    private String email;
    @Column(name = "Phone")
    private String phone;
    @Column(name = "Address")
    private String address;
    @Column(name = "Emp_Status")
    private Byte employeeStatus;

    public Employee() {
    }

    public Employee(String employeeId, String employeeName, Date dateOfBirth, String email, String phone, String address, Byte employeeStatus) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.employeeStatus = employeeStatus;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Byte getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(Byte employeeStatus) {
        this.employeeStatus = employeeStatus;
    }
}
