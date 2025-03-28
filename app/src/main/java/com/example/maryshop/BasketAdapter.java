package com.example.maryshop;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.maryshop.model.Order;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class BasketAdapter extends ArrayAdapter<Product> {
    ArrayList<Product> arrayList = new ArrayList<>();
    private Activity context;
    private int total = 0;
    int count = 1;
    private static final String TAG = "myLogs";

    public BasketAdapter(@NonNull Activity context, ArrayList<Product> arrayList) {
        super(context, 0, arrayList);
        this.arrayList = arrayList;
        this.context = context;
        Log.d(TAG,  "BasketAdapterconstructor ");
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.d(TAG,  "getView ");
        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.basket_catalog, parent, false);
        }
        Product currentPosition = getItem(position);
        assert currentPosition != null;


        TextView title_view = (TextView)currentItemView.findViewById(R.id.title_view);
        TextView price_view = (TextView)currentItemView.findViewById(R.id.price_view);
        TextView total_amount_view= (TextView)currentItemView.findViewById(R.id.total_amount_view);
        ImageView photo_view = (ImageView)currentItemView.findViewById(R.id.photo_view);
        Button btn_delete = (Button) currentItemView.findViewById(R.id.btn_delete);
        TextView sum_view = (TextView)currentItemView.findViewById(R.id.sum_view);

        total_amount_view.setText(String.valueOf(currentPosition.getAmount()));
        title_view.setText(currentPosition.getTitle());
        price_view.setText(String.valueOf(currentPosition.getPrice()));
        photo_view.setImageBitmap(ImageConvert.getImage(currentPosition.getPhoto()));
        btn_delete.setTag(currentPosition);


        total_amount_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = context.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog_alert, null);
                dialogBuilder.setView(dialogView);
                final TextView amount_view= (TextView)dialogView.findViewById(R.id.amount_view);
                final Button btn_plus= (Button) dialogView.findViewById(R.id.btn_plus);
                final Button btn_minus= (Button) dialogView.findViewById(R.id.btn_minus);
                final Button btn_dialog = (Button) dialogView.findViewById(R.id.btn_dialog);
                dialogBuilder.setTitle("Выберите кол-во: " + currentPosition.getAmount());
                final AlertDialog b = dialogBuilder.create();
                b.show();
                //прослушали кнопку плюс
                btn_plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        count = Integer.parseInt(String.valueOf(amount_view.getText()));
                        count++;
                        amount_view.setText("" + count);


                    }
                });
//                  //прослушали кнопку минус
                btn_minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        count = Integer.parseInt(String.valueOf(amount_view.getText()));
                        if (count == 1) {
                            amount_view.setText("1");
                        } else {
                            count -= 1;
                            amount_view.setText("" + count);
                        }
                    }
                });
                btn_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        total = ((currentPosition.getPrice()) * count);
                        amount_view.setText(String.valueOf(total));
                        total_amount_view.setText(String.valueOf(count));
                        b.dismiss();
                        currentPosition.setPrice(total);
                        //получаем сумму и отправляем ее в сет,чтобы потом вывести эту сумму на экране
                        Order.items_sum.add(calculateTotal());
                        Toast.makeText(context, "total : " + calculateTotal(), Toast.LENGTH_LONG).show();


                    }
                });
            }
        });


        //удаляем продукты из корзины
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //удалили из списка
                remove(currentPosition);
                //а нужно еще удалить из адаптера(из листа)
                //Product product = (Product) view.getTag();
                Log.d(TAG, " "  +currentPosition+ " currentPosition ");
                //arrayList.remove(currentPosition);
                notifyDataSetChanged();


             }
        });

        return currentItemView;
    }
    public int calculateTotal(){
        int total = 0;
        for(Product product: arrayList){
            total+=  product.getPrice();
        }
        Log.d(TAG, " "  +total+ " calculateTotal ");
        return total;
    }

    }




