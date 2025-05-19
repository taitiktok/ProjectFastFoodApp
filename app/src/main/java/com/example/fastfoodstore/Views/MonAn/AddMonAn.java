package com.example.fastfoodstore.Views.MonAn;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fastfoodstore.Model.DanhMuc.DanhMuc;
import com.example.fastfoodstore.Model.DanhMuc.MySqliteDBDanhMuc;
import com.example.fastfoodstore.Model.MonAn.MonAn;
import com.example.fastfoodstore.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AddMonAn extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int REQUEST_IMAGE_PICK = 102;

    ImageView imgMonAn, imgFromAlbum, imgFromCamera;
    Spinner spinnerNhomMon;
    Toolbar toolbar;
    Button btnAdd, btnCancel;
    EditText edtTenMonAn, edtGiaTien, edtMoTa;

    private Uri imageUriCamera = null;
    private String imageUriPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_mon_an);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mapping();
        setupToolbar();
        loadDanhMucToSpinner();

        btnCancel.setOnClickListener(v -> finish());

        btnAdd.setOnClickListener(v -> handleAddMonAn());
        imgFromCamera.setOnClickListener(v -> requestCameraPermission());
        imgFromAlbum.setOnClickListener(v -> openGallery());
    }

    private void mapping() {
        toolbar = findViewById(R.id.toolbarAddMonAn);
        spinnerNhomMon = findViewById(R.id.spinnerNhomMon);
        btnAdd = findViewById(R.id.ThemMonAn);
        btnCancel = findViewById(R.id.HuyBo);
        imgMonAn = findViewById(R.id.imageMonAn);
        imgFromAlbum = findViewById(R.id.imageAlbum);
        imgFromCamera = findViewById(R.id.imageCamera);
        edtTenMonAn = findViewById(R.id.txtTenMonAn);
        edtGiaTien = findViewById(R.id.txtGiaTien);
        edtMoTa = findViewById(R.id.txtMoTaChiTiet);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Thêm món ăn");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void loadDanhMucToSpinner() {
        MySqliteDBDanhMuc db = new MySqliteDBDanhMuc(this);
        ArrayList<DanhMuc> danhMucList = db.getDanhMuc();

        if (danhMucList.isEmpty()) {
            Toast.makeText(this, "Chưa có danh mục món ăn", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayAdapter<DanhMuc> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, danhMucList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNhomMon.setAdapter(adapter);
    }

    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
        imageUriCamera = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        if (imageUriCamera != null) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriCamera);
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(this, "Không thể mở camera", Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_IMAGE_PICK);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_IMAGE_PICK);
        }
    }

    private void handleAddMonAn() {
        if (spinnerNhomMon.getSelectedItem() == null) {
            Toast.makeText(this, "Vui lòng chọn danh mục", Toast.LENGTH_SHORT).show();
            return;
        }

        String tenMon = edtTenMonAn.getText().toString().trim();
        String giaTienStr = edtGiaTien.getText().toString().trim();
        String moTa = edtMoTa.getText().toString().trim();

        if (tenMon.isEmpty() || giaTienStr.isEmpty() || moTa.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        double giaTien;
        try {
            giaTien = Double.parseDouble(giaTienStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Giá tiền không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (imgMonAn.getDrawable() == null) {
            Toast.makeText(this, "Vui lòng chọn ảnh món ăn", Toast.LENGTH_SHORT).show();
            return;
        }

        Bitmap bitmap = ((BitmapDrawable) imgMonAn.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] imageBytes = stream.toByteArray();
        int maDanhMuc = ((DanhMuc) spinnerNhomMon.getSelectedItem()).getMaDanhMuc();
        MonAn monAn = new MonAn(tenMon, giaTien, moTa, imageBytes, maDanhMuc);
        MySqliteDBDanhMuc db = new MySqliteDBDanhMuc(this);
        db.insertMonAn(monAn);
        Toast.makeText(this, "Thêm món ăn thành công", Toast.LENGTH_SHORT).show();
        Log.d("Thong tin mon an ", monAn.toString());
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA_PERMISSION && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else if (requestCode == REQUEST_IMAGE_PICK && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            Toast.makeText(this, "Quyền bị từ chối", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && imageUriCamera != null) {
                imgMonAn.setImageURI(imageUriCamera);
                imageUriPath = imageUriCamera.toString();
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null && data.getData() != null) {
                Uri selectedImage = data.getData();
                imageUriPath = selectedImage.toString();
                imgMonAn.setImageURI(selectedImage);
            }
        }
    }
}
