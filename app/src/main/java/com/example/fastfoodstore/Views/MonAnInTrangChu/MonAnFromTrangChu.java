package com.example.fastfoodstore.Views.MonAnInTrangChu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodstore.Model.DanhMuc.DanhMuc;
import com.example.fastfoodstore.Model.DanhMuc.MySqliteDBDanhMuc;
import com.example.fastfoodstore.Model.MonAn.MonAn;
import com.example.fastfoodstore.Model.MonAnInTrangChu.RecyleViewMonAnInTrangChu;
import com.example.fastfoodstore.R;

import java.util.ArrayList;

public class MonAnFromTrangChu extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<MonAn> listMonAnByNhomMon;
    RecyleViewMonAnInTrangChu recycleView;
    int maNhomMonCanHienThi ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mon_an_from_trang_chu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mapping();
        Intent intent = getIntent();
        maNhomMonCanHienThi = intent.getIntExtra("IdNhomMonBan",-1);
        if(maNhomMonCanHienThi == -1){
            Log.d("Khong nhan dc ma ","hihi");
        }
        else{
            Log.d("Nhan duoc ma ", ""+maNhomMonCanHienThi);
            setAdapter(maNhomMonCanHienThi);
        }
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getTenNhomMon(maNhomMonCanHienThi));
        }
        toolbar.setNavigationOnClickListener(v->{
            finish();
        });
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return super.onContextItemSelected(item);
    }

    public String getTenNhomMon(int id){
        MySqliteDBDanhMuc db = new MySqliteDBDanhMuc(this);
        DanhMuc dm = db.getNhomMonById(id);
        return dm.getTenDanhMuc();
    }

    public void mapping(){
        toolbar = findViewById(R.id.toolbarBanMonAn);
        recyclerView = findViewById(R.id.recycleViewBanMonAn);
    }
    public void setAdapter(int id){
        MySqliteDBDanhMuc db = new MySqliteDBDanhMuc(this);
        listMonAnByNhomMon = db.getMonAnTheoDanhMuc(id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recycleView = new RecyleViewMonAnInTrangChu( this,listMonAnByNhomMon);
        recyclerView.setAdapter(recycleView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdapter(maNhomMonCanHienThi);
    }
}