package com.example.maryshop.ui.home;

import static android.content.Intent.getIntent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.maryshop.DBHelper;
import com.example.maryshop.HomeActivity;
import com.example.maryshop.R;
import com.example.maryshop.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    TextView hello_view;
    String name;
    private static final String TAG = "myLogs";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);
        hello_view = (TextView)view.findViewById(R.id.hello_view);
        //вывод имени пользователя не работает
        //if (getArguments() != null) {
        //name = getArguments().getString("nameStr");
        //hello_view.setText(String.valueOf(getArguments().getString("nameStr")));
        //hello_view.setText(name);
        //Log.d(TAG, "name  "  +name);
    //}
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}