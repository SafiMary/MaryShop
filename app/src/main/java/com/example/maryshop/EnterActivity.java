package com.example.maryshop;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.maryshop.ui.home.HomeFragment;


public class EnterActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnEnters;
    TextView name,pass;
    DBHelper dbHelper;
    Intent intent,intent_extra;
    String nameStr,passStr;
    Cursor cursor;
    HomeFragment homefragment;
    Bundle bundle;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enter);

        btnEnters= (Button) findViewById(R.id.btnEnters);
        name = (TextView) findViewById(R.id.name);
        pass = (TextView) findViewById(R.id.pass);
        dbHelper = new DBHelper(this);//объект базы данных




    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnEnters) {
            nameStr = name.getText().toString();
            passStr = pass.getText().toString();
            cursor = dbHelper.getAllData();
//передаем имя в наш фрагмент чтобы поприветствовать пользователя
//            fragmentManager = getSupportFragmentManager();
//            fragmentTransaction = fragmentManager.beginTransaction();
//            bundle = new Bundle();
//            bundle.putString("nameStr", nameStr);
//            homefragment = new HomeFragment();
//            homefragment.setArguments(bundle);
//            fragmentTransaction.replace(R.id.frameLayout,homefragment).commit();


            if(cursor.getCount() == 0){
                Toast.makeText(this,"Нет ни одной записи в базе",Toast.LENGTH_LONG).show();
            }
            if (loginCheck(cursor,nameStr,passStr)) {
                intent = new Intent(this,HomeActivity.class);

                startActivity(intent);
            }

                //если это админ,то его перебросит на страницу склада
            if (nameStr.equals("admin")&& passStr.equals("admin")) {
                    intent = new Intent(this, StorehouseActivity.class);
                    startActivity(intent);

            }

            cursor.close();
            dbHelper.close();

        }
    }
//метод проверки есть ли в базе такой пользователь
    public static boolean loginCheck(Cursor cursor,String name,String pass) {
        while (cursor.moveToNext()){
            if (cursor.getString(1).equals(name)) {
                if (cursor.getString(5).equals(pass)) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

}