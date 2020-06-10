package com.example.monthly_household_account_book.main_adapter;

public class Items {

    private String kind, category, money, year, month, day, id, date;
    private int fixed_money = 2000000;

    public void setDate(String date) {
        this.date = date;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        if (kind.matches("수입"))
            this.kind = "수입";
        else if (kind.matches("지출"))
            this.kind = "지출";

    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getFixed_money() {
        return fixed_money;
    }

    public void setFixed_money(int fixed_money) {
        this.fixed_money = fixed_money;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
