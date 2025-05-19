package com.example.fastfoodstore.Views.MonAnInTrangChu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fastfoodstore.Model.DanhMuc.MySqliteDBDanhMuc;
import com.example.fastfoodstore.Model.MonAn.MonAn;
import com.example.fastfoodstore.R;

import java.io.ByteArrayOutputStream;

public class XemChiTietMonAn1 extends AppCompatActivity {
    ImageView imageChiTietMonAn ,ThemSoLuong,GiamSoLuong;
    TextView giaMonAnChiTiet,tenMonAnChiTiet,moTaMonAnChiTiet,soLuong;
    Button ThemMonAnVaoGioHang;
    int maNhomMon ;
    String tenNhomMon;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_xem_chi_tiet_mon_an1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mapping();
        Intent intent = getIntent();
        int maMonChiTiet = intent.getIntExtra("IdMonAnXemChiTiet",-1);
        MySqliteDBDanhMuc db = new MySqliteDBDanhMuc(this);
        MonAn monAn = db.getDataMonAnById(maMonChiTiet);
        maNhomMon = monAn.getMaNhomMonAn();
        tenNhomMon = monAn.getTenNhomMon();
        tenMonAnChiTiet.setText(monAn.getTenMonAn());
        giaMonAnChiTiet.setText(String.valueOf(monAn.getGiaTien()));
        moTaMonAnChiTiet.setText(monAn.getMoTaChiTiet());
        byte[] imageBytes = monAn.getImageMonAn();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
        imageChiTietMonAn.setImageBitmap(bitmap);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Xem chi tiết món ăn");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v->{
            finish();
        });

        ThemSoLuong.setOnClickListener(v->{
            int SlCustom = Integer.valueOf(soLuong.getText().toString());
            int tangSL = tangSoLuong(SlCustom);
            soLuong.setText(String.valueOf(tangSL));
        });

        GiamSoLuong.setOnClickListener(v->{
            int SlCustom = Integer.valueOf(soLuong.getText().toString());
            int giamSL = giamSoLuong(SlCustom);
            soLuong.setText(String.valueOf(giamSL));
        });

        ThemMonAnVaoGioHang.setOnClickListener(v->{
            Intent intent1 = getIntent();
            int maMonAn = intent.getIntExtra("IdMonAnXemChiTiet",-1);
            Bitmap bitmap2 = ((BitmapDrawable) imageChiTietMonAn.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap2.compress(Bitmap.CompressFormat.JPEG, 80, stream);
             byte[] imageChiTietMonAn = stream.toByteArray();
             MySqliteDBDanhMuc db1 = new MySqliteDBDanhMuc(this);
             int MMA = maMonAn;
             int MaNhomMon = maNhomMon;
             String TM = tenMonAnChiTiet.getText().toString();
             double GT = Double.parseDouble(giaMonAnChiTiet.getText().toString());
             int SL = Integer.valueOf(soLuong.getText().toString());
             int MDM = Integer.valueOf(monAn.getMaNhomMonAn());
             String tenNM = tenNhomMon;
             MonAn monAn1 = new MonAn(MMA,TM,GT,imageChiTietMonAn,maNhomMon,SL,tenNM);
             db.themVaoGioHang(monAn1,SL);
             Toast.makeText(this, "Sản phẩm vừa được thêm vào giỏ hàng ", Toast.LENGTH_SHORT).show();
        });
    }

    public void mapping(){
        ThemSoLuong = findViewById(R.id.Add);
        GiamSoLuong = findViewById(R.id.Tru);
        imageChiTietMonAn = findViewById(R.id.image_ChiTietMonAn);
        tenMonAnChiTiet = findViewById(R.id.tenChiTietMonAn);
        giaMonAnChiTiet = findViewById(R.id.giaChiTietMonAn);
        moTaMonAnChiTiet = findViewById(R.id.MoTaChiTietMonAn);
        soLuong = findViewById(R.id.SoLuongChiTietMonAn);
        ThemMonAnVaoGioHang = findViewById(R.id.ThemMonAnVaoGioHang);
        toolbar = findViewById(R.id.toolbarXemChiTietMonAn);
    }

    public int tangSoLuong(int soLuong ){
        soLuong = soLuong + 1;
        return soLuong;
    }

    public int giamSoLuong(int soLuong){
        if(soLuong > 0) {
            soLuong = soLuong - 1;
        }
        return soLuong;
    }
}