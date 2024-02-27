package com.module3.model;

import com.module3.util.Storage.UserStorage;

public class Mess {
    public static void welcome(){
        System.out.printf("Xin chào %s | Quyền hạn : %s | Trạng thái tài khoản : %s\n",
                UserStorage.currentUserName,
                UserStorage.currentPermission == PermissionType.USER ? "User": "Admin",
                UserStorage.accStatus == ConstStatus.AccountStt.ACTIVE ? "Active" : "Block");
    }
    public static void choice(){
        System.out.println("Mời bạn lựa chọn: ");
    }
}
