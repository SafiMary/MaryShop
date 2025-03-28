package com.example.maryshop;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maryshop.model.Order;

import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ProductAdapter  extends CursorAdapter {

    private static final String TAG = "myLogs";
    public  ProductAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.search_catalog, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        int position = cursor.getPosition();
        // считываем атрибуты курсором, выбирая нужные колонки таблицы
        String title = cursor.getString(cursor.getColumnIndexOrThrow(AppProvider.KEY_TITLE));
        viewHolder.title_view.setText(title);
        String price = cursor.getString(cursor.getColumnIndexOrThrow(AppProvider.KEY_PRICE));
        viewHolder.price_view.setText(price);
        byte[] blob = cursor.getBlob(cursor.getColumnIndexOrThrow(AppProvider.KEY_PHOTO));
        viewHolder.photo_view.setImageBitmap(ImageConvert.getImage(blob));

        viewHolder.btn_add_basket.setTag(position);
        //прослушали кнопку добавить в корзину
        viewHolder.btn_add_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view != null) {
                    Object obj = view.getTag();
                    if (obj != null && obj instanceof Integer) {
                        //получили id нажатого item
                        int item_id = ((Integer) obj).intValue();
                        //в наш hashmap  сложили id товара- key
                        //сложили count - количество товара выбранное пользователем
                        Order.items_id.add(item_id);
                        Toast.makeText(context, "id = " + ((Integer) obj).intValue(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        Object obj = cursor.getInt(cursor.getColumnIndexOrThrow(AppProvider.KEY_ID));
        viewHolder.btn_add_basket.setTag(obj);
    }

    static class ViewHolder {
        TextView title_view,price_view;
        ImageView photo_view;
        Button btn_add_basket;
        public ViewHolder(View view) {
            title_view = (TextView)view.findViewById(R.id.title_view);
            price_view = (TextView)view.findViewById(R.id.price_view);
            photo_view = (ImageView)view.findViewById(R.id.photo_view);
            btn_add_basket = (Button) view.findViewById(R.id.btn_add_basket);
        }
    }




    }

