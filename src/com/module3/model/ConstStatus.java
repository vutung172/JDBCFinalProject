package com.module3.model;

public interface ConstStatus {
    interface ProductStt {
        boolean ACTIVE = true;
        boolean INACTION = false;
    }
    interface EmpStt {
        int ACTIVE = 0;
        int SLEEP = 1;
        int QUIT = 2;
    }
    interface AccountStt {
        boolean ACTIVE = true;
        boolean BLOCK = false;
    }
    interface BillStt {
        int CREATE = 0;
        int CANCEL = 1;
        int APPROVAL = 2;
    }
}
