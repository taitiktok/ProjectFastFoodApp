package com.example.fastfoodstore.Views.NhanVien;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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

import com.example.fastfoodstore.Model.NhanVien.MySqliteDbNhanVien;
import com.example.fastfoodstore.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddNhanVien extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int REQUEST_IMAGE_PICK = 102;
    ImageView imageDisplay, imageAddByAlbum, imageAddByCamera;
    Toolbar toolbarAddNhanVien;
    EditText txtTenNv, txtDiaChi, txtSoDienThoai;
    Button ThemNv, HuyBo;
    RadioButton txtNam;
    private String imageUriPath = "";
    private Uri imageUriCamera = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_nhan_vien);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mapping();
        setSupportActionBar(toolbarAddNhanVien);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Thêm nhân viên");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbarAddNhanVien.setNavigationOnClickListener(v -> finish());
        ThemNv.setOnClickListener(v -> {
            if (!checkValidate()) return;
            if (imageDisplay.getDrawable() == null) {
                Toast.makeText(this, "Vui lòng chọn ảnh nhân viên", Toast.LENGTH_SHORT).show();
                return;
            }
            Bitmap bitmap = ((BitmapDrawable) imageDisplay.getDrawable()).getBitmap();
            try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                byte[] imageBytes = stream.toByteArray();

                ContentValues values = new ContentValues();
                values.put("TenNhanVien", txtTenNv.getText().toString().trim());
                values.put("GioiTinh", txtNam.isChecked() ? "Nam" : "Nữ");
                values.put("DiaChi", txtDiaChi.getText().toString().trim());
                values.put("SoDienThoai", txtSoDienThoai.getText().toString().trim());
                values.put("Image", imageBytes);

                long result = new MySqliteDbNhanVien(AddNhanVien.this).insert(values);
                if (result != -1) {
                    Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Lỗi xử lý ảnh", Toast.LENGTH_SHORT).show();
            }
        });

        HuyBo.setOnClickListener(v -> finish());
        imageAddByCamera.setOnClickListener(v -> requestCameraPermission());
        imageAddByAlbum.setOnClickListener(v -> openGallery());
    }
    private void mapping() {
        // Đã bỏ txtMaNv
        txtTenNv = findViewById(R.id.txtTenNv);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        txtSoDienThoai = findViewById(R.id.txtSoDienThoai);
        txtNam = findViewById(R.id.txtNam);
        ThemNv = findViewById(R.id.ThemNhanVien);
        HuyBo = findViewById(R.id.HuyBo);
        toolbarAddNhanVien = findViewById(R.id.toolbarAddNhanVien);
        imageAddByAlbum = findViewById(R.id.imageAlbum);
        imageAddByCamera = findViewById(R.id.imageCamera);
        imageDisplay = findViewById(R.id.imageNhanVien);
    }
    private boolean checkValidate() {
        if (txtTenNv.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Tên nhân viên không được để trống", Toast.LENGTH_SHORT).show();
            txtTenNv.requestFocus();
            return false;
        }
        if (txtDiaChi.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Địa chỉ không được để trống", Toast.LENGTH_SHORT).show();
            txtDiaChi.requestFocus();
            return false;
        }
        if (txtSoDienThoai.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Số điện thoại không được để trống", Toast.LENGTH_SHORT).show();
            if(txtSoDienThoai.getText().toString().length() != 10){
                Toast.makeText(this, "Số điện thoại phải đủ 10 chữ số ", Toast.LENGTH_SHORT).show();
            }
            txtSoDienThoai.requestFocus();
            return false;
        }
        return true;
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
        try {
            imageUriCamera = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (imageUriCamera != null) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriCamera);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            } else {
                Toast.makeText(this, "Không thể mở camera", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi khi mở camera", Toast.LENGTH_SHORT).show();
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Bạn cần cấp quyền camera để sử dụng chức năng này", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_IMAGE_PICK) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "Bạn cần cấp quyền truy cập bộ nhớ để chọn ảnh", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && imageUriCamera != null) {
                imageDisplay.setImageURI(imageUriCamera);
                imageUriPath = imageUriCamera.toString();
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    imageUriPath = selectedImageUri.toString();
                    imageDisplay.setImageURI(selectedImageUri);
                }
            }
        }
    }

}