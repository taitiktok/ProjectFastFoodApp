package com.example.fastfoodstore.Views.TrangChu;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fastfoodstore.R;
import com.example.fastfoodstore.ViewModels.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class TrangChu extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        mapping();
        ViewCompat.setOnApplyWindowInsetsListener(bottomNavigationView, (v, insets) -> {
            int bottomInset = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom;
            v.setPadding(0, 0, 0, bottomInset);
            return insets;
        });
        setViewPager();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.TrangChu) {
                    viewPager.setCurrentItem(0);
                } else if (id == R.id.GioHang) {
                    viewPager.setCurrentItem(1);
                } else if (id == R.id.DoanhThu) {
                    viewPager.setCurrentItem(2);
                } else if (id == R.id.TaiKhoan) {
                    viewPager.setCurrentItem(3);
                } else {
                    return false;
                }
                return true;
            }
        });


        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0) {
                    bottomNavigationView.setSelectedItemId(R.id.TrangChu);
                } else if (position == 1) {
                    bottomNavigationView.setSelectedItemId(R.id.GioHang);
                } else if (position == 2) {
                    bottomNavigationView.setSelectedItemId(R.id.DoanhThu);
                } else if (position == 3) {
                    bottomNavigationView.setSelectedItemId(R.id.TaiKhoan);
                }
            }
        });


        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.TrangChu);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.GioHang);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.DoanhThu);
                        break;
                    case 3:
                        bottomNavigationView.setSelectedItemId(R.id.TaiKhoan);
                        break;
                }
            }
        });
    }

    public void setViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
    }

    public void mapping() {
        viewPager = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }
}
