package com.example.fastfoodstore.Views.Account;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fastfoodstore.Model.Account.MySqliteDbAccount;
import com.example.fastfoodstore.Model.DanhMuc.MySqliteDBDanhMuc;
import com.example.fastfoodstore.R;

public class SignIn extends AppCompatActivity {
    EditText txtUserAdmin,passWordAdmin,confirmPassWord;
    Button buttonSignUp;
    TextView tvSingUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mapping();
        buttonSignUp.setOnClickListener(v->{
            MySqliteDbAccount db = new MySqliteDbAccount(this);
            if(check()){
                String txtUserName = txtUserAdmin.getText().toString().trim();
                String passWord = passWordAdmin.getText().toString().trim();
                db.insertAccountAdmin(txtUserName,passWord);
                Toast.makeText(this, "Thêm tài khoản admin thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignIn.this, Login.class);
                startActivity(intent);
            }
        });
        tvSingUp.setOnClickListener(v->{
            Intent intent = new Intent(SignIn.this, Login.class);
            startActivity(intent);
        });
    }
    public void mapping(){
        txtUserAdmin = findViewById(R.id.txtUserAdmin);
        passWordAdmin = findViewById(R.id.txtPassWordAdmin);
        confirmPassWord = findViewById(R.id.txtPassWordAdmin1);
        buttonSignUp = findViewById(R.id.buttonSignUP);
        tvSingUp = findViewById(R.id.login);
    }
    public boolean check(){
        if(txtUserAdmin.getText().toString().trim().isEmpty()||  passWordAdmin.getText().toString().isEmpty()|| confirmPassWord.getText().toString().isEmpty()){
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin đăng kí ", Toast.LENGTH_SHORT).show();
            if(!passWordAdmin.getText().toString().trim().equals(confirmPassWord.getText().toString())){
                Toast.makeText(this, "Mật khẩu không trùng khớp ", Toast.LENGTH_SHORT).show();
                return false;
            }
            return false;
        }
        return true;
    }
}