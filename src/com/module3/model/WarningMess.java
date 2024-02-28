package com.module3.model;

import com.module3.util.Font.PrintForm;
import com.module3.util.Storage.UserStorage;

public class WarningMess {
    public static void welcome(){
        System.out.printf("Xin chào %s | Quyền hạn: %s \n", UserStorage.currentUserName, UserStorage.currentPermission);
    }
    public static void listEmpty(){
        PrintForm.attention("Danh sách hiện chưa có dữ liệu nào");
    }
    public static void objectNotExist(){
        PrintForm.warning("Đối tượng cần tìm không tồn tại");
    }
    public static void choiceFailure() {
        PrintForm.warning("Lựa chọn không phù hợp mời chọn lại !");
    }
    public static void createdSuccess(){
        PrintForm.success("Tạo thành công !!!");
    }
    public static void createdFailure(){
        PrintForm.warning("Tạo thất bại");
    }
    public static void updateSuccess(){
        PrintForm.warning("Cập nhật thành công");
    }
    public static void updateFailure(){
        PrintForm.warning("Cập nhật thất bại");
    }
}
