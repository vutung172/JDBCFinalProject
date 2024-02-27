package com.module3.entity;

import com.module3.model.ConstStatus;
import com.module3.model.PermissionType;
import com.module3.util.Annotation.Column;
import com.module3.util.Annotation.Id;
import com.module3.util.Annotation.Table;

@Table(name = "accounts")
public class Account {
    @Column(name = "Acc_id")
    private Integer accId;
    @Id
    @Column(name = "User_name")
    private String userName;
    @Id
    @Column(name = "Password")
    private String password;
    @Column(name = "Permission")
    private Boolean permission;
    @Column(name = "Emp_id")
    private String employeeId;
    @Column(name = "Acc_status")
    private Boolean accountStatus;

    public Account() {
    }

    public Account(Integer accId, String userName, String password, Boolean permission, String employeeId, Boolean accountStatus) {
        this.accId = accId;
        this.userName = userName;
        this.password = password;
        this.permission = permission;
        this.employeeId = employeeId;
        this.accountStatus = accountStatus;
    }

    public Integer getAccId() {
        return accId;
    }

    public void setAccId(Integer accId) {
        this.accId = accId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Boolean getPermission() {
        return permission;
    }

    public void setPermission(Boolean permission) {
        this.permission = permission;
    }

    public Boolean getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Boolean accountStatus) {
        this.accountStatus = accountStatus;
    }
}
