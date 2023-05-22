package com.example.kasirkoo.databaseshelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class KeranjangDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "Kasirkoo.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "keranjang";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TOTAL = "keranjang_total";

    public KeranjangDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("ONCREATE MASUK");
        String query =  "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TOTAL + " TEXT); ";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addTotalBelanja(String price){
        String totalBelanja = "0";
        Cursor belanjaan = getTotalBelanjang();
        if(belanjaan.getCount() == 0) {
            totalBelanja = "0";
        }
        else{
            while (belanjaan.moveToNext()){
                totalBelanja = belanjaan.getString(0);
            }
        }

        Float belanjaanBaru = Float.parseFloat(price);
        Float totalSebelumnya = Float.parseFloat(totalBelanja);
        Float totalBelanjaBaru = belanjaanBaru + totalSebelumnya;

        String total = totalBelanjaBaru.toString();

        System.out.println("TOTAL BELANJAAN BARU = " + belanjaanBaru);
        System.out.println("TOTAL BELANJAAN SEBELUMNYA = " + totalSebelumnya);
        System.out.println("TOTAL BELANJAAN = " + total);
        System.out.println(belanjaan.getCount() == 0);
        System.out.println(belanjaan.getCount() != 0);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TOTAL, total);

        if(belanjaan.getCount() == 0) {
            System.out.println("Masuk Create");
            long result = db.insert(TABLE_NAME,null,cv);
            if(result == -1){
                Toast.makeText(context, "Gagal menambahkan", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Sukses menambahkan!", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            System.out.println("Masuk Update");
            long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{"1"});
            if(result == -1){
                Toast.makeText(context, "Gagal menambahkan", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Sukses menambahkan!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Cursor getTotalBelanjang(){
        String query = "SELECT " + COLUMN_TOTAL + " FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            String query2 =  "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                    " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TOTAL + " TEXT); ";
            db.execSQL(query2);
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor getJumlahKategori(){
        String query = "SELECT count(" + COLUMN_ID + ") FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            String query2 =  "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                    " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TOTAL + " TEXT); ";
            db.execSQL(query2);
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor readAllDataWhere(String id){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            String query2 =  "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                    " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TOTAL + " TEXT); ";
            db.execSQL(query2);
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public void updateData(String row_id, String title){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TOTAL, title);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1) Toast.makeText(context, "Gagal update", Toast.LENGTH_LONG).show();
        else Toast.makeText(context, "Update sukses", Toast.LENGTH_LONG).show();
    }

    public void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?",  new String[]{row_id});
        if(result == -1) Toast.makeText(context, "Gagal menghapus", Toast.LENGTH_LONG).show();
        else Toast.makeText(context, "Sukses menghapus", Toast.LENGTH_LONG).show();
    }
}
