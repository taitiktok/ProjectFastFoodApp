package com.example.fastfoodstore.Views.DoanhThu;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fastfoodstore.Model.DanhMuc.DanhMuc;
import com.example.fastfoodstore.Model.DoanhThu.MySqliteDBDoanhThu;
import com.example.fastfoodstore.Model.DoanhThu.RecycleViewDoanhThu;
import com.example.fastfoodstore.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoanhThu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoanhThu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecycleViewDoanhThu recycleViewDoanhThu;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    TextView txtTongDoanhThu;
    public DoanhThu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoanhThu.
     */
    // TODO: Rename and change types and number of parameters
    public static DoanhThu newInstance(String param1, String param2) {
        DoanhThu fragment = new DoanhThu();
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
        View view = inflater.inflate(R.layout.fragment_doanh_thu, container, false);
        recyclerView = view.findViewById(R.id.recycleViewDoanhThu);
        Toolbar toolbar = view.findViewById(R.id.toolbarDoanhThu);
        txtTongDoanhThu = view.findViewById(R.id.txtTongDoanhThu);
        txtTongDoanhThu.setText(String.valueOf(TinhTongDoanhThu()));

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("Doanh thu");
        toolbar.setBackgroundColor(Color.parseColor("#FF9800"));
        toolbar.setTitleTextColor(Color.WHITE); //
        setAdapter();
        return view;
    }
    public void setAdapter(){
        MySqliteDBDoanhThu db = new MySqliteDBDoanhThu(getContext());
        ArrayList<com.example.fastfoodstore.Model.DoanhThu.DoanhThu> listThongKe = db.getThongKeDoanhThuTheoTenMon();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleViewDoanhThu = new RecycleViewDoanhThu(getContext(),listThongKe);
        recyclerView.setAdapter(recycleViewDoanhThu);

    }

    public double TinhTongDoanhThu(){
        MySqliteDBDoanhThu db = new MySqliteDBDoanhThu(getContext());
        ArrayList<com.example.fastfoodstore.Model.DoanhThu.DoanhThu> listDoanhThu = db.getThongKeDoanhThuTheoTenMon();
        double TongTien = 0;
        for(com.example.fastfoodstore.Model.DoanhThu.DoanhThu dt : listDoanhThu){
            TongTien = TongTien + dt.getTongTien();
        }
        return TongTien;
    }

    @Override
    public void onResume() {
        setAdapter();
        super.onResume();
        txtTongDoanhThu.setText(String.valueOf(TinhTongDoanhThu()));
    }
}