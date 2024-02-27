package com.module3.model;

public interface WarningMess {
    interface choice {
        String failure = "Lựa chọn không phù hợp, mời chọn lại!";
    }
    interface selection {
        String continuous = "Bạn có muốn tiếp tục không (Y/N): ";
    }
}
