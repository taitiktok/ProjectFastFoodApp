package com.example.fastfoodstore.Model.DanhMuc;

import java.io.Serializable;

public class DanhMuc implements Serializable {
    private  int maDanhMuc;
    private String tenDanhMuc;
    private byte[] image;
    public DanhMuc() {
    }

    public DanhMuc( String tenDanhMuc,byte[] image) {
        this.image = image;
        this.tenDanhMuc = tenDanhMuc;
    }

    public DanhMuc(int maDanhMuc, String tenDanhMuc, byte[] image) {
        this.maDanhMuc = maDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
        this.image = image;
    }

    public DanhMuc(int maDanhMuc, String tenDanhMuc) {
        this.maDanhMuc = maDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
    }
   public DanhMuc(String tenDanhMuc){
        this.tenDanhMuc = tenDanhMuc;
   }
    public int getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(int maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return tenDanhMuc+"";
    }
}
