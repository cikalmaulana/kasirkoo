package com.example.kasirkoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ProductActivity extends AppCompatActivity {

    Button list_product_button,list_kategori_button;
    ImageView imageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        setUp();

        list_product_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, ProdukListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        list_kategori_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, KategoriActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void setUp(){
        list_product_button = findViewById(R.id.list_product_button);
        list_kategori_button = findViewById(R.id.list_kategori_button);
        imageBack = findViewById(R.id.imageBack);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProductActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}