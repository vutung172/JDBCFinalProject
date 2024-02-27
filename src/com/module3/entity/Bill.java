package com.module3.entity;

import com.module3.util.Annotation.Column;
import com.module3.util.Annotation.Id;
import com.module3.util.Annotation.Table;

import java.util.Date;

@Table(name = "bills")
public class Bill {
    @Id
    @Column(name = "Bill_id")
    private Long billId;
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
    @Column(name = "Bill_Status")
    private Byte billStatus;

    public Bill() {
    }

    public Bill(Long billId, String billCode, Boolean billType, String employeeIdCreated, Date created, String employeeIdAuth, Date authDate, Byte billStatus) {
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

    public Byte getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(Byte billStatus) {
        this.billStatus = billStatus;
    }
}
