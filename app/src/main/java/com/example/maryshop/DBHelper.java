package com.example.maryshop;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "UserDB";
    public  static final String TABLE_USERS = "Users";
    public  static final String KEY_ID = "_id";
    public  static final String KEY_NAME = "_name";
    public  static final String KEY_PHONE = "_phone";
    public  static final String KEY_EMAIL = "_email";
    public  static final String KEY_ADDRESS = "_address";
    public  static final String KEY_PASS = "_pass";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_USERS+ "("+ KEY_ID+ " integer primary key AUTOINCREMENT,"
                + KEY_NAME +" text," +KEY_PHONE+ " text,"
        +KEY_EMAIL + " text,"  + KEY_ADDRESS + " text,"  + KEY_PASS + " text"   +")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " +TABLE_USERS);
        onCreate(db);
    }
//вставка значений в таблицу
    public String InsertData(String Name, String Phone, String Email,String Address,String Pass) {
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_NAME,Name);
            contentValues.put(KEY_PHONE,Phone);
            contentValues.put(KEY_EMAIL,Email);
            contentValues.put(KEY_ADDRESS,Address);
            contentValues.put(KEY_PASS,Pass);
            database.insert(TABLE_USERS,null,contentValues);
            return "Пользователь зарегистрирован успешно";
        } catch (Exception ex) {
            return ex.getMessage().toString();
        }
    }


//курсор для поиска по всей таблице
    public Cursor getAllData() {
        try {
            String selectQuery = "SELECT * FROM " + TABLE_USERS;
            SQLiteDatabase database = this.getWritableDatabase();
            Cursor cursor = database.rawQuery(selectQuery, null);
            return cursor;
        } catch (Exception ex) {
            return null;
        }
    }

    }


