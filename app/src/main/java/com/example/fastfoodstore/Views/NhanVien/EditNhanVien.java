package com.example.fastfoodstore.Views.NhanVien;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
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
import com.example.fastfoodstore.Model.NhanVien.NhanVien;
import com.example.fastfoodstore.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditNhanVien extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int REQUEST_IMAGE_PICK = 102;
    TextView tvIdNv;
    Toolbar toolbarAddNhanVien;
    EditText txtTenNv, txtDiaChi, txtSoDienThoai; // Đã bỏ txtMaNv
    Button SaveNv, HuyBo;
    RadioButton txtNam,txtNu;
    ImageView imageDisplay, imageAddByAlbum, imageAddByCamera;
    private String imageUriPath = "";      // Lưu Uri ảnh dạng chuỗi
    private Uri imageUriCamera = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_nhan_vien);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mapping();
        setSupportActionBar(toolbarAddNhanVien);
        getDataNhanVienEdit();
        imageAddByCamera.setOnClickListener(v -> requestCameraPermission());
        imageAddByAlbum.setOnClickListener(v -> openGallery());
        Log.d("OnStart dang dc goi ","OnStartDang");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Chỉnh sửa thông tin nhân viên");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getDataNhanVienEdit();
        toolbarAddNhanVien.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        HuyBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SaveNv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkValidate()) return;
                if (imageDisplay.getDrawable() == null) {
                    Toast.makeText(EditNhanVien.this, "Vui lòng chọn ảnh nhân viên", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Xử lý ảnh
                Bitmap bitmap = ((BitmapDrawable) imageDisplay.getDrawable()).getBitmap();
                try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                    byte[] imageBytes = stream.toByteArray();

                    MySqliteDbNhanVien db = new MySqliteDbNhanVien(EditNhanVien.this);
                    String gioiTinh = " ";
                    int id = Integer.valueOf(tvIdNv.getText().toString());
                    String tenNhanVien = txtTenNv.getText().toString();
                    if(txtNam.isChecked()){
                        gioiTinh = "Nam";
                    }
                    if(txtNu.isChecked()){
                        gioiTinh = "Nữ";
                    }
                    String diaChi = txtDiaChi.getText().toString();
                    String sdt = txtSoDienThoai.getText().toString();
                    //HamUpDateDuLieu
                    db.updateData(id,tenNhanVien,gioiTinh,diaChi,sdt,imageBytes);
                    Toast.makeText(EditNhanVien.this, "Cap nhat du lieu thanh cong", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(EditNhanVien.this, "Lỗi xử lý ảnh", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void mapping(){
        txtNu = findViewById(R.id.txtNu);
        txtTenNv = findViewById(R.id.txtTenNv);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        txtSoDienThoai = findViewById(R.id.txtSoDienThoai);
        txtNam = findViewById(R.id.txtNam);
        SaveNv = findViewById(R.id.ThemNhanVien);
        HuyBo = findViewById(R.id.HuyBo);
        toolbarAddNhanVien = findViewById(R.id.toolbarAddNhanVien);
        imageAddByAlbum = findViewById(R.id.imageAlbum);
        imageAddByCamera = findViewById(R.id.imageCamera);
        imageDisplay = findViewById(R.id.imageNhanVien);
        tvIdNv = findViewById(R.id.tvNv);
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
            if(txtSoDienThoai.getText().toString().length() != 10 ){
                Toast.makeText(this, "Số điện thoại phải đủ 10 chữ số ! ", Toast.LENGTH_SHORT).show();
            }
            txtSoDienThoai.requestFocus();
            return false;
        }
        return true;
    }
    public void getDataNhanVienEdit(){
        Intent intent = getIntent();
        int id = intent.getIntExtra("idFix", -1);
        if (id == -1) {
            Log.d("Kh","Khong nhan duoc du lieu");
        }
        else{
            Log.d("Nhan dc du lieu",""+id);
            MySqliteDbNhanVien db = new MySqliteDbNhanVien(EditNhanVien.this);
        }
        MySqliteDbNhanVien db = new MySqliteDbNhanVien(EditNhanVien.this);
        NhanVien nv = db.getNhanVienById(id);
        if (nv != null) {
            tvIdNv.setText(""+nv.getMaNhanVien());
            txtTenNv.setText(nv.getTenNhanVien());
            txtDiaChi.setText(nv.getDiaChi());
            txtSoDienThoai.setText(nv.getSoDienThoai());
            if (nv.getGioiTinh().equalsIgnoreCase("Nam")) {
                txtNam.setChecked(true);
            } else if (nv.getGioiTinh().equalsIgnoreCase("Nữ")) {
                txtNu.setChecked(true);
            }
            byte[] imageBytes = nv.getImageNhanVien();
            if (imageBytes != null && imageBytes.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                imageDisplay.setImageBitmap(bitmap);
            } else {
                imageDisplay.setImageResource(R.drawable.image_nv);
            }
        } else {
            Toast.makeText(this, "Không tìm thấy nhân viên với ID này", Toast.LENGTH_SHORT).show();
        }
    }

}