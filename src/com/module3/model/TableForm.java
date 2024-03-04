package com.module3.model;

import java.util.Objects;

public interface TableForm {
    interface accounts{
        String header = "";
        String column = " %12s | %31s | %31s | %10s | %13s | %15s \n";
    }
    interface billDetails{
        String header = "";
        String column = " %10s | %20s | %12s | %10s | %10s \n";
    }
    interface bills{
        String header = "";
        String column = " %10s | %12s | %15s | %20s | %10s | %20s | %10s | %15s \n";
    }
    interface employees{
        String header = "";
        String column = " %14s | %20s | %20s | %30s | %30s | %30s | %15s \n";
    }
    interface products{
        String header = "";
        String column = " %15s | %30s | %30s | %15s | %10s | %12s | %15s \n";
    }

}
