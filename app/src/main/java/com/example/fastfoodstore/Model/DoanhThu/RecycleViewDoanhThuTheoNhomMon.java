package com.example.fastfoodstore.Model.DoanhThu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodstore.R;

import java.util.ArrayList;

public class RecycleViewDoanhThuTheoNhomMon extends RecyclerView.Adapter<RecycleViewDoanhThuTheoNhomMon.MyViewHolder> {
    Context context;
    ArrayList<DoanhThu> ListdoanhThu = new ArrayList<>();

    public RecycleViewDoanhThuTheoNhomMon(Context context, ArrayList<DoanhThu> listdoanhThu) {
        this.context = context;
        ListdoanhThu = listdoanhThu;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleviewthongkedoanhthutheothang,parent,false);
       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
          DoanhThu doanhThu1 = ListdoanhThu.get(position);
          holder.stt.setText(doanhThu1.getStt()+"");
          holder.TenNhomMon.setText(doanhThu1.getTenDanhMuc());
          holder.DoanhThuTheoNhomMon.setText(""+doanhThu1.getTongTien());
    }

    @Override
    public int getItemCount() {
        return ListdoanhThu.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
         TextView  stt,TenNhomMon,DoanhThuTheoNhomMon;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            stt = itemView.findViewById(R.id.stt);
            TenNhomMon = itemView.findViewById(R.id.tennhommoninbieudo);
            DoanhThuTheoNhomMon = itemView.findViewById(R.id.doanhthutheonhommon);
        }
    }
}
