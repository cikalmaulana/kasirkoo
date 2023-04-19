package com.example.kasirkoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    EditText title_input, price_input, stock_input, code_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        title_input = findViewById(R.id.title_input);
        price_input = findViewById(R.id.price_input);
        stock_input = findViewById(R.id.stock_input);
        code_input = findViewById(R.id.code_input);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                myDB.addBook(
                        title_input.getText().toString().trim(),
                        Integer.valueOf(price_input.getText().toString().trim()),
                        Integer.valueOf(stock_input.getText().toString().trim()),
                        code_input.getText().toString().trim()
                );

                Intent intent = new Intent(AddActivity.this, ProdukActivity.class);
                startActivity(intent);
            }
        });
    }
}