package com.example.fastfoodstore.Model.MonAn;


import java.io.Serializable;
import java.sql.Blob;
import java.util.Arrays;

public class MonAn implements Serializable {
     private int maMonAn;
     private String tenMonAn;
     private double giaTien;
     private String moTaChiTiet;
     private byte[] imageMonAn;
     private int maNhomMonAn;
     private String tenNhomMon;
    private Boolean isExpanded = false;
    private int SoLuong;
    public MonAn() {
    }

    public MonAn(int maMonAn, String tenMonAn, double giaTien, String moTaChiTiet, byte[] imageMonAn, String tenNhomMon) {
        this.maMonAn = maMonAn;
        this.tenMonAn = tenMonAn;
        this.giaTien = giaTien;
        this.moTaChiTiet = moTaChiTiet;
        this.imageMonAn = imageMonAn;
        this.tenNhomMon = tenNhomMon;
    }
    public MonAn(int maMonAn, String tenMonAn, double giaTien, String moTaChiTiet, byte[] imageMonAn, int maNhomMonAn, String tenNhomMon) {
        this.maMonAn = maMonAn;
        this.tenMonAn = tenMonAn;
        this.giaTien = giaTien;
        this.moTaChiTiet = moTaChiTiet;
        this.imageMonAn = imageMonAn;
        this.maNhomMonAn = maNhomMonAn;
        this.tenNhomMon = tenNhomMon;
    }

    public MonAn(int maMonAn, String tenMonAn, double giaTien, String moTaChiTiet, byte[] imageMonAn, int maNhomMonAn) {
        this.maMonAn = maMonAn;
        this.tenMonAn = tenMonAn;
        this.giaTien = giaTien;
        this.moTaChiTiet = moTaChiTiet;
        this.imageMonAn = imageMonAn;
        this.maNhomMonAn = maNhomMonAn;
    }

    public MonAn(int maMonAn, String tenMonAn, double giaTien, byte[] imageMonAn, int maNhomMonAn, int soLuong,String tenDanhMuc) {
        this.maMonAn = maMonAn;
        this.tenMonAn = tenMonAn;
        this.giaTien = giaTien;
        this.imageMonAn = imageMonAn;
        this.maNhomMonAn = maNhomMonAn;
        this.SoLuong = soLuong;
        this.tenNhomMon = tenDanhMuc;
    }

    public MonAn(int maMonAn, String tenMonAn, double giaTien, byte[] imageMonAn, int maNhomMonAn,String moTaChiTiet) {
        this.maMonAn = maMonAn;
        this.tenMonAn = tenMonAn;
        this.giaTien = giaTien;
        this.imageMonAn = imageMonAn;
        this.tenNhomMon = tenNhomMon;
        this.maNhomMonAn = maNhomMonAn;
        this.moTaChiTiet = moTaChiTiet;
    }

    public MonAn(String tenMonAn, double giaTien, String moTaChiTiet, byte[] imageMonAn, int maNhomMonAn) {
        this.tenMonAn = tenMonAn;
        this.giaTien = giaTien;
        this.moTaChiTiet = moTaChiTiet;
        this.imageMonAn = imageMonAn;
        this.maNhomMonAn = maNhomMonAn;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public int getMaMonAn() {
        return maMonAn;
    }

    public void setMaMonAn(int maMonAn) {
        this.maMonAn = maMonAn;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public String getTenNhomMon() {
        return tenNhomMon;
    }

    public void setTenNhomMon(String tenNhomMon) {
        this.tenNhomMon = tenNhomMon;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }

    public String getMoTaChiTiet() {
        return moTaChiTiet;
    }

    public void setMoTaChiTiet(String moTaChiTiet) {
        this.moTaChiTiet = moTaChiTiet;
    }

    public byte[] getImageMonAn() {
        return imageMonAn;
    }

    public void setImageMonAn(byte[] imageMonAn) {
        this.imageMonAn = imageMonAn;
    }

    public int getMaNhomMonAn() {
        return maNhomMonAn;
    }

    public void setMaNhomMonAn(int maNhomMonAn) {
        this.maNhomMonAn = maNhomMonAn;
    }

    public Boolean getExpanded() {
        return isExpanded;
    }

    public void setExpanded(Boolean expanded) {
        isExpanded = expanded;
    }

    @Override
    public String toString() {
        return "MonAn{" +
                "SoLuong=" + SoLuong +
                ", tenNhomMon='" + tenNhomMon + '\'' +
                ", maNhomMonAn=" + maNhomMonAn +
//                ", imageMonAn=" + Arrays.toString(imageMonAn) +
                ", giaTien=" + giaTien +
                ", tenMonAn='" + tenMonAn + '\'' +
                ", maMonAn=" + maMonAn +
                '}';
    }
}
