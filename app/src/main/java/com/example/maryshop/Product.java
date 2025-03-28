package com.example.maryshop;

import java.io.Serializable;

public class Product implements Serializable {


    int id;
    String title;
    int price;
    byte[] photo;
    int amount;
//    boolean basket;

    public Product(int _id,String _title, int _price, byte[] _photo, int _amount) {
        id = _id;
        title = _title;
        price = _price;
        photo = _photo;
        amount = _amount;
    }
    public Product(int _id,String _title, int _price, byte[] _photo) {
        id = _id;
        title = _title;
        price = _price;
        photo = _photo;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    //public byte[] getPhoto() {return photo;}
    public byte[] getPhoto() {return photo;}
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
