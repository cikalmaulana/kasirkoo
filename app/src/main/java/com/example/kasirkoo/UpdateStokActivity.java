package com.example.kasirkoo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kasirkoo.databaseshelper.KategoriDatabaseHelper;
import com.example.kasirkoo.databaseshelper.MyDatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class UpdateStokActivity extends AppCompatActivity {

    private static final int REQUEST_GALLERY = 1;
    private static final int REQUEST_CAMERA = 2;

    EditText stock_input;
    Button update_button;
    String id, title, code, stokAwal;
    byte[] image_bitmap;
    TextView product_title_textView,backTextView,product_code_textView;
    ImageView product_imageView;
    Drawable produkimageDraable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_stok);

        ArrayList<String> inputOptions = new ArrayList<>();
        stock_input = findViewById(R.id.stock_input2);

        product_imageView = findViewById(R.id.product_imageView);
//        product_code_textView = findViewById(R.id.product_code_textView);
        produkimageDraable = product_imageView.getDrawable();
        product_title_textView = findViewById(R.id.product_title_textView);
        backTextView = findViewById(R.id.backTextView);

        KategoriDatabaseHelper kategoriDB = new KategoriDatabaseHelper(UpdateStokActivity.this);
        Cursor cursor = kategoriDB.readAllData();
        if(cursor.getCount() == 0) {
            inputOptions.add("-");
        }
        else{
            while (cursor.moveToNext()){
                inputOptions.add(cursor.getString(1));
            }
        }

        getAndSetIntentDatea();
        System.out.println("image_bitmap = " + image_bitmap);

        product_title_textView.setText(title);

        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setTitle(title);
        }

        update_button = findViewById(R.id.update_button);
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(produkimageDraable);
                BitmapDrawable bitmapDrawable = (BitmapDrawable) produkimageDraable;
                System.out.println(bitmapDrawable);
                Bitmap bitmap = bitmapDrawable.getBitmap(); // Ganti imagePath dengan path dari gambar
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateStokActivity.this);
                String stokBaru = stock_input.getText().toString().trim();
                if(Integer.valueOf(stokAwal)+Integer.valueOf(stokBaru) <0){
                    Toast.makeText(UpdateStokActivity.this, "Stok tidak boleh kurang dari 0!", Toast.LENGTH_SHORT).show();
                }else{
                    myDB.updateStok(id,Integer.valueOf(stokAwal),Integer.valueOf(stokBaru));
                    Intent intent = new Intent(UpdateStokActivity.this, ProdukStokActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateStokActivity.this, ProdukStokActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void getAndSetIntentDatea(){
        if(getIntent().hasExtra("id") &&
                getIntent().hasExtra("title") &&
                getIntent().hasExtra("stock") &&
                getIntent().hasExtra("code") &&
                getIntent().hasExtra("images")){

            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            stokAwal = getIntent().getStringExtra("stock");
            code = getIntent().getStringExtra("code");
            image_bitmap = getIntent().getByteArrayExtra("images"); //kirim berupa byte array

            Bitmap bmp = BitmapFactory.decodeByteArray(image_bitmap, 0, image_bitmap.length); //dconvert byte array jadi bitmap lagi

            product_imageView.setImageBitmap(bmp);
        }else{
            Toast.makeText(this, "Tidak ada data.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UpdateStokActivity.this, ProdukStokActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK && data != null) {
            // Memilih gambar dari galeri
            Uri imageUri = data.getData();
            product_imageView.setImageURI(imageUri);
            produkimageDraable = product_imageView.getDrawable();
            // Lakukan sesuatu dengan imageUri, seperti menampilkan di ImageView
        } else if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK && data != null) {
            // Mengambil foto dari kamera
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            product_imageView.setImageBitmap(imageBitmap);
            produkimageDraable = product_imageView.getDrawable();
            // Lakukan sesuatu dengan imageBitmap
        }
    }
}