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

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.MyViewHolder>{

    public Context context;
    public ArrayList kategori_id, kategori_title;
    Activity activity;
    int index = 0;

    public KategoriAdapter(Activity activity, Context context, ArrayList kategori_id, ArrayList kategori_title){
        this.activity = activity;
        this.context = context;
        this.kategori_id = kategori_id;
        this.kategori_title = kategori_title;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.kategori_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KategoriAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        index++;
        holder.kategori_id_txt.setText(index+"");
        holder.kategori_title_txt.setText(String.valueOf(kategori_title.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateProductActivity.class);
                intent.putExtra("id",String.valueOf(kategori_id.get(position)));
                intent.putExtra("title",String.valueOf(kategori_title.get(position)));
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return kategori_id.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView kategori_id_txt, kategori_title_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            kategori_id_txt = itemView.findViewById(R.id.kategori_id_txt);
            kategori_title_txt = itemView.findViewById(R.id.kategori_title_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
