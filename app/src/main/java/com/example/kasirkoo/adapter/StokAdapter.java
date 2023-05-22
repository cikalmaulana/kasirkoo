package com.example.kasirkoo.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kasirkoo.R;
import com.example.kasirkoo.UpdateProductActivity;
import com.example.kasirkoo.UpdateStokActivity;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class StokAdapter extends RecyclerView.Adapter<StokAdapter.MyViewHolder>{

    public Context context;
    public ArrayList product_id, product_title, product_stock, product_code;
    public ArrayList<Bitmap> product_image;//product image dari sqlite isinya blob
    Activity activity;
    int index = 0;

    public StokAdapter(Activity activity, Context context, ArrayList product_id, ArrayList product_title, ArrayList product_stock, ArrayList product_code, ArrayList<Bitmap> product_image){ //product_image berupa bitmap yang disimpen ke array list
        this.activity = activity;
        this.context = context;
        this.product_id = product_id;
        this.product_title = product_title;
        this.product_stock = product_stock;
        this.product_code = product_code;
        this.product_image = product_image;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_stok,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StokAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        index++;

        holder.product_id_txt.setText(index+"");
        holder.product_title_txt.setText(String.valueOf(product_title.get(position)));
        holder.product_stock_txt.setText(String.valueOf(product_stock.get(position)));
        holder.product_code_txt.setText(String.valueOf(product_code.get(position)));
        holder.product_imageView.setImageBitmap(product_image.get(position));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateStokActivity.class);
                Bitmap bmp = product_image.get(position);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                byte[] byteArray = stream.toByteArray();
                bmp.recycle();

                intent.putExtra("id",String.valueOf(product_id.get(position)));
                intent.putExtra("title",String.valueOf(product_title.get(position)));
                intent.putExtra("stock",String.valueOf(product_stock.get(position)));
                intent.putExtra("code",String.valueOf(product_code.get(position)));
                intent.putExtra("images",byteArray);
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return product_id.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView product_id_txt, product_title_txt, product_stock_txt, product_code_txt;
        ImageView product_imageView;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_id_txt = itemView.findViewById(R.id.product_id_txt);
            product_title_txt = itemView.findViewById(R.id.product_title_txt);
            product_stock_txt = itemView.findViewById(R.id.product_stock_txt);
            product_code_txt = itemView.findViewById(R.id.product_code_txt);
            product_imageView = itemView.findViewById(R.id.product_image);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}

