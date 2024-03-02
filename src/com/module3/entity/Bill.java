package com.module3.entity;

import com.module3.util.Annotation.*;

import java.util.Date;

@Table(name = "bills")
public class Bill {
    @Id
    @Index(name = "Bill_id")
    @Column(name = "Bill_id")
    private Long billId;
    @Index(name = "Bill_Code")
    @Column(name = "Bill_Code")
    private String billCode;
    @Column(name = "Bill_Type")
    private Boolean billType;
    @Column(name = "Emp_id_created")
    private String employeeIdCreated;
    @Column(name = "Created")
    private Date created;
    @Column(name = "Emp_id_auth")
    private String employeeIdAuth;
    @Column(name = "Auth_date")
    private Date authDate;
    @Mark
    @Column(name = "Bill_Status")
    private Integer billStatus;

    public Bill() {
    }

    public Bill(Long billId, String billCode, Boolean billType, String employeeIdCreated, Date created, String employeeIdAuth, Date authDate, Integer billStatus) {
        this.billId = billId;
        this.billCode = billCode;
        this.billType = billType;
        this.employeeIdCreated = employeeIdCreated;
        this.created = created;
        this.employeeIdAuth = employeeIdAuth;
        this.authDate = authDate;
        this.billStatus = billStatus;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public Boolean getBillType() {
        return billType;
    }

    public void setBillType(Boolean billType) {
        this.billType = billType;
    }

    public String getEmployeeIdCreated() {
        return employeeIdCreated;
    }

    public void setEmployeeIdCreated(String employeeIdCreated) {
        this.employeeIdCreated = employeeIdCreated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getEmployeeIdAuth() {
        return employeeIdAuth;
    }

    public void setEmployeeIdAuth(String employeeIdAuth) {
        this.employeeIdAuth = employeeIdAuth;
    }

    public Date getAuthDate() {
        return authDate;
    }

    public void setAuthDate(Date authDate) {
        this.authDate = authDate;
    }

    public Integer getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(Integer billStatus) {
        this.billStatus = billStatus;
    }
}
