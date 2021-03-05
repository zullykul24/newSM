package com.example.myapplication;

public class HistoryItem {
    private int paymentId;
    private int accountId;
    private int tableId;
    private int orderId;
    private int discountId;
    private double amount;
    private long date;
    private String status;
    private String paymentTime;


    public HistoryItem(int paymentId, int accountId, int tableId,  int orderId, int discountId, double amount, long date, String status) {
        this.paymentId = paymentId;
        this.accountId = accountId;
        this.orderId = orderId;
        this.discountId = discountId;
        this.amount = amount;
        this.date = date;
        this.status = status;
        this.tableId = tableId;
    }


    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }
}
