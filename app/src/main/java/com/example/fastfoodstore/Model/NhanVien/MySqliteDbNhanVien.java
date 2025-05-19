package com.example.fastfoodstore.Model.NhanVien;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MySqliteDbNhanVien extends SQLiteOpenHelper {
    private static final int VERSION_DB = 1;
    private static final String DATABASE = "newQUANLYNHANVIEN.db";
    private static final String TABLE_NAME = "NhanVien";
    private static final String COLUMN_ID = "MaNhanVien";
    private static final String COLUMN_NAME = "TenNhanVien";
    private static final String COLUMN_GIOITINH = "GioiTinh";
    private static final String COLUMN_DIACHI = "DiaChi";
    private static final String COLUMN_SODIENTHOAI = "SoDienThoai";
    private static final String COLUMN_IMAGE = "Image";

    public MySqliteDbNhanVien(@Nullable Context context) {
        super(context, DATABASE, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String executeTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_GIOITINH + " TEXT NOT NULL, " +
                COLUMN_DIACHI + " TEXT NOT NULL, " +
                COLUMN_SODIENTHOAI + " TEXT NOT NULL, " +
                COLUMN_IMAGE + " BLOB NOT NULL)";
        db.execSQL(executeTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor rawQuerys(String sql){
        SQLiteDatabase db = getWritableDatabase();
       return db.rawQuery(sql,null);

    }
    public void insertData(String name, String sdt, String diaChi, String gioiTinh, byte[] image) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO NHANVIEN VALUES (NULL , ? , ? , ?, ? , ?) ";
        SQLiteStatement sqLiteStatement = db.compileStatement(sql);
        sqLiteStatement.clearBindings();
        sqLiteStatement.bindString(1, name);
        sqLiteStatement.bindString(2, sdt);
        sqLiteStatement.bindString(3, diaChi);
        sqLiteStatement.bindString(3, gioiTinh);
        sqLiteStatement.bindBlob(4, image);
        sqLiteStatement.executeInsert();

    }
    public ArrayList<NhanVien> getAllNhanVienById() {
        ArrayList<NhanVien> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int maNv = cursor.getInt(0);
                String tenNv = cursor.getString(1);
                String sdt = cursor.getString(2);
                String diaChi = cursor.getString(3);
                String gioiTinh = cursor.getString(4);
                byte[] image = cursor.getBlob(5);

                NhanVien nhanVien = new NhanVien(maNv, tenNv, sdt, diaChi, gioiTinh, image);
                list.add(nhanVien);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }


    public long insert(ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAME, null, values);  // Insert vào bảng NhanVien
        db.close();
        return result;
    }
    public NhanVien getNhanVienById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        NhanVien nhanVien = null;

        String query = " SELECT * FROM " + TABLE_NAME+ " WHERE MaNhanVien = ? ";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            int maNv = cursor.getInt(0);
            String tenNv = cursor.getString(1);
            String sdt = cursor.getString(2);
            String diaChi = cursor.getString(3);
            String gioiTinh = cursor.getString(4);
            byte[] image = cursor.getBlob(5);
            nhanVien = new NhanVien(maNv, tenNv, sdt, diaChi, gioiTinh, image);
        }

        cursor.close();
        db.close();

        return nhanVien;
    }
    public NhanVien getNhanVienBySoDienThoai(String soDienThoai) {
        SQLiteDatabase db = getReadableDatabase();
        NhanVien nhanVien = null;

        String query = " SELECT * FROM " + TABLE_NAME+ " WHERE SoDienThoai = ? ";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(soDienThoai)});

        if (cursor.moveToFirst()) {
            int maNv = cursor.getInt(0);
            String tenNv = cursor.getString(1);
            String sdt = cursor.getString(2);
            String diaChi = cursor.getString(3);
            String gioiTinh = cursor.getString(4);
            byte[] image = cursor.getBlob(5);
            nhanVien = new NhanVien(maNv, tenNv, sdt, diaChi, gioiTinh, image);
        }

        cursor.close();
        db.close();

        return nhanVien;
    }

    public void updateData(int id, String name, String gioiTinh, String diaChi, String sdt, byte[] image) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_GIOITINH, gioiTinh);
        values.put(COLUMN_DIACHI, diaChi);
        values.put(COLUMN_SODIENTHOAI, sdt);
        values.put(COLUMN_IMAGE, image);
        db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }


    public void deleteNhanVien(int idDelete){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = " DELETE FROM "+TABLE_NAME+" WHERE "+COLUMN_ID+" = "+idDelete;
        db.execSQL(sql);
        db.close();
    }


    public Cursor getDataNhanVien(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }
}
