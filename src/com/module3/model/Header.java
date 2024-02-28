package com.module3.model;

public class Header {
    public static void accounts(){
        System.out.printf(TableForm.accounts.column,
                "Mã tài khoản",
                "Tên tài khoản",
                "Mật khẩu",
                "Quyền hạn",
                "Mã nhân viên",
                "Trạng thái");
    }
    public static void billDetails(){
        System.out.printf(TableForm.billDetails.column,
                "Mã phiếu chi tiết",
                "Mã phiếu",
                "Mã sản phẩm",
                "Số lượng",
                "Giá");
    }
    public static void bills(){
        System.out.printf(TableForm.bills.column,
                "Mã phiếu",
                "Mã code",
                "Loại phiếu",
                "Mã nhân viên tạo",
                "Ngày tạo",
                "Mã nhân viên duyệt",
                "Ngày duyệt",
                "Trạng thái");
    }
    public static void employees(){
        System.out.printf(TableForm.employees.column,
                "Mã nhân viên",
                "Tên nhân viên",
                "Ngày-tháng-năm sinh",
                "Địa chỉ e-mail",
                "Số điện thoại",
                "Địa chỉ",
                "Trạng thái");
    }
    public static void products(){
        System.out.printf(TableForm.products.column,
                "Mã sản phẩm",
                "Tên sản phẩm",
                "Nhà sản xuất",
                "Ngày tạo",
                "Lô",
                "Số lượng",
                "Trạng thái");
    }

}
