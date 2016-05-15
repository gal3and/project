package com.example.chohee.myapplication;

import java.util.Date;

/**
 * Created by yoon on 2016-05-12.
 */
public class AccountItemDto {
    private String item_name;
    private int item_price;
    private String buy_date;

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getItem_price() {
        return item_price;
    }

    public void setItem_price(int item_price) {
        this.item_price = item_price;
    }

    public String getBuy_date() {
        return buy_date;
    }

    public void setBuy_date(String buy_date) {
        this.buy_date = buy_date;
    }
}
