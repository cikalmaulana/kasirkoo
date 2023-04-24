package com.example.kasirkoo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kasirkoo.databaseshelper.KategoriDatabaseHelper;
import com.example.kasirkoo.databaseshelper.MyDatabaseHelper;

import java.util.ArrayList;

public class UpdateProductActivity extends AppCompatActivity {

    EditText title_input, price_input, stock_input, code_input;
    Button update_button,delete_button;
    String id, title, price, stock, code, kategori, selectedOption,selectedOptionId;
    TextView product_title_textView,backTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        ArrayList<String> inputOptions = new ArrayList<>();

        title_input = findViewById(R.id.title_input2);
        price_input = findViewById(R.id.price_input2);
        stock_input = findViewById(R.id.stock_input2);
        code_input = findViewById(R.id.code_input2);
        product_title_textView = findViewById(R.id.product_title_textView);
        delete_button = findViewById(R.id.delete_button);
        backTextView = findViewById(R.id.backTextView);

        KategoriDatabaseHelper kategoriDB = new KategoriDatabaseHelper(UpdateProductActivity.this);
        Cursor cursor = kategoriDB.readAllData();
        if(cursor.getCount() == 0) {
            inputOptions.add("-");
        }
        else{
            while (cursor.moveToNext()){
                inputOptions.add(cursor.getString(1));
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, inputOptions);
        Spinner spinnerInput = findViewById(R.id.spinner_input);
        spinnerInput.setAdapter(adapter);
        spinnerInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOption = parent.getItemAtPosition(position).toString();
                selectedOptionId = String.valueOf(position + 1);
                System.out.println("VALUE = " + selectedOption);
                System.out.println("ID = " + selectedOptionId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ketika tidak ada opsi yang dipilih
            }
        });

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
                myDB.updateData(id,title,Integer.valueOf(price),Integer.valueOf(stock),code, Integer.valueOf(selectedOptionId));
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
                getIntent().hasExtra("code") &&
                getIntent().hasExtra("kategori")){

            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            price = getIntent().getStringExtra("price");
            stock = getIntent().getStringExtra("stock");
            code = getIntent().getStringExtra("code");
            kategori = getIntent().getStringExtra("kategori");

            title_input.setText(title);
            price_input.setText(price);
            stock_input.setText(stock);
            code_input.setText(code);
        }else{
            Toast.makeText(this, "Tidak ada data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        View alertCustomDialog = LayoutInflater.from(UpdateProductActivity.this).inflate(R.layout.custom_dialog,null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setView(alertCustomDialog);
        Button acceptBtn = (Button) alertCustomDialog.findViewById(R.id.delete_button);
        Button noBtn = (Button) alertCustomDialog.findViewById(R.id.no_button);

        final AlertDialog dialog = alertDialog.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateProductActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

//        builder.setTitle("Hapus " + title + " ?");
//        builder.setMessage("Anda yakin menghapus " + title + " ?");
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateProductActivity.this);
//                myDB.deleteOneRow(id);
//                finish();
//            }
//        });
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        builder.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UpdateProductActivity.this, ProdukListActivity.class);
        startActivity(intent);
        finish();
    }
}