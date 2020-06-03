package com.example.monthly_household_account_book.main_adapter;

public class Items {

    private String kind,category,money;
    private String won = "￦";


    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        if(kind.matches("수입"))
            this.kind = "수입";
        else if(kind.matches("지출"))
            this.kind = "지출";

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
