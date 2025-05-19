package com.example.fastfoodstore.Model.DoanhThu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodstore.Model.DanhMuc.DanhMuc;
import com.example.fastfoodstore.Model.DanhMuc.RecycleViewDanhMuc;
import com.example.fastfoodstore.R;

import java.util.ArrayList;

public class RecycleViewDoanhThu extends RecyclerView.Adapter<RecycleViewDoanhThu.MyViewHolder>  {
    private Context context;
    private ArrayList<DoanhThu> listDoanhThu;

    public RecycleViewDoanhThu(Context context, ArrayList<DoanhThu> listDoanhThu) {
        this.context = context;
        this.listDoanhThu = listDoanhThu;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_thongke, parent, false);
        return new RecycleViewDoanhThu.MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DoanhThu doanhThu = listDoanhThu.get(position);
        holder.stt.setText(String.valueOf(doanhThu.getStt()));
        holder.ten.setText(doanhThu.getTenMon());
        holder.soluong.setText(String.valueOf(doanhThu.getSoLuong()));
        holder.tongTien.setText(String.valueOf(doanhThu.getTongTien()));
    }

    @Override
    public int getItemCount() {
        return listDoanhThu.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView stt,ten,soluong,tongTien;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            stt = itemView.findViewById(R.id.txtSoTT);
            ten = itemView.findViewById(R.id.txtTenMonInListView);
            soluong = itemView.findViewById(R.id.txtSoLuongMonInList);
            tongTien = itemView.findViewById(R.id.txtSLInListView);
        }
    }
}
