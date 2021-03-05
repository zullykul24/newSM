package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Account  {
    private int account_id;
    private int account_group;
    private String userName;
    private String password;

    public Account(int account_id, int account_group, String userName, String password) {
        this.account_id = account_id;
        this.account_group = account_group;
        this.userName = userName;
        this.password = password;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getAccount_group() {
        return account_group;
    }

    public void setAccount_group(int account_group) {
        this.account_group = account_group;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
