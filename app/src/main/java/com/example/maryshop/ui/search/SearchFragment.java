package com.example.maryshop.ui.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.maryshop.BreadActivity;
import com.example.maryshop.FishActivity;
import com.example.maryshop.GroceryActivity;
import com.example.maryshop.MeatActivity;
import com.example.maryshop.MilkActivity;
import com.example.maryshop.R;
import com.example.maryshop.VegetableActivity;
import com.example.maryshop.databinding.FragmentSearchBinding;


public class SearchFragment extends Fragment implements View.OnClickListener
//        View.OnClickListener,AdapterView.OnItemClickListener {
{
    private FragmentSearchBinding binding;
    private static final String TAG = "myLogs";
    ListView list_view;
    Context context = null;
    String[] category;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        SearchViewModel searchViewModel =
//                new ViewModelProvider(this).get(SearchViewModel.class);
////
//        binding = FragmentSearchBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
////
//        final TextView textView = binding.textSearch;
//        searchViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        context = this.getContext();
        list_view = (ListView) rootView.findViewById(R.id.list_view);
        // Получим массив строк из ресурсов
        Resources res = context.getResources();
        category = res.getStringArray(R.array.prod_array);
        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, category);
        // присваиваем адаптер списку
        list_view.setAdapter(adapter);
        //прослушиваем list_view
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                TextView textView = (TextView) itemClicked;
                String strText = textView.getText().toString(); // получаем текст нажатого элемента
                if(strText.equalsIgnoreCase(category[0])) {
                    // Запускаем активность, связанную с определенной категорией
                    startActivity(new Intent(getActivity(), MilkActivity.class));
                }
                if(strText.equalsIgnoreCase(category[1])) {
                    // Запускаем активность, связанную с определенной категорией
                    startActivity(new Intent(getActivity(), VegetableActivity.class));
                }
                if(strText.equalsIgnoreCase(category[2])) {
                    // Запускаем активность, связанную с определенной категорией
                    startActivity(new Intent(getActivity(), MeatActivity.class));
                }
                if(strText.equalsIgnoreCase(category[3])) {
                    // Запускаем активность, связанную с определенной категорией
                    startActivity(new Intent(getActivity(), BreadActivity.class));
                }
                if(strText.equalsIgnoreCase(category[4])) {
                    // Запускаем активность, связанную с определенной категорией
                    startActivity(new Intent(getActivity(), FishActivity.class));
                }
                if(strText.equalsIgnoreCase(category[5])) {
                    // Запускаем активность, связанную с определенной категорией
                    startActivity(new Intent(getActivity(), GroceryActivity.class));
                }


                }
        });

        return rootView;
    }

    @Override
    public void onClick(View v) {

    }

}




