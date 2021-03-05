package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VoucherItem  implements Serializable {
    private int voucherId;
    private String voucherCode;
    private int discount;
    private String title;
    private long startDate;
    private long endDate;
    private ArrayList<String> conditions ;

    public ArrayList<String> getConditions() {
        return conditions;
    }

    public void setConditions(ArrayList<String> conditions) {
        this.conditions = conditions;
    }

    public VoucherItem(int voucherId, String voucherCode, int discount, String title, long startDate, long endDate, ArrayList<String> conditions) {
        this.voucherId = voucherId;
        this.voucherCode = voucherCode;
        this.discount = discount;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.conditions = conditions;
    }
    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }
}
