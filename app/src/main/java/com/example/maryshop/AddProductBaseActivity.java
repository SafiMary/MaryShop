package com.example.maryshop;

import android.os.Bundle;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;


public class AddProductBaseActivity extends AppCompatActivity implements View.OnClickListener {

    public Toolbar tool_main;
    public TextView setTitle_main;
    Button btn_add_product;
    TextView nomination, price, balance;
    Spinner category;
    ImageView photo;
    AppProvider.DBProvider dbProvider;
    Cursor cursor;
    ContentValues contentValues;
    String nominationStr, categoryStr, priceStr, photoStr;
    Intent intent;
    Toast toast;
    Integer count, balanceStr;
    private static final int SELECT_PICTURE = 100;
    Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product_base);
        btn_add_product = (Button) findViewById(R.id.btn_add_product);
        nomination = (TextView) findViewById(R.id.nomination);
        balance = (TextView) findViewById(R.id.balance);
        category = (Spinner) findViewById(R.id.category);
        price = (TextView) findViewById(R.id.price);
        photo = (ImageView) findViewById(R.id.photo);
        dbProvider = new AppProvider.DBProvider(this);//объект базы данных
        contentValues = new ContentValues();//для добавления новых строк в таблицу
        cursor = dbProvider.getAllData();//получаем курсор
        // Получим массив строк из ресурсов
//        Resources res = getResources();
//        String[] prod = res.getStringArray(R.array.prod_spinner);
        // Создаем адаптер ArrayAdapter и Настраиваем адаптер
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.prod_spinner, android.R.layout.simple_spinner_item);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        category.setAdapter(adapter);

//прослушиваем спиннер с категориями
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                count = (Integer) getSelectedItemPosition();
            }

            public int getSelectedItemPosition() {
                return category.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        // прослушиваем выбор категории
        category.setOnItemSelectedListener(itemSelectedListener);
    }
        //обработка нажатий на 3 кнопки
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_add_product) {
                nominationStr = nomination.getText().toString();
                priceStr = price.getText().toString();
                balanceStr = Integer.parseInt(balance.getText().toString());

                // Сохранение в бд
                dbProvider.InsertTableProducts(nominationStr, priceStr, (saveImageInDB(selectedImageUri)), balanceStr, count);
                toast = Toast.makeText(this, "Изображение сохранено в бд", Toast.LENGTH_LONG);
                toast.show();
            }
            if (v.getId() == R.id.photo) {
                openImageChooser();
            }
            dbProvider.close();

        
    }

    //Выбор изображения из галереи
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Выберите картинку"), SELECT_PICTURE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                selectedImageUri = data.getData();

                if (null != selectedImageUri) {
                    photo.setImageURI(selectedImageUri);

                    // Считывание из бд изображения
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (loadImageFromDB()) {
//                                toast = Toast.makeText(this, "Изображение выгрузилось из бд",Toast.LENGTH_LONG);
//                                toast.show();
                            }
                        }
                    }, 3000);
                }

            }
        }
    }


    // Save the
    public byte[] saveImageInDB(Uri selectedImageUri) {
        try {
            InputStream iStream = getContentResolver().openInputStream(selectedImageUri);
            byte[] inputData = ImageConvert.getBytes(iStream);
            return inputData;
        } catch (IOException ioe) {
            return null;
        }

    }


    Boolean loadImageFromDB() {
        try {
            dbProvider.getWritableDatabase();
            byte[] bytes = dbProvider.retreiveImageFromDB();
            dbProvider.close();
            // Показать изображение из DB в ImageView
            photo.setImageBitmap(ImageConvert.getImage(bytes));
            return true;
        } catch (Exception e) {
            dbProvider.close();
            return false;
        }
    }

}

