package com.example.kasirkoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kasirkoo.databaseshelper.KategoriDatabaseHelper;
import com.example.kasirkoo.databaseshelper.MyDatabaseHelper;

public class ProductActivity extends AppCompatActivity {

    RelativeLayout list_product_button,list_kategori_button,list_stock_button;
    ImageView imageBack;
    MyDatabaseHelper myDatabaseHelper;
    KategoriDatabaseHelper kategoriDatabaseHelper;
    TextView jumlah_produk, jumlah_kategori, jumlah_stok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        setUp();

        myDatabaseHelper = new MyDatabaseHelper(ProductActivity.this);
        kategoriDatabaseHelper = new KategoriDatabaseHelper(ProductActivity.this);

        setJumlahProduk();
        setJumlahKategori();
        setJumlahStok();

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

        list_stock_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, ProdukStokActivity.class);
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
        list_stock_button = findViewById(R.id.list_stock_button);
        imageBack = findViewById(R.id.imageBack);
        jumlah_produk = findViewById(R.id.jumlah_produk);
        jumlah_kategori = findViewById(R.id.jumlah_kategori);
        jumlah_stok = findViewById(R.id.jumlah_stok);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProductActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void setJumlahProduk(){
        Cursor cursor = myDatabaseHelper.getJumlahProdukAll();
        if(cursor.getCount() == 0) {
            jumlah_produk.setText("0 produk");
        }
        else{
            while (cursor.moveToNext()){
                jumlah_produk.setText(cursor.getString(0) + " produk");
            }
        }
    }

    public void setJumlahKategori(){
        Cursor cursor = kategoriDatabaseHelper.getJumlahKategori();
        if(cursor.getCount() == 0) {
            jumlah_kategori.setText("0 kategori");
        }
        else{
            while (cursor.moveToNext()){
                jumlah_kategori.setText(cursor.getString(0) + " kategori");
            }
        }
    }

    public void setJumlahStok(){
        Cursor cursor = myDatabaseHelper.getJumlahStokAll();
        if(cursor.getCount() == 0) {
            jumlah_stok.setText("0 stok produk");
        }
        else{
            while (cursor.moveToNext()){
                jumlah_stok.setText(cursor.getString(0) + " stok produk");
            }
        }
    }
}