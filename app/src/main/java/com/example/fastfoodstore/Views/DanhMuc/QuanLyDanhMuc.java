package com.example.fastfoodstore.Views.DanhMuc;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodstore.Model.DanhMuc.DanhMuc;
import com.example.fastfoodstore.Model.DanhMuc.MySqliteDBDanhMuc;
import com.example.fastfoodstore.Model.DanhMuc.RecycleViewDanhMuc;
import com.example.fastfoodstore.R;

import java.util.ArrayList;

public class QuanLyDanhMuc extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar;
    ImageView add, edit;
    Button buttonSave, buttonCancel;
    AlertDialog diglog;
    View viewDigLog;
    EditText txtTenNhomMon;
    RecyclerView recycleViewDanhMuc;
    EditText txtSearchDm;
    RecycleViewDanhMuc recycleViewDM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Quản lý danh mục ");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quan_ly_danh_muc);
        mapping();
        setupToolbar();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SetAdapter();
        txtSearchDm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                recycleViewDM.filter(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        toolbar.setNavigationOnClickListener(v->{finish();});
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanLyDanhMuc.this, add_nhom_mon1.class);
                startActivity(intent);
            }
        });
    }

    public void SetAdapter(){
        MySqliteDBDanhMuc db = new MySqliteDBDanhMuc(QuanLyDanhMuc.this);
        ArrayList<DanhMuc> listDanhMuc = db.getDanhMuc();
        recycleViewDanhMuc.setLayoutManager(new LinearLayoutManager(this));
        recycleViewDM = new RecycleViewDanhMuc(this, listDanhMuc);
        recycleViewDanhMuc.setAdapter(recycleViewDM);
    }


    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Quản lý danh mục ");
        }
    }

    public void mapping() {
        add = findViewById(R.id.addNhomMon);
        toolbar = findViewById(R.id.toolbarQuanLyDanhMuc);
        recycleViewDanhMuc = findViewById(R.id.recycleQLDM);
        txtSearchDm = findViewById(R.id.txtSearch);
    }
    public void loadData(){
        MySqliteDBDanhMuc db = new MySqliteDBDanhMuc(QuanLyDanhMuc.this);
        ArrayList<DanhMuc> list = db.getDanhMuc();
        recycleViewDanhMuc.setLayoutManager(new LinearLayoutManager(this));
        recycleViewDM = new RecycleViewDanhMuc(this, list); // Cập nhật lại adapter chính
        recycleViewDanhMuc.setAdapter(recycleViewDM);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SetAdapter();
    }
}

