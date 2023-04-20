package com.example.kasirkoo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateProductActivity extends AppCompatActivity {

    EditText title_input, price_input, stock_input, code_input;
    Button update_button,delete_button;
    String id, title, price, stock, code;
    TextView product_title_textView,backTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        title_input = findViewById(R.id.title_input2);
        price_input = findViewById(R.id.price_input2);
        stock_input = findViewById(R.id.stock_input2);
        code_input = findViewById(R.id.code_input2);
        product_title_textView = findViewById(R.id.product_title_textView);
        delete_button = findViewById(R.id.delete_button);
        backTextView = findViewById(R.id.backTextView);

        getAndSetIntentDatea();

        product_title_textView.setText(title);

        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setTitle(title);
        }

        update_button = findViewById(R.id.update_button);
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateProductActivity.this);
                title = title_input.getText().toString().trim();
                price = price_input.getText().toString().trim();
                stock = stock_input.getText().toString().trim();
                code = code_input.getText().toString().trim();
                myDB.updateData(id,title,Integer.valueOf(price),Integer.valueOf(stock),code);
                Intent intent = new Intent(UpdateProductActivity.this, ProdukListActivity.class);
                startActivity(intent);
                finish();
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateProductActivity.this, ProdukListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void getAndSetIntentDatea(){
        if(getIntent().hasExtra("id") &&
                getIntent().hasExtra("title") &&
                getIntent().hasExtra("price") &&
                getIntent().hasExtra("stock") &&
                getIntent().hasExtra("code")){

            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            price = getIntent().getStringExtra("price");
            stock = getIntent().getStringExtra("stock");
            code = getIntent().getStringExtra("code");

            title_input.setText(title);
            price_input.setText(price);
            stock_input.setText(stock);
            code_input.setText(code);
        }else{
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete " + title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateProductActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UpdateProductActivity.this, ProdukListActivity.class);
        startActivity(intent);
        finish();
    }
}