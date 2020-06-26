package com.example.monthly_household_account_book;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateRun {
    private String year;
    private String month;
    private String day;
    private String time;
    private Date nowDate;
    private SimpleDateFormat Dateformat;


    public void runningDate(String kindTime){
        nowDate = new Date(System.currentTimeMillis());

        Dateformat = new SimpleDateFormat("yyyy");
        this.year = Dateformat.format(nowDate);

        Dateformat = new SimpleDateFormat("MM");
        this.month = Dateformat.format(nowDate);

        Dateformat = new SimpleDateFormat("dd");
        this.day = Dateformat.format(nowDate);
        if(kindTime.equals("user"))
            Dateformat = new SimpleDateFormat("HHmm");
        else
            Dateformat = new SimpleDateFormat("HHmmss");

        this.time = Dateformat.format(nowDate);
    }

    public String getId() {
        //tb_data id
        runningDate("");
        return year + month + day + time;
    }

    public String getTimeforUser(){
        runningDate("user");
        return time;
    }
    public String getMonthDay(){
        runningDate("");
        return month + day;
    }

    public String getNowYear() {
        runningDate("");
        return year;
    }

    public String getNowYear_Month() {
        runningDate("");
        return year+month;
    }
    public String getMonth(){
        runningDate("");
        return month;
    }

}
