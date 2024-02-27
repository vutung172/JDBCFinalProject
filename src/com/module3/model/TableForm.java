package com.module3.model;

import java.util.Objects;

public interface TableForm {
    interface accounts{
        String header = "";
        String column = " %s | %s | %s | %s | %s | %s \n";
    }
    interface billDetails{
        String header = "";
        String column = " %s | %s | %s | %s | %s \n";
    }
    interface bills{
        String header = "";
        String column = " %s | %s | %s | %s | %s | %s | %s | %s \n";
    }
    interface employees{
        String header = "";
        String column = " %s | %s | %s | %s | %s | %s | %s \n";
    }
    interface products{
        String header = "";
        String column = " %s | %s | %s | %s | %s | %s | %s \n";
    }

}
