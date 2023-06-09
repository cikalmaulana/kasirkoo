package com.example.kasirkoo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProdukActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    MyDatabaseHelper myDB;
    ArrayList<String> product_id,product_title, product_price, product_stock, product_code;
    CustomAdapter customAdapter;
    ImageView imageBack;
    TextView no_product_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk);
        setUp();
    }

    void setUp(){
        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        imageBack = findViewById(R.id.imageBack);
        no_product_textView = findViewById(R.id.no_product_textView);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProdukActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProdukActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        myDB = new MyDatabaseHelper(ProdukActivity.this);
        product_id = new ArrayList<>();
        product_title = new ArrayList<>();
        product_price = new ArrayList<>();
        product_stock = new ArrayList<>();
        product_code = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(ProdukActivity.this,this, product_id,product_title,product_price,product_stock,product_code);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProdukActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0) {
            no_product_textView.setVisibility(View.VISIBLE);
        }
        else{
            no_product_textView.setVisibility(View.GONE);
            while (cursor.moveToNext()){
                product_id.add(cursor.getString(0));
                product_title.add(cursor.getString(1));
                product_price.add(cursor.getString(2));
                product_stock.add(cursor.getString(3));
                product_code.add(cursor.getString(4));
            }
        }
    }
}