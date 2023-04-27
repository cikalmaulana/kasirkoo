package com.example.kasirkoo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kasirkoo.adapter.StokAdapter;
import com.example.kasirkoo.databaseshelper.KategoriDatabaseHelper;
import com.example.kasirkoo.databaseshelper.MyDatabaseHelper;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

public class ProdukStokActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyDatabaseHelper myDB;
    ArrayList<String> product_id,product_title, product_stock, product_code;
    ArrayList product_imageList;
    StokAdapter stokAdapter;
    ImageView imageBack, product_image;
    TextView no_product_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_stok);
        setUp();
    }

    void setUp(){
        recyclerView = findViewById(R.id.recyclerView);
        imageBack = findViewById(R.id.imageBack);
        no_product_textView = findViewById(R.id.no_product_textView);
        product_image = findViewById(R.id.product_image);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProdukStokActivity.this, ProductActivity.class);
                startActivity(intent);
                finish();
            }
        });

        myDB = new MyDatabaseHelper(ProdukStokActivity.this);
        product_id = new ArrayList<>();
        product_title = new ArrayList<>();
        product_stock = new ArrayList<>();
        product_code = new ArrayList<>();
        product_imageList = new ArrayList<>();

        storeDataInArrays();

        stokAdapter = new StokAdapter(ProdukStokActivity.this,this, product_id,product_title,product_stock,product_code,product_imageList);
        recyclerView.setAdapter(stokAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProdukStokActivity.this));
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
                byte[] byteArray = cursor.getBlob(6); //ngambil dari db nya blob
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length); //ubah ke bitmap


                product_id.add(cursor.getString(0));
                product_title.add(cursor.getString(1));
                product_stock.add(cursor.getString(3));
                product_code.add(cursor.getString(4));
                product_imageList.add(bitmap); //masukin ke arraylist nya bitmap
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProdukStokActivity.this, ProductActivity.class);
        startActivity(intent);
        finish();
    }
}