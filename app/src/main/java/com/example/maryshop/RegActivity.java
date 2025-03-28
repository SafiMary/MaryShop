package com.example.maryshop;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class RegActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnRegistr;
    TextView name, phone, email, address, pass;
    DBHelper dbHelper;
    Cursor cursor;
    ContentValues contentValues;
    String nameStr, phoneStr, emailStr, addressStr, passStr;
    Intent intent;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reg);

        btnRegistr = (Button) findViewById(R.id.btnRegistr);
        name = (TextView) findViewById(R.id.name);
        phone = (TextView) findViewById(R.id.phone);
        email = (TextView) findViewById(R.id.email);
        address = (TextView) findViewById(R.id.address);
        pass = (TextView) findViewById(R.id.pass);
        dbHelper = new DBHelper(this);//объект базы данных
        contentValues = new ContentValues();//для добавления новых строк в таблицу
        cursor = dbHelper.getAllData();//получаем курсор
    }

    @Override
    public void onClick(View v) {
        //проверка чтоб не регистрировались с логином и паролем админа
        if (name.getText().toString().equals("admin") &&
                pass.getText().toString().equals("admin")) {
            toast = Toast.makeText(this, "Данный логин и пароль не подходят, попробуйте другие", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        //нужна проверка чтоб не регистрировались с тем же логином
        if (loginCheck(cursor, name.getText().toString())) {
            toast = Toast.makeText(this, "Данный логин уже занят, выберите другой", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        //записываем введенные данные  в базу
        if (v.getId() == R.id.btnRegistr) {
            nameStr = name.getText().toString();
            phoneStr = phone.getText().toString();
            emailStr = email.getText().toString();
            addressStr = address.getText().toString();
            passStr = pass.getText().toString();

            // записали строки в таблицу
            dbHelper.InsertData(nameStr, phoneStr, emailStr, addressStr, passStr);
            toast = Toast.makeText(this, "Пользователь " + nameStr + " зарегистрирован", Toast.LENGTH_LONG);
            toast.show();
            intent = new Intent(this, EnterActivity.class);
            startActivity(intent);

        }
        dbHelper.close();

    }

    //проверка есть ли такой логин в базе, если есть, то не регистрировать пользователя
//а сообщить что такое имя занято
    public static boolean loginCheck(Cursor cursor, String name) {
        while (cursor.moveToNext()) {
            if (cursor.getString(1).equals(name)) {
                return true;
            }
        }
        return false;
    }
}