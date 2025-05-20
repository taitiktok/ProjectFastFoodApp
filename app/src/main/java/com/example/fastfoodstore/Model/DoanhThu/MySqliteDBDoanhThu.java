package com.example.fastfoodstore.Model.DoanhThu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySqliteDBDoanhThu extends SQLiteOpenHelper {
    public MySqliteDBDoanhThu(@Nullable Context context) {
        super(context,"NewDOANHTHU.db", null,3);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS DoanhThu (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maMon INTEGER," +
                "tenMon TEXT," +
                "soLuong INTEGER," +
                "giaTien REAL," +
                "tongTien REAL," +
                "idDanhMuc INTEGER," +
                "tenDanhMuc TEXT)");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            db.execSQL("CREATE TABLE IF NOT EXISTS DoanhThuTheoThang (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "maMon INTEGER," +
                    "tenMon TEXT," +
                    "soLuong INTEGER," +
                    "giaTien REAL," +
                    "tongTien REAL," +
                    "idDanhMuc INTEGER," +
                    "tenDanhMuc TEXT)");
            db.execSQL("CREATE TABLE IF NOT EXISTS DonHang ( "
            );
        }
    }
    public void themVaoDoanhThu(DoanhThu monAn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maMon", monAn.getMaMon());
        values.put("tenMon", monAn.getTenMon());
        values.put("soLuong", monAn.getSoLuong());
        values.put("giaTien", monAn.getGiaTien());
        values.put("tongTien", monAn.getGiaTien() * monAn.getSoLuong());
        values.put("idDanhMuc", monAn.getIdDanhMuc());
        values.put("tenDanhMuc", monAn.getTenDanhMuc());
        db.insert("DoanhThu", null, values);
        db.close();
    }
    public void themVaoDoanhThuTheoThang(DoanhThu monAn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maMon", monAn.getMaMon());
        values.put("tenMon", monAn.getTenMon());
        values.put("soLuong", monAn.getSoLuong());
        values.put("giaTien", monAn.getGiaTien());
        values.put("tongTien", monAn.getGiaTien() * monAn.getSoLuong());
        values.put("idDanhMuc", monAn.getIdDanhMuc());
        values.put("tenDanhMuc", monAn.getTenDanhMuc());
        db.insert("DoanhThuTheoThang", null, values);
        db.close();
    }
    public void clearDoanhThuTheoCaLamViec(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM DoanhThu WHERE  id > 0");
        db.close();
    }
    public ArrayList<DoanhThu> getThongKeDoanhThuTheoDanhMuc() {
        ArrayList<DoanhThu> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT dm.tenDanhMuc, SUM(ct.soLuong) AS tongSoLuong, SUM(ct.thanhTien) AS tongTien " +
                "FROM ChiTietDonHang ct " +
                "JOIN MonAn m ON ct.maMon = m.maMon " +
                "JOIN DanhMuc dm ON m.maDanhMuc = dm.maDanhMuc " +
                "GROUP BY dm.maDanhMuc";
        Cursor cursor = db.rawQuery(sql, null);
        int stt = 1;
        while (cursor.moveToNext()) {
            String tenDanhMuc = cursor.getString(cursor.getColumnIndexOrThrow("tenDanhMuc"));
            int soLuong = cursor.getInt(cursor.getColumnIndexOrThrow("tongSoLuong"));
            double tongTien = cursor.getDouble(cursor.getColumnIndexOrThrow("tongTien"));
            list.add(new DoanhThu(stt++, tenDanhMuc, soLuong, tongTien));
        }
        cursor.close();
        return list;
    }

    public ArrayList<DoanhThu> getThongKeDoanhThuTheoMaNhomMon() {
        ArrayList<DoanhThu> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT idDanhMuc, tenDanhMuc, " +
                "SUM(soLuong) as tongSoLuong, " +
                "SUM(tongTien) as tongTien " +
                "FROM DoanhThu " +
                "GROUP BY idDanhMuc, tenDanhMuc";

        Cursor cursor = db.rawQuery(query, null);
        int stt = 1;

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idDanhMuc = cursor.getInt(cursor.getColumnIndexOrThrow("idDanhMuc"));
                String tenDanhMuc = cursor.getString(cursor.getColumnIndexOrThrow("tenDanhMuc"));
                int soLuong = cursor.getInt(cursor.getColumnIndexOrThrow("tongSoLuong"));
                double tongTien = cursor.getDouble(cursor.getColumnIndexOrThrow("tongTien"));

                DoanhThu dt = new DoanhThu();
                dt.setStt(stt++);
                dt.setIdDanhMuc(idDanhMuc);
                dt.setTenDanhMuc(tenDanhMuc);
                dt.setSoLuong(soLuong);
                dt.setTongTien(tongTien);
                list.add(dt);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return list;
    }

    public ArrayList<DoanhThu> getThongKeDoanhThuTheoTenMon() {
        ArrayList<DoanhThu> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT tenMon, " +
                "SUM(soLuong) as tongSoLuong, " +
                "SUM(tongTien) as tongTien " +
                "FROM DoanhThu " +
                "GROUP BY tenMon";

        Cursor cursor = db.rawQuery(query, null);
        int stt = 1;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String tenMon = cursor.getString(cursor.getColumnIndexOrThrow("tenMon"));
                int soLuong = cursor.getInt(cursor.getColumnIndexOrThrow("tongSoLuong"));
                double tongTien = cursor.getDouble(cursor.getColumnIndexOrThrow("tongTien"));

                DoanhThu dt = new DoanhThu();
                dt.setStt(stt++);
                dt.setTenMon(tenMon);
                dt.setSoLuong(soLuong);
                dt.setTongTien(tongTien);
                list.add(dt);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return list;
    }

    public List<PieEntry> getPieEntriesDoanhThuTheoMaNhomMon() {
        List<PieEntry> entries = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT tenDanhMuc, SUM(tongTien) as tongTien " +
                "FROM DoanhThuTheoThang " +
                "GROUP BY idDanhMuc, tenDanhMuc";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String tenDanhMuc = cursor.getString(cursor.getColumnIndexOrThrow("tenDanhMuc"));
                float tongTien = cursor.getFloat(cursor.getColumnIndexOrThrow("tongTien"));

                entries.add(new PieEntry(tongTien, tenDanhMuc));
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return entries;
    }
}

