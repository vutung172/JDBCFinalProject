package com.module3.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface DateTimeFormat {
    String dateFormat = "dd-MM-yyyy";
    String dateDBFormat = "yyyy-MM-dd";
    default Date checkerDateFormater(String date){
        Date parseDate = null;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            parseDate = sdf.parse(date);
        }catch (Exception e){
            WarningMess.dateFormatFailure();
        }
        return parseDate;
    };
    default String dateTransfer(Date date){
        String parseDate;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            parseDate = sdf.format(date);
        }catch (Exception e){
            WarningMess.dateFormatFailure();
            return "fail";
        }
        return parseDate;
    }
    default String dateTransferToDB(Date date){
        String parseDate = null;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(dateDBFormat);
            parseDate = sdf.format(date);
        }catch (Exception e){
            WarningMess.dateFormatFailure();
        }
        return parseDate;
    }
}
