package com.example.myapplication;

public class PaymentTableItem {
    private int tableName;

    public PaymentTableItem(int tableName) {
        this.tableName = tableName;
    }

    public int getTableName() {
        return tableName;
    }

    public void setTableName(int tableName) {
        this.tableName = tableName;
    }
}
