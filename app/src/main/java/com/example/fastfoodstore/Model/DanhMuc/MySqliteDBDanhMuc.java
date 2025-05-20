package com.example.fastfoodstore.Model.DanhMuc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.fastfoodstore.Model.MonAn.MonAn;

import java.util.ArrayList;

public class MySqliteDBDanhMuc extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QuanLyDanhMucNew.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_DANHMUC = "DanhMuc";
    private static final String COLUMN_DANHMUC_ID = "MaDanhMuc";
    private static final String COLUMN_DANHMUC_NAME = "TenDanhMuc";
    private static final String COLUMN_IMAGE = "Image";

    private static final String TABLE_MONAN = "MonAn";
    private static final String COLUMN_MONAN_ID = "MaMon";
    private static final String COLUMN_MONAN_NAME = "TenMonAn";
    private static final String COLUMN_MONAN_PRICE = "GiaTien";
    private static final String COLUMN_MONAN_DESCRIPTION = "MoTaChiTiet";
    private static final String COLUMN_MONAN_IMAGE = "Image";
    private static final String COLUMN_MONAN_DANHMUC_ID = "IdNhomMon";

    private static final String TABLE_GIOHANG = "GioHangNew";
    private static final String COLUMN_MONAN_SOLUONG = "SoLuong";

    public MySqliteDBDanhMuc(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDanhMucTable = "CREATE TABLE " + TABLE_DANHMUC + " (" +
                COLUMN_DANHMUC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DANHMUC_NAME + " TEXT NOT NULL, " +
                COLUMN_IMAGE + " BLOB NOT NULL)";
        db.execSQL(createDanhMucTable);

        String createMonAnTable = "CREATE TABLE " + TABLE_MONAN + " (" +
                COLUMN_MONAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MONAN_NAME + " TEXT NOT NULL, " +
                COLUMN_MONAN_PRICE + " REAL NOT NULL, " +
                COLUMN_MONAN_DESCRIPTION + " TEXT NOT NULL, " +
                COLUMN_MONAN_IMAGE + " BLOB, " +
                COLUMN_MONAN_DANHMUC_ID + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + COLUMN_MONAN_DANHMUC_ID + ") REFERENCES " +
                TABLE_DANHMUC + "(" + COLUMN_DANHMUC_ID + ") ON DELETE CASCADE)";
        db.execSQL(createMonAnTable);

        String createGioHangTable = "CREATE TABLE " + TABLE_GIOHANG + " (" +
                COLUMN_MONAN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_MONAN_NAME + " TEXT NOT NULL, " +
                COLUMN_MONAN_PRICE + " REAL NOT NULL, " +
                COLUMN_MONAN_IMAGE + " BLOB, " +
                COLUMN_MONAN_DANHMUC_ID + " INTEGER NOT NULL, " +
                "tenDanhMuc TEXT, " +  
                COLUMN_MONAN_SOLUONG + " INTEGER DEFAULT 1, " +
                "FOREIGN KEY(" + COLUMN_MONAN_DANHMUC_ID + ") REFERENCES " +
                TABLE_DANHMUC + "(" + COLUMN_DANHMUC_ID + ") ON DELETE CASCADE)";
        db.execSQL(createGioHangTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GIOHANG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DANHMUC);
        onCreate(db);
    }

    public void themVaoGioHang(MonAn monAn, int soLuongMoi) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_MONAN_SOLUONG + " FROM " + TABLE_GIOHANG + " WHERE " + COLUMN_MONAN_ID + " = ?",
                new String[]{String.valueOf(monAn.getMaMonAn())});

        if (cursor.moveToFirst()) {
            int soluongHienTai = cursor.getInt(0);
            int tongSoLuong = soluongHienTai + soLuongMoi;

            ContentValues values = new ContentValues();
            values.put(COLUMN_MONAN_SOLUONG, tongSoLuong);

            db.update(TABLE_GIOHANG, values, COLUMN_MONAN_ID + " = ?",
                    new String[]{String.valueOf(monAn.getMaMonAn())});
        } else {
            ContentValues values = new ContentValues();
            values.put(COLUMN_MONAN_ID, monAn.getMaMonAn());
            values.put(COLUMN_MONAN_NAME, monAn.getTenMonAn());
            values.put(COLUMN_MONAN_PRICE, monAn.getGiaTien());
            values.put(COLUMN_MONAN_IMAGE, monAn.getImageMonAn());
            values.put(COLUMN_MONAN_DANHMUC_ID, monAn.getMaNhomMonAn());
            values.put("tenDanhMuc", monAn.getTenNhomMon());
            values.put(COLUMN_MONAN_SOLUONG, soLuongMoi);

            db.insert(TABLE_GIOHANG, null, values);
        }

        cursor.close();
        db.close();
    }


    public void clearGioHang() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_GIOHANG + " WHERE " + COLUMN_MONAN_ID + " > 0"); // Bỏ dấu ngoặc dư
        db.close();
    }


    public void capNhatSoLuongGioHang(int maMonAn, int soLuongMoi) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("SoLuong", soLuongMoi);
        db.update("GioHangNew", values, "MaMon = ?", new String[]{String.valueOf(maMonAn)});
        db.close();
    }


    public void  deleteMonAnInGioHan(int id){
        SQLiteDatabase db1 = getWritableDatabase();
        db1.delete(TABLE_GIOHANG, COLUMN_MONAN_ID + " = ?", new String[]{String.valueOf(id)});
        db1.close();
    }

    public ArrayList<MonAn> getAllMonAnInGioHang() {
        ArrayList<MonAn> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GIOHANG, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MONAN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MONAN_NAME));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_MONAN_PRICE));
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_MONAN_IMAGE));
                int danhMucId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MONAN_DANHMUC_ID));
                int soLuong = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MONAN_SOLUONG));
                String tenDanhMuc = cursor.getString(cursor.getColumnIndexOrThrow("tenDanhMuc"));
                MonAn monAn = new MonAn(id, name, price, image, danhMucId, soLuong,tenDanhMuc);
                list.add(monAn);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }


    public void insertDanhMuc(String tenDanhMuc, byte[] image) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO " + TABLE_DANHMUC + " VALUES (NULL, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, tenDanhMuc);
        statement.bindBlob(2, image);
        statement.executeInsert();
        db.close();
    }

    public ArrayList<DanhMuc> getDanhMuc() {
        ArrayList<DanhMuc> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_DANHMUC;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                byte[] image = cursor.getBlob(2);
                list.add(new DanhMuc(id, name, image));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return list;
    }

    public DanhMuc getNhomMonById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        DanhMuc danhMuc = null;

        String query = " SELECT * FROM " + TABLE_DANHMUC+ " WHERE MaDanhMuc = ? ";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            int maNhomMon = cursor.getInt(0);
            String tenNhomMon = cursor.getString(1);
            byte[] image = cursor.getBlob(2);
            danhMuc = new DanhMuc(maNhomMon,tenNhomMon,image);
        }
        cursor.close();
        db.close();
        return danhMuc;
    }


    public void deleteDanhMuc(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_DANHMUC, COLUMN_DANHMUC_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateDanhMuc(int id, String newName, byte[] newImage) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DANHMUC_NAME, newName);
        values.put(COLUMN_IMAGE, newImage);
        db.update(TABLE_DANHMUC, values, COLUMN_DANHMUC_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }


    public ArrayList<String> loadNhomMonOnSpinner() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT " + COLUMN_DANHMUC_NAME + " FROM " + TABLE_DANHMUC;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return list;
    }


    public void insertMonAn(MonAn monAn) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO " + TABLE_MONAN + " (" +
                COLUMN_MONAN_NAME + "," +
                COLUMN_MONAN_PRICE + "," +
                COLUMN_MONAN_DESCRIPTION + "," +
                COLUMN_MONAN_IMAGE + "," +
                COLUMN_MONAN_DANHMUC_ID + ") VALUES (?,?,?,?,?)";
        try {
            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings();
            statement.bindString(1, monAn.getTenMonAn());
            statement.bindDouble(2, monAn.getGiaTien());
            statement.bindString(3, monAn.getMoTaChiTiet());
            statement.bindBlob(4, monAn.getImageMonAn());
            statement.bindLong(5, monAn.getMaNhomMonAn());
            statement.executeInsert();
        } catch (Exception e) {
            Log.e("DB_ERROR", "Insert MonAn failed: " + e.getMessage());
        } finally {
            db.close();
        }
    }


    public MonAn getDataMonAnById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        MonAn monAn = null;

        String query =
                "SELECT MonAn.*, DanhMuc." + COLUMN_DANHMUC_NAME + " AS tenNhom " +
                        "FROM " + TABLE_MONAN + " " +
                        "INNER JOIN " + TABLE_DANHMUC + " ON " +
                        "MonAn." + COLUMN_MONAN_DANHMUC_ID + " = DanhMuc." + COLUMN_DANHMUC_ID + " " +
                        "WHERE MonAn." + COLUMN_MONAN_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            int maMonAn = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MONAN_ID));
            String tenMonAn = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MONAN_NAME));
            double giaTien = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_MONAN_PRICE));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_MONAN_IMAGE));
            int maNhomMon = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MONAN_DANHMUC_ID));
            String moTaChiTiet = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MONAN_DESCRIPTION));
            String tenNhomMon = cursor.getString(cursor.getColumnIndexOrThrow("tenNhom")); // Lấy từ alias "tenNhom"
            monAn = new MonAn(maMonAn, tenMonAn, giaTien, image, maNhomMon,moTaChiTiet);
            monAn.setTenNhomMon(tenNhomMon);
        }

        cursor.close();
        return monAn;
    }

    public ArrayList<MonAn> getMonAnTheoDanhMuc(int idDanhMuc) {
        ArrayList<MonAn> listMonAn = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String getDataQuery = "SELECT MonAn." + COLUMN_MONAN_ID + ", " +
                "MonAn." + COLUMN_MONAN_NAME + ", " +
                "MonAn." + COLUMN_MONAN_PRICE + ", " +
                "MonAn." + COLUMN_MONAN_DESCRIPTION + ", " +
                "MonAn." + COLUMN_MONAN_IMAGE + ", " +
                "DanhMuc." + COLUMN_DANHMUC_NAME + " AS tenDanhMuc " +
                "FROM " + TABLE_MONAN + " AS MonAn " +
                "INNER JOIN " + TABLE_DANHMUC + " AS DanhMuc " +
                "ON MonAn." + COLUMN_MONAN_DANHMUC_ID + " = DanhMuc." + COLUMN_DANHMUC_ID + " " +
                "WHERE DanhMuc." + COLUMN_DANHMUC_ID + " = ?";

        Cursor cursor = db.rawQuery(getDataQuery, new String[]{String.valueOf(idDanhMuc)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MONAN_ID));
                String tenMon = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MONAN_NAME));
                double gia = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_MONAN_PRICE));
                String mota = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MONAN_DESCRIPTION));
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_MONAN_IMAGE));
                String tenDanhMuc = cursor.getString(cursor.getColumnIndexOrThrow("tenDanhMuc"));

                MonAn monAn = new MonAn(id, tenMon, gia, mota, image, tenDanhMuc);
                listMonAn.add(monAn);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return listMonAn;
    }

    public ArrayList<MonAn> getDataMonAn() {
        ArrayList<MonAn> listMonAn = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String getDataQuery = "SELECT MonAn." + COLUMN_MONAN_ID + ", " +
                "MonAn." + COLUMN_MONAN_NAME + ", " +
                "MonAn." + COLUMN_MONAN_PRICE + ", " +
                "MonAn." + COLUMN_MONAN_DESCRIPTION + ", " +
                "MonAn." + COLUMN_MONAN_IMAGE + ", " +
                "DanhMuc." + COLUMN_DANHMUC_NAME + " AS tenDanhMuc" + // Đặt bí danh rõ ràng
                " FROM " + TABLE_MONAN + " AS MonAn" +
                " INNER JOIN " + TABLE_DANHMUC + " AS DanhMuc" +
                " ON MonAn." + COLUMN_MONAN_DANHMUC_ID + " = DanhMuc." + COLUMN_DANHMUC_ID;

        Cursor cursor = db.rawQuery(getDataQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MONAN_ID));
                String tenMon = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MONAN_NAME));
                double gia = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_MONAN_PRICE));
                String mota = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MONAN_DESCRIPTION));
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_MONAN_IMAGE));
                String tenDanhMuc = cursor.getString(cursor.getColumnIndexOrThrow("tenDanhMuc")); // bí danh ở trên
                MonAn monAn = new MonAn(id, tenMon, gia, mota, image, tenDanhMuc);
                listMonAn.add(monAn);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return listMonAn;
    }

    public void deleteMonAn(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM " + TABLE_MONAN + " WHERE " + COLUMN_DANHMUC_ID + " = ?";
        db.execSQL(sql, new Object[]{id});
    }
    public boolean updateMonAnById(int id, String tenMonAn, double gia, String moTa, byte[] image, int idDanhMuc) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MONAN_NAME, tenMonAn);
        values.put(COLUMN_MONAN_PRICE, gia);
        values.put(COLUMN_MONAN_DESCRIPTION, moTa);
        values.put(COLUMN_MONAN_IMAGE, image);
        values.put(COLUMN_MONAN_DANHMUC_ID, idDanhMuc);

        int rowsAffected = db.update(
                TABLE_MONAN,
                values,
                COLUMN_MONAN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
        return rowsAffected > 0;
    }

}
