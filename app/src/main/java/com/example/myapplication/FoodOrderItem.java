package com.example.myapplication;

import android.view.Menu;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.DuplicateFormatFlagsException;

public class FoodOrderItem implements Serializable {
    private MenuFoodItem item;
    private int number;
    private String note;

    public FoodOrderItem(int dish_id, String dish_name, int group_id, Double price, byte[] image) {
        item = new MenuFoodItem(dish_id, dish_name, group_id, price, image);
        this.number = 1;
    }
//    public FoodOrderItem(int dish_id, String dish_name,  Double price, int HinhAnh) {
//        item = new MenuFoodItem(dish_name, price, HinhAnh);
//        this.number = 1;
//    }

    public FoodOrderItem(int dish_id) {
        item = new MenuFoodItem(dish_id);
        this.number = 1;
    }

    public int getDish_id() { return item.getDish_id();}

    public void setDish_id(int dish_id) {
        item.setDish_id(dish_id);
    }

    public String getDish_name() {
        return item.getDish_name();
    }

    public void setDish_name(String dish_name) {
        item.setDish_name(dish_name);
    }

    public int getGroup_id() {
        return item.getGroup_id();
    }

    public void setGroup_id(int group_id) {
        item.setGroup_id(group_id);
    }

    public Double getPrice() {
        return item.getPrice();
    }

    public void setPrice(Double price) {
        item.setPrice(price);
    }

    public byte[] getImage() {
        return item.getImage();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setImage(byte[] image) {
        item.setImage(image);
    }

//    public int getHinhAnh() {
//        return item.getHinhAnh();
//    }
//
//    public void setHinhAnh(int hinhAnh) {
//        item.setHinhAnh(hinhAnh);
//    }

    public String getNote() {return this.note;}
    public void setNote(String note) {this.note = note;}
}
