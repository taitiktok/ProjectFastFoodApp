package com.example.fastfoodstore.Model.NhanVien;

import java.io.Serializable;

public class NhanVien implements Serializable {
    private int maNhanVien;
    private String tenNhanVien;
    private String gioiTinh;
    private String diaChi;
    private String soDienThoai;
    private byte[] imageNhanVien;
    private Boolean isExpanded = false;
    public NhanVien() {
    }

    public NhanVien(int maNhanVien, String tenNhanVien, String gioiTinh, String diaChi, String soDienThoai, byte[] imageNhanVien) {
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.imageNhanVien = imageNhanVien;
    }

    public int getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public byte[] getImageNhanVien() {
        return imageNhanVien;
    }

    public void setImageNhanVien(byte[] imageNhanVien) {
        this.imageNhanVien = imageNhanVien;
    }

    public Boolean getExpanded() {
        return isExpanded;
    }

    public void setExpanded(Boolean expanded) {
        isExpanded = expanded;
    }

    @Override
    public String toString() {
        return "NhanVien{}";
    }
}
