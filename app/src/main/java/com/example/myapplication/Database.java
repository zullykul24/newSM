package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class Database  extends SQLiteOpenHelper {
    long millis=System.currentTimeMillis();
    java.sql.Date date=new java.sql.Date(millis);
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertDish(String name, Double price, byte[] image) {
        SQLiteDatabase database = getWritableDatabase();
        // dishId, dishname, groupID, price, image;
        String sql = "INSERT INTO dish values (null,?,?,?,?,"+date+")";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, name);
        // chưa biết nên cho ntn nên tạm thời cứ để giá trị mặc định là 1.
        statement.bindLong(2, 1);
        statement.bindDouble(3,price);
        statement.bindBlob(4, image);
        statement.executeInsert();
    }


    public Cursor getData(String sql){
        SQLiteDatabase database  =  getReadableDatabase();
        return database.rawQuery(sql, null);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
