package com.example.fastfoodstore.Views.DanhMuc;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fastfoodstore.Model.DanhMuc.DanhMuc;
import com.example.fastfoodstore.Model.DanhMuc.MySqliteDBDanhMuc;
import com.example.fastfoodstore.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Edit_Nhom_Mon extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int REQUEST_IMAGE_PICK = 102;

    TextView tvIdNhomMon;
    EditText txtTenNhomMon;
    Button themNhomMon, Huybo;
    ImageView imageDisplay, imageAddByAlbum, imageAddByCamera;

    private Uri imageUriCamera = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_nhom_mon);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mapping();

        Intent intent = getIntent();
        int idNhomMon = intent.getIntExtra("IdNhomMonEdit", -1);
        if (idNhomMon == -1) {
            Toast.makeText(this, "Không nhận được ID nhóm món", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvIdNhomMon.setText(String.valueOf(idNhomMon));
        loadDanhMucData(idNhomMon);

        imageAddByCamera.setOnClickListener(v -> requestCameraPermission());
        imageAddByAlbum.setOnClickListener(v -> openGallery());

        themNhomMon.setOnClickListener(v -> {
            String tenNhomMon = txtTenNhomMon.getText().toString().trim();
            if (tenNhomMon.isEmpty()) {
                Toast.makeText(this, "Tên nhóm món không được để trống", Toast.LENGTH_SHORT).show();
                return;
            }

            Bitmap bitmap = ((BitmapDrawable) imageDisplay.getDrawable()).getBitmap();
            try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                byte[] imageBytes = stream.toByteArray();

                MySqliteDBDanhMuc db = new MySqliteDBDanhMuc(this);
                db.updateDanhMuc(idNhomMon, tenNhomMon, imageBytes);

                Toast.makeText(this, "Cập nhật danh mục thành công", Toast.LENGTH_SHORT).show();
                finish();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Lỗi xử lý ảnh", Toast.LENGTH_SHORT).show();
            }
        });

        Huybo.setOnClickListener(v -> finish());
    }

    private void mapping() {
        tvIdNhomMon = findViewById(R.id.tvNhomMon);
        txtTenNhomMon = findViewById(R.id.txtTenNhomMon);
        themNhomMon = findViewById(R.id.buttonThemNhomMon);
        Huybo = findViewById(R.id.HuyBoNhomMon);
        imageAddByAlbum = findViewById(R.id.imageAlbum);
        imageAddByCamera = findViewById(R.id.imageCamera);
        imageDisplay = findViewById(R.id.imageNhomMon);
    }

    private void loadDanhMucData(int id) {
        MySqliteDBDanhMuc db = new MySqliteDBDanhMuc(this);
        DanhMuc danhMuc = db.getNhomMonById(id);

        if (danhMuc != null) {
            txtTenNhomMon.setText(danhMuc.getTenDanhMuc());
            byte[] imageBytes = danhMuc.getImage();
            if (imageBytes != null && imageBytes.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                imageDisplay.setImageBitmap(bitmap);
            }
        } else {
            Toast.makeText(this, "Không tìm thấy danh mục", Toast.LENGTH_SHORT).show();
        }
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
                Toast.makeText(this, "Cần cấp quyền camera để sử dụng chức năng này", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_IMAGE_PICK) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "Cần cấp quyền đọc bộ nhớ để chọn ảnh", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && imageUriCamera != null) {
                imageDisplay.setImageURI(imageUriCamera);
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    imageDisplay.setImageURI(selectedImageUri);
                }
            }
        }
    }
}
