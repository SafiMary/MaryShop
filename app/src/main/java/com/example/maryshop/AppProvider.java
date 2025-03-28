package com.example.maryshop;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.res.Resources;
import android.database.Cursor;
import android.content.ContentUris;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class AppProvider extends ContentProvider {
    static final String PROVIDER_NAME = "com.example.provider";
    static final String URL = "content://" + PROVIDER_NAME + "/products";
    public static final Uri CONTENT_URI = Uri.parse(URL);
    static final UriMatcher uriMatcher;
    private SQLiteDatabase database;
    DBProvider dbProvider;
    public static final int PRODUCTS = 1;
    public static final int PRODUCTS_ID = 2;
    private static HashMap<String, String> MyMap;
    //Для построения путей uri для запросов к источнику данных определен объект sUriMatcher
    //С помощью метода addURI в объект UriMatcher добавляется определенный путь uri, используемый для отправки запроса
    //для обращения ко всей таблице, либо для обращения к отдельному объекту

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "products", PRODUCTS);
        uriMatcher.addURI(PROVIDER_NAME, "products/#", PRODUCTS_ID);
    }

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "storeDB";
    public  static final String TABLE_PRODUCTS = "products";
    public  static final String KEY_ID = "_id";
    public  static final String KEY_TITLE = "_title";
    public  static final String KEY_PRICE = "_price";
    public  static final String KEY_PHOTO = "_photo";
    public  static final String KEY_BALANCE= "_balance";
    public  static final String KEY_CATEGORY_ID = "_category_id";

    public  static final String TABLE_CATEGORIES = "categories";
    public  static final String KEY_ID_TABLE_CATEGORIES = "_id";
    public  static final String KEY_TITLE_TABLE_CATEGORIES = "_title";



    // class that creates and manages the provider's database
    public static class DBProvider extends SQLiteOpenHelper {
        Context mContext;
        public DBProvider(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //создаем 2 таблицы
            db.execSQL("create table "+ TABLE_CATEGORIES+ "("+ KEY_ID_TABLE_CATEGORIES+ " integer primary key AUTOINCREMENT,"
                    + KEY_TITLE_TABLE_CATEGORIES+")");
            // Добавляем записи в таблицу
            ContentValues contentValues = new ContentValues();
            // Получим массив строк из ресурсов
            Resources res = mContext.getResources();
            String[] prod = res.getStringArray(R.array.prod_array);
// проходим через массив и вставляем записи в таблицу
            for(String item : prod){
                contentValues.put(KEY_TITLE_TABLE_CATEGORIES,item);
                db.insert(TABLE_CATEGORIES,null,contentValues);
            }
            db.execSQL("create table "+ TABLE_PRODUCTS+ "("+ KEY_ID+ " integer primary key AUTOINCREMENT,"
                    + KEY_TITLE +" text," +KEY_PRICE+ " integer,"
                    +KEY_PHOTO + " BLOB NOT NULL,"
                    + KEY_BALANCE + " integer," + KEY_CATEGORY_ID+ " integer,"
                    + " FOREIGN KEY(" + KEY_CATEGORY_ID +")" +
                    " REFERENCES "+TABLE_CATEGORIES+"("+KEY_ID_TABLE_CATEGORIES+") "   +");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            Log.w(DBProvider.class.getName(),
                    "Upgrading database from version " + oldVersion + " to "
                            + newVersion + ". Old data will be destroyed");
            db.execSQL("drop table if exists " +TABLE_PRODUCTS);
            db.execSQL("drop table if exists " +TABLE_CATEGORIES);

            onCreate(db);
        }

        public byte[] retreiveImageFromDB() {
            SQLiteDatabase database = this.getWritableDatabase();
            Cursor cur = database.query(false, TABLE_PRODUCTS, new String[]{KEY_ID, KEY_PHOTO},
                    null, null, null, null,
                    KEY_ID + " DESC", "1");
            if (cur.moveToFirst()) {
                byte[] blob = cur.getBlob(cur.getColumnIndexOrThrow(KEY_PHOTO));
                cur.close();
                return blob;
            }
            cur.close();
            return null;
        }

        public Product getProductsByID(int id_product){
            SQLiteDatabase db = this.getWritableDatabase();
            Product product = null;
            String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS+ " WHERE " +KEY_ID+ "="+ id_product;
            Cursor cursor = db.rawQuery(selectQuery,null);
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_PRICE));
                byte[] blob = cursor.getBlob(cursor.getColumnIndexOrThrow(KEY_PHOTO));
                //int blob = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_PHOTO));
                product = new Product(id, title, price, blob);
            }
            cursor.close();
            return  product;
        }

        public Cursor getAllData() {
            try {
                String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS;
                SQLiteDatabase database = this.getWritableDatabase();
                Cursor cursor = database.rawQuery(selectQuery, null);
                return cursor;
            } catch (Exception ex) {
                return null;
            }
        }
        //вставка значений в таблицу продуктов
        public String InsertTableProducts(String Title, String Price, byte[] Photo,Integer Balances,Integer Category_id) {
            try {
                SQLiteDatabase database = this.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(KEY_TITLE,Title);
                contentValues.put(KEY_PRICE,Price);
                contentValues.put(KEY_PHOTO,Photo);
                contentValues.put(KEY_BALANCE,Balances);
                contentValues.put(KEY_CATEGORY_ID,Category_id);
                database.insert(TABLE_PRODUCTS,null,contentValues);
                return "Продукт добавлен успешно";
            } catch (Exception ex) {
                return ex.getMessage().toString();
            }
        }
   }
//начальная инициализация провайдера
    @Override
    public boolean onCreate() {
        // TODO Auto-generated method stub
        Context context = getContext();
        dbProvider = new DBProvider(context);
        // permissions to be writable
        database = dbProvider.getWritableDatabase();
        if(database == null)
            return false;
        else
            return true;
    }
    //получение данных
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        // the TABLE_NAME to query on
        queryBuilder.setTables(TABLE_PRODUCTS);

        switch (uriMatcher.match(uri)) {
            // maps all database column names
            case PRODUCTS:
                queryBuilder.setProjectionMap(MyMap);
                break;
            case PRODUCTS_ID:
                queryBuilder.appendWhere( KEY_ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder == ""){
            // No sorting-> sort on names by default
            sortOrder = KEY_TITLE;
        }
        Cursor cursor = queryBuilder.query(database, projection, selection,
                selectionArgs, null, null, sortOrder);
        /**
         * register to watch a content URI for changes
         */
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
        }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            // Get all friend-birthday records
            case PRODUCTS:
                return "vnd.android.cursor.dir/vnd.example.products";
            // Get a particular friend
            case PRODUCTS_ID:
                return "vnd.android.cursor.item/vnd.example.products";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
    //добавление данных

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long row = database.insert(TABLE_PRODUCTS, "", values);

        // If record is added successfully
        if(row > 0) {
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Fail to add a new record into " + uri);
    }
//удаление данных
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)){
            case PRODUCTS:
                // delete all the records of the table
                count = database.delete(TABLE_PRODUCTS, selection, selectionArgs);
                break;
            case PRODUCTS_ID:
                String id = uri.getLastPathSegment(); //gets the id
                count = database.delete( TABLE_PRODUCTS, KEY_ID +  " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
//обновление данных
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)){
            case PRODUCTS:
                count = database.update(TABLE_PRODUCTS, values, selection, selectionArgs);
                break;
            case PRODUCTS_ID:
                count = database.update(TABLE_PRODUCTS, values, KEY_ID +
                        " = " + uri.getLastPathSegment() +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
