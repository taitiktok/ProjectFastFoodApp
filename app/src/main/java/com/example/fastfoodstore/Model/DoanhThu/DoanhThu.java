package com.example.fastfoodstore.Model.DoanhThu;

import java.io.Serializable;

public class DoanhThu implements Serializable {
    private int id;
    private int maMon;
    private String tenMon;
    private int soLuong;
    private double giaTien;
    private double tongTien;
    private int idDanhMuc;
    private String tenDanhMuc;
    private int Stt;
    public DoanhThu() {
    }
    public DoanhThu(int id, String tenMon, int soLuong,double tongTien) {
        this.id = id;
        this.tenMon = tenMon;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
    }

    public DoanhThu(int id, int maMon, String tenMon, int soLuong,
                    double giaTien, double tongTien,
                    int idDanhMuc, String tenDanhMuc) {
        this.id = id;
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.tongTien = tongTien;
        this.idDanhMuc = idDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
    }

    // Nếu cần khởi tạo khi chưa có id (insert mới)
    public DoanhThu(int maMon, String tenMon, int soLuong,
                    double giaTien, double tongTien,
                    int idDanhMuc, String tenDanhMuc) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.tongTien = tongTien;
        this.idDanhMuc = idDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getMaMon() {
        return maMon;
    }
    public void setMaMon(int maMon) {
        this.maMon = maMon;
    }

    public String getTenMon() {
        return tenMon;
    }
    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public int getSoLuong() {
        return soLuong;
    }
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaTien() {
        return giaTien;
    }
    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }

    public double getTongTien() {
        return tongTien;
    }
    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public int getIdDanhMuc() {
        return idDanhMuc;
    }
    public void setIdDanhMuc(int idDanhMuc) {
        this.idDanhMuc = idDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }
    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public int getStt() {
        return Stt;
    }

    public void setStt(int stt) {
        Stt = stt;
    }

    @Override
    public String toString() {
        return "DoanhThu{" +
                "id=" + id +
                ", maMon=" + maMon +
                ", tenMon='" + tenMon + '\'' +
                ", soLuong=" + soLuong +
                ", giaTien=" + giaTien +
                ", tongTien=" + tongTien +
                ", idDanhMuc=" + idDanhMuc +
                ", tenDanhMuc='" + tenDanhMuc + '\'' +
                '}';
    }
}
