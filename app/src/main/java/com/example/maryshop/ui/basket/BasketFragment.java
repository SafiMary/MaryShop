package com.example.maryshop.ui.basket;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.maryshop.AppProvider;
import com.example.maryshop.BasketAdapter;
import com.example.maryshop.Product;
import com.example.maryshop.R;
import com.example.maryshop.databinding.FragmentBasketBinding;
import com.example.maryshop.model.Order;
import java.util.ArrayList;



public class BasketFragment extends Fragment {

    private FragmentBasketBinding binding;
    ListView list_view_basket;
    Button btn_sum;
    AppProvider.DBProvider dbProvider;
    Cursor cursor;
    private static final String TAG = "myLogs";
    BasketAdapter basketAdapter;
    Activity context;
    ArrayList<Product> basketArray;
    TextView sum_view;
    String text;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket,container,false);
        //context = this.getContext();
        context = this.getActivity();
        list_view_basket = (ListView)view.findViewById(R.id.list_view_basket);
        btn_sum = (Button) view.findViewById(R.id.btn_sum);
        sum_view = (TextView)view.findViewById(R.id.sum_view);
        dbProvider = new AppProvider.DBProvider(context);
        //нужен для нашего BasketAdapter
        basketArray = new ArrayList<>();

         //достали из нашей set значения id ,перебрали
        //добавили наши объекты класса в лист чтобы вывести на экран корзины
        for(Integer el: Order.items_id){
            basketArray.add(dbProvider.getProductsByID(el));
            Log.d(TAG, "el"  +el);
        }

        //используем адаптер
        basketAdapter = new BasketAdapter(context, basketArray);
        list_view_basket.setAdapter(basketAdapter);



        list_view_basket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        //выводим сумму заказа
        btn_sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Integer el: Order.items_sum){
                    sum_view.setText(String.valueOf(el));
                }

            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}