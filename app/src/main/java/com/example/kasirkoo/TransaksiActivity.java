package com.example.kasirkoo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kasirkoo.databaseshelper.KeranjangDatabaseHelper;
import com.example.kasirkoo.databaseshelper.MyDatabaseHelper;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class TransaksiActivity extends AppCompatActivity {

    ImageView imageBack;
    TextView result_textView,total_textView;
    Button num1,num2,num3,num4,num5,num6,num7,num8,num9,num0,num000,numC,delete_button,add_button,cariProduk;
    ExtendedFloatingActionButton checkout_button;
    String resTemp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

        setUp();
        setUpOnClick();
    }

    void setUp(){
        imageBack = findViewById(R.id.imageBack);
        result_textView = findViewById(R.id.result_textView);
        total_textView = findViewById(R.id.total_textView);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
        num5 = findViewById(R.id.num5);
        num6 = findViewById(R.id.num6);
        num7 = findViewById(R.id.num7);
        num8 = findViewById(R.id.num8);
        num9 = findViewById(R.id.num9);
        num0 = findViewById(R.id.num0);
        num000 = findViewById(R.id.num000);
        numC = findViewById(R.id.numC);
        delete_button = findViewById(R.id.delete_button);
        add_button = findViewById(R.id.add_button);
        cariProduk = findViewById(R.id.cariProduk);
        checkout_button = findViewById(R.id.checkout_button);
    }

    void setUpOnClick(){
        num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempRes = resTemp + "1";
                if(Float.parseFloat(tempRes)>1000000000){
                    Toast.makeText(TransaksiActivity.this, "Nominal terlalu besar!", Toast.LENGTH_SHORT).show();
                }else{
                    resTemp += "1";
                    setResult(resTemp);
                }
            }
        });

        num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempRes = resTemp + "2";
                if(Float.parseFloat(tempRes)>1000000000){
                    Toast.makeText(TransaksiActivity.this, "Nominal terlalu besar!", Toast.LENGTH_SHORT).show();
                }else{
                    resTemp += "2";
                    setResult(resTemp);
                }
            }
        });

        num3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempRes = resTemp + "3";
                if(Float.parseFloat(tempRes)>1000000000){
                    Toast.makeText(TransaksiActivity.this, "Nominal terlalu besar!", Toast.LENGTH_SHORT).show();
                }else{
                    resTemp += "3";
                    setResult(resTemp);
                }
            }
        });

        num4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempRes = resTemp + "4";
                if(Float.parseFloat(tempRes)>1000000000){
                    Toast.makeText(TransaksiActivity.this, "Nominal terlalu besar!", Toast.LENGTH_SHORT).show();
                }else{
                    resTemp += "4";
                    setResult(resTemp);
                }
            }
        });

        num5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempRes = resTemp + "5";
                if(Float.parseFloat(tempRes)>1000000000){
                    Toast.makeText(TransaksiActivity.this, "Nominal terlalu besar!", Toast.LENGTH_SHORT).show();
                }else{
                    resTemp += "5";
                    setResult(resTemp);
                }
            }
        });

        num6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempRes = resTemp + "6";
                if(Float.parseFloat(tempRes)>1000000000){
                    Toast.makeText(TransaksiActivity.this, "Nominal terlalu besar!", Toast.LENGTH_SHORT).show();
                }else{
                    resTemp += "6";
                    setResult(resTemp);
                }
            }
        });

        num7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempRes = resTemp + "7";
                if(Float.parseFloat(tempRes)>1000000000){
                    Toast.makeText(TransaksiActivity.this, "Nominal terlalu besar!", Toast.LENGTH_SHORT).show();
                }else{
                    resTemp += "7";
                    setResult(resTemp);
                }
            }
        });

        num8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempRes = resTemp + "8";
                if(Float.parseFloat(tempRes)>1000000000){
                    Toast.makeText(TransaksiActivity.this, "Nominal terlalu besar!", Toast.LENGTH_SHORT).show();
                }else{
                    resTemp += "8";
                    setResult(resTemp);
                }
            }
        });

        num9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempRes = resTemp + "9";
                if(Float.parseFloat(tempRes)>1000000000){
                    Toast.makeText(TransaksiActivity.this, "Nominal terlalu besar!", Toast.LENGTH_SHORT).show();
                }else{
                    resTemp += "9";
                    setResult(resTemp);
                }
            }
        });

        num0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempRes = resTemp + "0";
                if(Float.parseFloat(tempRes)>1000000000){
                    Toast.makeText(TransaksiActivity.this, "Nominal terlalu besar!", Toast.LENGTH_SHORT).show();
                }else{
                    resTemp += "0";
                    setResult(resTemp);
                }
            }
        });

        num000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempRes = resTemp + "000";
                if(Float.parseFloat(tempRes)>1000000000){
                    Toast.makeText(TransaksiActivity.this, "Nominal terlalu besar!", Toast.LENGTH_SHORT).show();
                }else{
                    resTemp += "000";
                    setResult(resTemp);
                }
            }
        });

        numC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resTemp = "0";
                setResult(resTemp);
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resTemp =  resTemp.substring(0, resTemp.length() - 1);
                setResult(resTemp);
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeranjangDatabaseHelper myDB = new KeranjangDatabaseHelper(TransaksiActivity.this);
                myDB.addTotalBelanja(resTemp);
                resTemp = "0";
                setResult(resTemp);

                String totalBelanja = "0";
                Cursor belanjaan = myDB.getTotalBelanjang();
                if(belanjaan.getCount() == 0) {
                    totalBelanja = "0";
                }
                else{
                    while (belanjaan.moveToNext()){
                        totalBelanja = belanjaan.getString(0);
                    }
                }

                setTotalTextView(totalBelanja);
            }
        });

        cariProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    void setResult(String result){
        float number = Float.parseFloat(result);

        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedNumber = decimalFormat.format(number);
        result_textView.setText(formattedNumber);
    }

    void setTotalTextView(String total){
        float number = Float.parseFloat(total);

        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedNumber = decimalFormat.format(number);
        total_textView.setText(formattedNumber);
    }
}