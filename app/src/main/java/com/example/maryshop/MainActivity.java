package com.example.maryshop;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
 Button btnEnter,btnReg;
 Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

       btnEnter = (Button) findViewById(R.id.btnEnter);
       btnReg = (Button) findViewById(R.id.btnReg);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnEnter) {
            intent = new Intent(this, EnterActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.btnReg) {
            intent = new Intent(MainActivity.this, RegActivity.class);
            startActivity(intent);
        }
    }
}