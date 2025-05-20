package com.example.fastfoodstore.Views.GioHang;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfoodstore.Model.DanhMuc.MySqliteDBDanhMuc;
import com.example.fastfoodstore.Model.DoanhThu.DoanhThu;
import com.example.fastfoodstore.Model.DoanhThu.MySqliteDBDoanhThu;
import com.example.fastfoodstore.Model.MonAn.MonAn;
import com.example.fastfoodstore.Model.MonAn.RecycleViewMonAn;
import com.example.fastfoodstore.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GioHang#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GioHang extends Fragment {
    RecyclerView recyclerView;
    RecycleViewGioHang recycleViewGioHang;
    TextView tvTongTien;
    ImageView imageView;
     boolean trangthai = false;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
   Toolbar toolbar;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public GioHang() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GioHang.
     */

    public static GioHang newInstance(String param1, String param2) {
        GioHang fragment = new GioHang();
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
        View view =  inflater.inflate(R.layout.fragment_gio_hang, container, false);
        toolbar = view.findViewById(R.id.toolbarGioHang);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("Giỏ hàng");
        toolbar.setBackgroundColor(Color.parseColor("#FF9800"));
        toolbar.setTitleTextColor(Color.WHITE);
        recyclerView = view.findViewById(R.id.recycleViewGioHang);
        tvTongTien = view.findViewById(R.id.tvTongTien);
        imageView = view.findViewById(R.id.chietKhau);


        imageView = view.findViewById(R.id.chietKhau);
        imageView.setOnClickListener(v -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
            LayoutInflater inflater1 = getLayoutInflater();
            View dialogView = inflater1.inflate(R.layout.chietkhau, null);
            dialogBuilder.setView(dialogView);
            dialogBuilder.setCancelable(false);
            AlertDialog dialog = dialogBuilder.create();
            Button huy = dialogView.findViewById(R.id.HuyBo);
            TextView status = dialogView.findViewById(R.id.status);
//            Switch switchChietKhau = dialogView.findViewById(R.id.switchChietKhau);
//            switchChietKhau.setOnCheckedChangeListener(((buttonView, isChecked) -> {
//                if(switchChietKhau.isChecked() == true){
//                    status.setText("Phần trăm");
//                    trangthai = true;
//                }
//                else{
//                    status.setText("Số tiền");
//                }
//            }));
//            huy.setOnClickListener(view1 -> {
//                dialog.dismiss();
//            });
//            dialog.show();
        });
        
        double getTongTien = TongTien();
        tvTongTien.setText(" "+getTongTien);
        TextView buttonThanhToan = view.findViewById(R.id.tvButtonThanhToan);
        buttonThanhToan.setOnClickListener(v -> {
            MySqliteDBDanhMuc dbDanhMuc = new MySqliteDBDanhMuc(getContext());
            MySqliteDBDoanhThu dbDoanhThu = new MySqliteDBDoanhThu(getContext());
            ArrayList<MonAn> listGioHang = dbDanhMuc.getAllMonAnInGioHang();
            for (MonAn monAn : listGioHang) {
                DoanhThu doanhThu = new DoanhThu(
                        0,
                        monAn.getMaMonAn(),
                        monAn.getTenMonAn(),
                        monAn.getSoLuong(),
                        monAn.getGiaTien(),
                        monAn.getGiaTien() * monAn.getSoLuong(),
                        monAn.getMaNhomMonAn(),
                        monAn.getTenNhomMon()
                );
                dbDoanhThu.themVaoDoanhThuTheoThang(doanhThu);
                dbDoanhThu.themVaoDoanhThu(doanhThu);
            }
            dbDanhMuc.clearGioHang();
            ArrayList<MonAn> afterClear = dbDanhMuc.getAllMonAnInGioHang();
            Log.d("DEBUG", "Sau clear giỏ hàng còn: " + afterClear.size());
            setAdapter();
            tvTongTien = view.findViewById(R.id.tvTongTien);
            tvTongTien.setText(String.valueOf(TongTien()));
            Toast.makeText(getContext(), "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
        });
        setAdapter();
        return view;
    }
    public void setAdapter() {
        MySqliteDBDanhMuc db = new MySqliteDBDanhMuc(getContext());
        ArrayList<MonAn> listMonAnInGioHang = db.getAllMonAnInGioHang();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleViewGioHang = new RecycleViewGioHang(getContext(), listMonAnInGioHang);
        recyclerView.setAdapter(recycleViewGioHang);

        recycleViewGioHang.setOnQuantityChangeListener(new RecycleViewGioHang.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged() {
                TextView txtTongTien = getView().findViewById(R.id.tvTongTien);
                txtTongTien.setText(String.valueOf(TongTien()));
            }
        });
    }
    public double TongTien(){
        double TongTien = 0 ;
        MySqliteDBDanhMuc db = new MySqliteDBDanhMuc(getContext());
        ArrayList<MonAn> listMonAn = db.getAllMonAnInGioHang();
        for(MonAn monAn : listMonAn){
            double thanhTien = monAn.getGiaTien()*monAn.getSoLuong();
            TongTien = TongTien + thanhTien;
        }
        return TongTien;
    }
    @Override
    public void onResume() {
        super.onResume();
        setAdapter();
        double getTongTien = TongTien();
        tvTongTien.setText(" "+getTongTien);
    }

}