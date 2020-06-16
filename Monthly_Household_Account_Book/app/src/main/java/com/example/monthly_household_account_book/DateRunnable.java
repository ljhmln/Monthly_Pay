package com.example.monthly_household_account_book;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateRunnable implements Runnable {
    private String year;
    private String month;
    private String day;
    private String time;
    private Date nowDate;
    private SimpleDateFormat Dateformat;

    public void run() {
        while (true) {
            nowDate = new Date(System.currentTimeMillis());

            Dateformat = new SimpleDateFormat("yyyy");
            this.year = Dateformat.format(nowDate);

            Dateformat = new SimpleDateFormat("MM");
            this.month = Dateformat.format(nowDate);

            Dateformat = new SimpleDateFormat("dd");
            this.day = Dateformat.format(nowDate);

            Dateformat = new SimpleDateFormat("HHmmss");
            this.time = Dateformat.format(nowDate);

        }

    }

    public String getNowDate_Time() {
        //tb_data id
        return year + month + day + time;
    }

    public String getNowYear() {
        return year;
    }

    public String getNowYear_Month() {
        nowDate = new Date(System.currentTimeMillis());

        Dateformat = new SimpleDateFormat("yyyy");
        this.year = Dateformat.format(nowDate);

        Dateformat = new SimpleDateFormat("MM");
        this.month = Dateformat.format(nowDate);

        return year+month;
    }
}
