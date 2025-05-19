package com.example.fastfoodstore.Views.Account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fastfoodstore.Model.Account.AccountAdmin;
import com.example.fastfoodstore.Model.Account.MySqliteDbAccount;
import com.example.fastfoodstore.Model.NhanVien.MySqliteDbNhanVien;
import com.example.fastfoodstore.Model.NhanVien.NhanVien;
import com.example.fastfoodstore.R;
import com.example.fastfoodstore.Views.Main.MainActivity;
import com.example.fastfoodstore.Views.TrangChu.TrangChu;

import java.util.List;

public class Login extends AppCompatActivity {

    private TextView signup;
    private Switch switchRole;
    private EditText txtUserName, txtPassword;
    private Button buttonLogin;
    private boolean isAdmin = false;
    private LoadingDiglog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupListeners();
    }

    private void initViews() {
        signup = findViewById(R.id.signup);
        switchRole = findViewById(R.id.switchRole);
        txtUserName = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassWord);
        buttonLogin = findViewById(R.id.buttonDangNhap);
        loadingDialog = new LoadingDiglog(this);
    }

    private void setupListeners() {
        signup.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, SignIn.class));
        });

        switchRole.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isAdmin = isChecked;
            switchRole.setText(isChecked ? "Admin" : "Nhân viên");
        });

        buttonLogin.setOnClickListener(v -> {
            String username = txtUserName.getText().toString().trim();
            String password = txtPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (isAdmin) {
                loadingDialog.startLoadingDialog();
                new Handler().postDelayed(() -> {
                    loginAsAdmin(username, password);
                    loadingDialog.dismissDialog();
                }, 2000);
            } else {
                loadingDialog.startLoadingDialog();
                new Handler().postDelayed(() -> {
                    loginAsEmployee(username, password);
                    loadingDialog.dismissDialog();
                }, 2000);
            }
        });
    }

    private void loginAsAdmin(String username, String password) {
        MySqliteDbAccount db = new MySqliteDbAccount(this);
        List<AccountAdmin> admins = db.getAllAccountAdmin();

        for (AccountAdmin admin : admins) {
            if (admin.getUserNameAdmin().equals(username) && admin.getPassWordAdmin().equals(password)) {
                startActivity(new Intent(this, MainActivity.class));
                Toast.makeText(this, "Đăng nhập thành công (Admin)!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Toast.makeText(this, "Sai tài khoản hoặc mật khẩu Admin!", Toast.LENGTH_SHORT).show();
    }

    private void loginAsEmployee(String username, String password) {
        MySqliteDbNhanVien db = new MySqliteDbNhanVien(this);
        List<NhanVien> nhanViens = db.getAllNhanVienById();

        for (NhanVien nv : nhanViens) {
            if (nv.getTenNhanVien().equals(username) && nv.getSoDienThoai().equals(password)) {
                saveLoginState(nv.getSoDienThoai());
                startActivity(new Intent(this, TrangChu.class));
                Toast.makeText(this, "Đăng nhập thành công (Nhân viên)!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Toast.makeText(this, "Sai tên hoặc số điện thoại nhân viên!", Toast.LENGTH_SHORT).show();
    }

    private void saveLoginState(String phoneNumber) {
        SharedPreferences sharedPreferences = getSharedPreferences("TTNV", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Sdt", phoneNumber);
        editor.apply();
    }
}
