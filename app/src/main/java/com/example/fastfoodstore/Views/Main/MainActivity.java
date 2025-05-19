package com.example.fastfoodstore.Views.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fastfoodstore.R;
import com.example.fastfoodstore.Views.DanhMuc.QuanLyDanhMuc;
import com.example.fastfoodstore.Views.MonAn.QuanLyMonAn;
import com.example.fastfoodstore.Views.NhanVien.QuanLyNhanVien;
import com.example.fastfoodstore.Views.ThongKe.ThongKeDoanhThu2;
import com.example.fastfoodstore.Views.TrangChu.TrangChu;

public class MainActivity extends AppCompatActivity {
    ImageView img;
    Toolbar toolbar;
    LinearLayout funQuanLyNhanVien,QuanLyMonAn;
    CardView QLDM,TKDT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mapping();
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Ẩn nút back nếu không cần
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });
        QLDM.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuanLyDanhMuc.class);
            startActivity(intent);
        });
        funQuanLyNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuanLyNhanVien.class);
                startActivity(intent);
            }
        });
        QuanLyMonAn.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this,QuanLyMonAn.class);
            startActivity(intent);
        });
        TKDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThongKeDoanhThu2.class);
                startActivity(intent);
            }
        });
        img.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, TrangChu.class);
            startActivity(intent);
        });
    }
    public void mapping(){
        img = findViewById(R.id.goToShop);
        QuanLyMonAn = findViewById(R.id.QuanLyMonAn);
        QLDM = findViewById(R.id.QLDM);
        toolbar = findViewById(R.id.toolbar);
        funQuanLyNhanVien = findViewById(R.id.QLNV);
        TKDT = findViewById(R.id.ThongKeDoanhThu);
    }
}