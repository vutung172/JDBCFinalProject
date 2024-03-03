package com.module3.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface DateTimeFormat {
    default String dateFormater(String date){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date parseDate = sdf.parse(date);
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
        return date;
    };
}
