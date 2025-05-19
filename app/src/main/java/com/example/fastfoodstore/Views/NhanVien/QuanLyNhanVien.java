package com.example.fastfoodstore.Views.NhanVien;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fastfoodstore.Model.NhanVien.MySqliteDbNhanVien;
import com.example.fastfoodstore.Model.NhanVien.NhanVien;
import com.example.fastfoodstore.Model.NhanVien.RecycleViewNhanVien;
import com.example.fastfoodstore.R;
import com.example.fastfoodstore.Views.Main.MainActivity;

import java.util.ArrayList;

public class QuanLyNhanVien extends AppCompatActivity {
    Toolbar toolbarQLNV;
    ImageView addNV;
    ArrayList<NhanVien> listNhanVien;
    RecyclerView recyclerView;
    RecycleViewNhanVien recycleViewNhanVien;
    EditText txtSearchNv;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quan_ly_nhan_vien);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mapping();
        initData();
        setupToolbar();
        setupRecyclerView();
        setupListeners();

        txtSearchNv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                recycleViewNhanVien.filter(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

    }

    private void mapping() {
        txtSearchNv = findViewById(R.id.txtSearch);
        addNV = findViewById(R.id.add);
        toolbarQLNV = findViewById(R.id.toolbarQuanLyNhanVien);
        recyclerView = findViewById(R.id.recycleViewQlnv);
        listNhanVien = new ArrayList<>();
    }
    private void initData() {
        loadDataNhanVien();
    }
    private void setupToolbar() {
        setSupportActionBar(toolbarQLNV);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Quản lý nhân viên");
        }
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Thêm LayoutManager
        recycleViewNhanVien = new RecycleViewNhanVien(this, listNhanVien);
        recyclerView.setAdapter(recycleViewNhanVien);
    }

    private void setupListeners() {
        toolbarQLNV.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(QuanLyNhanVien.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        addNV.setOnClickListener(v -> {
            Intent intentAddNhanVien = new Intent(QuanLyNhanVien.this, AddNhanVien.class);
            startActivity(intentAddNhanVien);
        });
    }

    public void loadDataNhanVien() {
        listNhanVien.clear();
        MySqliteDbNhanVien db = new MySqliteDbNhanVien(QuanLyNhanVien.this);
        String query = "SELECT * FROM NHANVIEN";
        Cursor cursor = db.rawQuerys(query);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int maNv = cursor.getInt(0);
                    String tenNv = cursor.getString(1);
                    String gioiTinh = cursor.getString(2);
                    String diaChi = cursor.getString(3);
                    String soDienThoai = cursor.getString(4);
                    byte[] image = cursor.getBlob(5);
                    listNhanVien.add(new NhanVien(maNv, tenNv, gioiTinh, diaChi, soDienThoai, image));
                } while (cursor.moveToNext());
            } else {
                Toast.makeText(this, "Không có dữ liệu nhân viên", Toast.LENGTH_SHORT).show();
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

            if (recycleViewNhanVien != null) {
                recycleViewNhanVien.setData(listNhanVien);
            }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataNhanVien();
    }
}