package com.example.maryshop;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maryshop.ui.basket.BasketFragment;
import com.example.maryshop.ui.home.HomeFragment;
import com.example.maryshop.ui.profile.ProfileFragment;
import com.example.maryshop.ui.search.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.maryshop.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity  implements BottomNavigationView.OnItemSelectedListener {

    private ActivityHomeBinding binding;
    public TextView setTitle_main;
    public Toolbar tool_main;
    BottomNavigationView navView;
    ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_search,
                R.id.navigation_basket,
                R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration); //из-за этой строчки не было перехода на фрамгменты
        NavigationUI.setupWithNavController(binding.navView, navController);
        //получаем Toolbar и его заголовок
        tool_main = (Toolbar) findViewById(R.id.tool_main);
        setTitle_main = (TextView) tool_main.findViewById(R.id.setTitle_main);
        setTitle_main.setText(R.string.main);
        //navView.setOnItemSelectedListener(this);
        binding.navView.setOnItemSelectedListener(this);


    }

//прослушиваем нажатие на кнопки BottomNavigationView и устанавливаем нужны текст Toolbar

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.navigation_home) {
            setTitle_main.setText(R.string.main);
            replaceFragment(new HomeFragment());
        }
        if (item.getItemId() == R.id.navigation_search) {
            setTitle_main.setText(R.string.search);
            replaceFragment(new SearchFragment());
        }
        if (item.getItemId() == R.id.navigation_basket) {
            setTitle_main.setText(R.string.basket);
            replaceFragment(new BasketFragment());
        }
        if (item.getItemId() == R.id.navigation_profile) {
            setTitle_main.setText(R.string.profile);
            replaceFragment(new ProfileFragment());
        }
        return false;
    }
//функция переключения между фрагментами
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.commit();
    }
}