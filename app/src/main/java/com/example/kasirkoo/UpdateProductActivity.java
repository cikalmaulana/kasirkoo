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

public class UpdateProductActivity extends AppCompatActivity {

    private static final int REQUEST_GALLERY = 1;
    private static final int REQUEST_CAMERA = 2;
    private static final int REQUEST_CAMERA_PERMISSION = 1;

    EditText title_input, price_input, stock_input, code_input;
    Button update_button,delete_button;
    String id, title, price, stock, code, kategori, selectedOption, selectedOptionId;
    byte[] image_bitmap;
    TextView product_title_textView,backTextView;
    ImageView product_imageView,addGambar_btn;
    Drawable produkimageDraable;
    int quality = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        ArrayList<String> inputOptions = new ArrayList<>();
        title_input = findViewById(R.id.title_input2);
        price_input = findViewById(R.id.price_input2);
        stock_input = findViewById(R.id.stock_input2);
        code_input = findViewById(R.id.code_input2);

        product_imageView = findViewById(R.id.product_imageView);
        produkimageDraable = product_imageView.getDrawable();
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ketika tidak ada opsi yang dipilih
            }
        });

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
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
                byte[] byteArray = stream.toByteArray();

                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateProductActivity.this);
                title = title_input.getText().toString().trim();
                price = price_input.getText().toString().trim();
                stock = stock_input.getText().toString().trim();
                code = code_input.getText().toString().trim();
                myDB.updateData(id,title,Integer.valueOf(price),Integer.valueOf(stock),code, Integer.valueOf(selectedOptionId),byteArray);
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

        product_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProductActivity.this);
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
                                        // Mengambil foto dari kamera
                                        if (ContextCompat.checkSelfPermission(UpdateProductActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                                            Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            startActivityForResult(intentCamera, REQUEST_CAMERA);
                                        } else {
                                            ActivityCompat.requestPermissions(UpdateProductActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
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
    }

    void getAndSetIntentDatea(){
        if(getIntent().hasExtra("id") &&
                getIntent().hasExtra("title") &&
                getIntent().hasExtra("price") &&
                getIntent().hasExtra("stock") &&
                getIntent().hasExtra("code") &&
                getIntent().hasExtra("kategori") &&
                getIntent().hasExtra("images")){

            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            price = getIntent().getStringExtra("price");
            stock = getIntent().getStringExtra("stock");
            code = getIntent().getStringExtra("code");
            kategori = getIntent().getStringExtra("kategori");
            image_bitmap = getIntent().getByteArrayExtra("images"); //kirim berupa byte array

            Bitmap bmp = BitmapFactory.decodeByteArray(image_bitmap, 0, image_bitmap.length); //dconvert byte array jadi bitmap lagi

            title_input.setText(title);
            price_input.setText(price);
            stock_input.setText(stock);
            code_input.setText(code);
            product_imageView.setImageBitmap(bmp);
            produkimageDraable = product_imageView.getDrawable();
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
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UpdateProductActivity.this, ProdukListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK && data != null) {
            // Memilih gambar dari galeri
            Uri imageUri = data.getData();
            int size = getImageSize(imageUri);
            if(size > 3000){
                Toast.makeText(this, "File terlalu besar! Maksimal ukuran 3MB", Toast.LENGTH_LONG).show();
            }else{
                if(size > 2000 && size <= 3000){
                    quality = 3;
                }else if(size > 1000 && size <= 2000){
                    quality = 4;
                }else if(size > 500 && size <= 1000){
                    quality = 8;
                }else if(size > 100 && size <= 500){
                    quality = 18;
                }else{
                    quality = 100;
                }
                product_imageView.setImageURI(imageUri);
                produkimageDraable = product_imageView.getDrawable();
            }
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

    public int getImageSize(Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.SIZE};
        Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int sizeIndex = cursor.getColumnIndex(filePathColumn[0]);
        int size = cursor.getInt(sizeIndex) / 1024; // in KB
        cursor.close();
        return size;
    }
}