package com.example.fastfoodstore.Views.Fragement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfoodstore.Model.DanhMuc.MySqliteDBDanhMuc;
import com.example.fastfoodstore.Model.DoanhThu.MySqliteDBDoanhThu;
import com.example.fastfoodstore.Model.NhanVien.MySqliteDbNhanVien;
import com.example.fastfoodstore.Model.NhanVien.NhanVien;
import com.example.fastfoodstore.R;
import com.example.fastfoodstore.Views.Account.LoadingDiglog;
import com.example.fastfoodstore.Views.Account.Login;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaiKhoan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaiKhoan extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LoadingDiglog loadingDiglog;
    CircleImageView imageView ;
    TextView txtTenNhanVien ;
    TextView txtMaNhanVien ;
    TextView txtGioiTinh ;
    TextView txtSoDienThoai ;
    TextView txtDiaChi ;
    LinearLayout buttonLogOut;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TaiKhoan() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaiKhoan.
     */
    // TODO: Rename and change types and number of parameters
    public static TaiKhoan newInstance(String param1, String param2) {
        TaiKhoan fragment = new TaiKhoan();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_tai_khoan, container, false);
        imageView = view.findViewById(R.id.imageNhanVienMain);
        txtTenNhanVien = view.findViewById(R.id.txtNhanVienMain);
        txtMaNhanVien = view.findViewById(R.id.txtMaNhanVienMain);
        txtGioiTinh = view.findViewById(R.id.txtGioiTinhMain);
        txtSoDienThoai = view.findViewById(R.id.txtSoDienThoaiMain);
        txtDiaChi = view.findViewById(R.id.txtDiaChiMain);
        buttonLogOut = view.findViewById(R.id.LogOut);
        loadingDiglog = new LoadingDiglog(getActivity());
        buttonLogOut.setOnClickListener(v->{
            new AlertDialog.Builder(view.getContext()).setTitle("Logout").setMessage("Bạn có muốn đăng xuất khỏi ứng dụng ? ")
                    .setNegativeButton("Đồng ý",(dialog, which) -> {
                        MySqliteDBDanhMuc db = new MySqliteDBDanhMuc(getContext());
                        MySqliteDBDoanhThu db1 = new MySqliteDBDoanhThu(getContext());
                        db.clearGioHang();
                        db1.clearDoanhThuTheoCaLamViec();
                        Handler handler = new Handler();
                        loadingDiglog.dangDangXuat();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(view.getContext(), Login.class);
                                startActivity(intent);
                                loadingDiglog.dismissDialog();
                            }
                        }, 2000);

                    }).setPositiveButton("Hủy bỏ",null).show();
        });
        loadData();
        return view;
    }
    public NhanVien getNhanVienBySdt(String sdt){
        MySqliteDbNhanVien db = new MySqliteDbNhanVien(getContext());
        NhanVien nhanVien = db.getNhanVienBySoDienThoai(sdt);
        if(nhanVien == null){
            Toast.makeText(getContext(), "Khong ton tai nhan vien", Toast.LENGTH_SHORT).show();
        }
        return nhanVien;
    }
    public void loadData(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("TTNV",Context.MODE_PRIVATE);
        String Sodienthoai = sharedPreferences.getString("Sdt","defaulUser");
        NhanVien nhanVien = getNhanVienBySdt(Sodienthoai);
        if(nhanVien!=null){
            txtMaNhanVien.setText(String.valueOf(nhanVien.getMaNhanVien()));
            txtTenNhanVien.setText(nhanVien.getTenNhanVien());
            txtGioiTinh.setText(nhanVien.getGioiTinh());
            txtSoDienThoai.setText(nhanVien.getSoDienThoai());
            txtDiaChi.setText(nhanVien.getDiaChi());
            byte[] imageBytes = nhanVien.getImageNhanVien();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
            imageView.setImageBitmap(bitmap);
        }
        else{
            txtMaNhanVien.setText("Nhóm 01 - Khoa CNTT");
            txtTenNhanVien.setText("Trường ĐH Công Nghiệp Hà Nội");
            txtGioiTinh.setText("Nam");
            txtSoDienThoai.setText("1900 3088");
            txtDiaChi.setText("Quận Nam Từ Liêm - Thành Phố Hà Nội");
            imageView.setImageResource(R.drawable.account);
        }
    }
    @Override
    public void onResume() {
        loadData();
        super.onResume();
    }
}