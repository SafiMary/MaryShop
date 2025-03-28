package com.example.maryshop;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class StorehouseActivity extends AppCompatActivity implements View.OnClickListener {
    public Toolbar tool_main;
    public TextView setTitle_main;
    Button btn_add, btn_redact, btn_delete;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_storehouse);
        //получаем Toolbar и его заголовок
        tool_main = (Toolbar) findViewById(R.id.tool_main);
        setTitle_main = (TextView) tool_main.findViewById(R.id.setTitle_main);
        setTitle_main.setText(R.string.store);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_redact = (Button) findViewById(R.id.btn_redact);
        btn_delete = (Button) findViewById(R.id.btn_delete);


    }

    //обработка нажатий на 3 кнопки
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add) {
            startActivity(new Intent(this, AddProductBaseActivity.class));
        }
        if (v.getId() == R.id.btn_redact) {
            startActivity(new Intent(this, RedactProductBaseActivity.class));
        }
        if (v.getId() == R.id.btn_delete) {
            startActivity(new Intent(this, DeleteProductBaseActivity.class));
        }


    }


}