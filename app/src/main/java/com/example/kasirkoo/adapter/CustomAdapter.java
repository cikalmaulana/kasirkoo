package com.example.kasirkoo.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kasirkoo.R;
import com.example.kasirkoo.UpdateProductActivity;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    public Context context;
    public ArrayList product_id, product_title, product_price, product_stock, product_code;
    Activity activity;
    int index = 0;

    public CustomAdapter(Activity activity, Context context, ArrayList product_id, ArrayList product_title, ArrayList product_price, ArrayList product_stock, ArrayList product_code){
        this.activity = activity;
        this.context = context;
        this.product_id = product_id;
        this.product_title = product_title;
        this.product_price = product_price;
        this.product_stock = product_stock;
        this.product_code = product_code;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        index++;
        holder.product_id_txt.setText(index+"");
        holder.product_title_txt.setText(String.valueOf(product_title.get(position)));
        holder.product_price_txt.setText(String.valueOf(product_price.get(position)));
        holder.product_stock_txt.setText(String.valueOf(product_stock.get(position)));
        holder.product_code_txt.setText(String.valueOf(product_code.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateProductActivity.class);
                intent.putExtra("id",String.valueOf(product_id.get(position)));
                intent.putExtra("title",String.valueOf(product_title.get(position)));
                intent.putExtra("price",String.valueOf(product_price.get(position)));
                intent.putExtra("stock",String.valueOf(product_stock.get(position)));
                intent.putExtra("code",String.valueOf(product_code.get(position)));
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return product_id.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView product_id_txt, product_title_txt, product_price_txt, product_stock_txt, product_code_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_id_txt = itemView.findViewById(R.id.product_id_txt);
            product_title_txt = itemView.findViewById(R.id.product_title_txt);
            product_price_txt = itemView.findViewById(R.id.product_price_txt);
            product_stock_txt = itemView.findViewById(R.id.product_stock_txt);
            product_code_txt = itemView.findViewById(R.id.product_code_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
