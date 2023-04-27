package com.example.kasirkoo;

import android.Manifest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import java.io.FileOutputStream;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    EditText title_input, price_input, stock_input, code_input;
    Button add_button;
    TextView backTextView;
    String selectedOption,selectedOptionId;
    KategoriDatabaseHelper kategoriDB;
    ImageView preview_imageView, addGambar_btn;

    Drawable produkimageDraable;
    private static final int REQUEST_GALLERY = 1;
    private static final int REQUEST_CAMERA = 2;
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private boolean cameraOptionSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ArrayList<String> inputOptions = new ArrayList<>();

        title_input = findViewById(R.id.title_input);
        price_input = findViewById(R.id.price_input);
        stock_input = findViewById(R.id.stock_input);
        code_input = findViewById(R.id.code_input);
        add_button = findViewById(R.id.add_button);
        backTextView = findViewById(R.id.backTextView);
        addGambar_btn = findViewById(R.id.addGambar_btn);
        preview_imageView = findViewById(R.id.preview_imageView);
        produkimageDraable = preview_imageView.getDrawable();
        preview_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                builder.setTitle("Tambah Gambar")
                        .setItems(new CharSequence[]{"Galeri", "Kamera"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean active = false;
                                switch (which) {
                                    case 0:
                                        // Memilih gambar dari galeri
                                        Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(intentGallery, REQUEST_GALLERY);
                                        break;
                                    case 1:
                                        cameraOptionSelected = true;
                                        // Mengambil foto dari kamera
                                        if (ContextCompat.checkSelfPermission(AddActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && cameraOptionSelected) {
                                            // Izin akses kamera sudah diberikan, jalankan aksi yang membutuhkan izin kamera di sini
                                            // Misalnya, membuka kamera atau mengambil foto dari kamera
                                            Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            startActivityForResult(intentCamera, REQUEST_CAMERA);
                                            cameraOptionSelected = false;
                                        } else {
                                            // Izin akses kamera belum diberikan, minta izin kepada pengguna
                                            ActivityCompat.requestPermissions(AddActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                                        }
                                        break;
                                }
                            }
                        })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });

        kategoriDB = new KategoriDatabaseHelper(AddActivity.this);
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

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(produkimageDraable);
                BitmapDrawable bitmapDrawable = (BitmapDrawable) produkimageDraable;
                System.out.println(bitmapDrawable);
                Bitmap bitmap = bitmapDrawable.getBitmap(); // Ganti imagePath dengan path dari gambar
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                System.out.println("TITEL INPUT = " + title_input.getText().toString());
                
                if(title_input.getText().length() <= 0){
                    Toast.makeText(AddActivity.this, "Masukan nama produk!", Toast.LENGTH_SHORT).show();
                }
                else if(price_input.getText().length() <= 0){
                    Toast.makeText(AddActivity.this, "Masukan harga produk!", Toast.LENGTH_SHORT).show();
                }
                else if(stock_input.getText().length() <= 0){
                    Toast.makeText(AddActivity.this, "Masukan stok produk atau isi dengan 0", Toast.LENGTH_SHORT).show();
                }
                else if(code_input.getText().length() <= 0){
                    Toast.makeText(AddActivity.this, "Masukan kode produk atau isi dengan -", Toast.LENGTH_SHORT).show();
                }
                else{
                    MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                    myDB.addProduk(
                            title_input.getText().toString().trim(),
                            Integer.valueOf(price_input.getText().toString().trim()),
                            Integer.valueOf(stock_input.getText().toString().trim()),
                            code_input.getText().toString().trim(),
                            Integer.valueOf(selectedOptionId),
                            byteArray
                    );

                    Intent intent = new Intent(AddActivity.this, ProdukListActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, ProdukListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddActivity.this, ProdukListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK && data != null) {
            // Memilih gambar dari galeri
            Uri imageUri = data.getData();
            preview_imageView.setImageURI(imageUri);
            produkimageDraable = preview_imageView.getDrawable();
            // Lakukan sesuatu dengan imageUri, seperti menampilkan di ImageView
        } else if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK && data != null) {
            // Mengambil foto dari kamera
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            preview_imageView.setImageBitmap(imageBitmap);
            produkimageDraable = preview_imageView.getDrawable();
            // Lakukan sesuatu dengan imageBitmap
        }
    }
}