package com.example.maryshop;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.maryshop.model.Order;

import java.security.InvalidParameterException;

public class MilkActivity extends AppCompatActivity implements  LoaderManager.LoaderCallbacks<Cursor>
        {
    ListView list_view_milk;
    private static final int LOADER_ID = 225;
    private static final String TAG = "myLogs";
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_milk);
        list_view_milk = (ListView)findViewById(R.id.list_view_milk);

        // Адаптер. Пока данных нет используем null
        productAdapter = new ProductAdapter(this, null);
        list_view_milk.setAdapter(productAdapter);
        LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this);
        list_view_milk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                Object o = list_view_milk.getItemAtPosition(position);
                Product product = (Product) o;
                Log.d(TAG, " "  +product+ " position ");


            }
        });

    }


    //загружаем курсор
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        // Зададим нужные колонки
        Log.d(TAG, "SearchFragment-onCreateLoader...");
        String[] projection = {
                AppProvider.KEY_ID,
                AppProvider.KEY_TITLE,
                AppProvider.KEY_PRICE,
                AppProvider.KEY_PHOTO,
                AppProvider.KEY_BALANCE,
        };
        if(id == LOADER_ID)
            // Загрузчик запускает запрос AppProvider в фоновом потоке
            return new CursorLoader(this,
                    AppProvider.CONTENT_URI,// URI контент-провайдера для запроса
                    projection,// колонки, которые попадут в результирующий курсор
                    null, // без условия WHERE
                    null,// без аргументов
                    AppProvider.KEY_TITLE);// сортировка по наименованию продукта, может быть по умолчанию null

        else
            throw new InvalidParameterException("Invalid loader id");

    }
    //получение данных и вывод их на экран
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        // Обновляем CursorAdapter новым курсором, которые содержит обновленные данные
        productAdapter.swapCursor(data);
    }
    //сброс загрузчика
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        // Освобождаем ресурсы
        productAdapter.swapCursor(null);

    }



}