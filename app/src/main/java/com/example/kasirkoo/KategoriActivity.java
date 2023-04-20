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

import com.example.kasirkoo.adapter.CustomAdapter;
import com.example.kasirkoo.adapter.KategoriAdapter;
import com.example.kasirkoo.databaseshelper.KategoriDatabaseHelper;
import com.example.kasirkoo.databaseshelper.MyDatabaseHelper;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

public class KategoriActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ExtendedFloatingActionButton add_button;
    KategoriDatabaseHelper myDB;
    ArrayList<String> kategori_id,kategori_title;
    KategoriAdapter kategoriAdapter;
    ImageView imageBack;
    TextView no_kategori_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);
        setUp();
    }

    void setUp(){
        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        imageBack = findViewById(R.id.imageBack);
        no_kategori_textView = findViewById(R.id.no_kategori_textView);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KategoriActivity.this, ProductActivity.class);
                startActivity(intent);
                finish();
            }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KategoriActivity.this, AddKategoriActivity.class);
                startActivity(intent);
                finish();
            }
        });

        myDB = new KategoriDatabaseHelper(KategoriActivity.this);
        kategori_id = new ArrayList<>();
        kategori_title = new ArrayList<>();

        storeDataInArrays();

        kategoriAdapter = new KategoriAdapter(KategoriActivity.this,this, kategori_id,kategori_title);
        recyclerView.setAdapter(kategoriAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(KategoriActivity.this));
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
            no_kategori_textView.setVisibility(View.VISIBLE);
        }
        else{
            no_kategori_textView.setVisibility(View.GONE);
            while (cursor.moveToNext()){
                kategori_id.add(cursor.getString(0));
                kategori_title.add(cursor.getString(1));
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(KategoriActivity.this, ProductActivity.class);
        startActivity(intent);
        finish();
    }
}