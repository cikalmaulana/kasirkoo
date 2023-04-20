package com.example.kasirkoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kasirkoo.databaseshelper.KategoriDatabaseHelper;
import com.example.kasirkoo.databaseshelper.MyDatabaseHelper;

public class AddKategoriActivity extends AppCompatActivity {

    EditText title_input;
    Button add_button;
    TextView backTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kategori);

        title_input = findViewById(R.id.kategori_title_input);
        add_button = findViewById(R.id.add_kategori_button);
        backTextView = findViewById(R.id.backTextView);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KategoriDatabaseHelper myDB = new KategoriDatabaseHelper(AddKategoriActivity.this);
                myDB.addBook(
                        title_input.getText().toString().trim()
                );

                Intent intent = new Intent(AddKategoriActivity.this, KategoriActivity.class);
                startActivity(intent);
                finish();
            }
        });
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddKategoriActivity.this, ProdukListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddKategoriActivity.this, KategoriActivity.class);
        startActivity(intent);
        finish();
    }
}