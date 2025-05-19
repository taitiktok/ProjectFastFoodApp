package com.example.fastfoodstore.Views.TrangChu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodstore.Model.DanhMuc.DanhMuc;
import com.example.fastfoodstore.Model.MonAn.MonAn;
import com.example.fastfoodstore.Views.MonAnInTrangChu.MonAnFromTrangChu;
import com.example.fastfoodstore.R;

import java.util.ArrayList;

public class RecycleViewNhomMonInTrangChu extends RecyclerView.Adapter<RecycleViewNhomMonInTrangChu.MyViewHolder> {
    @NonNull
    private Context context;
    private ArrayList<DanhMuc> listDanhMuc;
    private ArrayList<DanhMuc> listDanhMucSearch;

    public RecycleViewNhomMonInTrangChu(ArrayList<DanhMuc> listDanhMuc, @NonNull Context context) {
        this.listDanhMuc = listDanhMuc;
        this.context = context;
        this.listDanhMucSearch = new ArrayList<>(listDanhMuc);
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_in_recycleview_danhmuc_in_trangchu, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DanhMuc danhMuc = listDanhMucSearch.get(position);
        holder.tenNhomMon.setText(danhMuc.getTenDanhMuc());

        byte[] imageBytes = danhMuc.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.imageNhomMonInTrangChu.setImageBitmap(bitmap);

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MonAnFromTrangChu.class);
            DanhMuc nhomMonBan = listDanhMucSearch.get(position);
            intent.putExtra("IdNhomMonBan", nhomMonBan.getMaDanhMuc());
            Log.d("", "Ma danh muc vua nhan duoc la " + nhomMonBan.getMaDanhMuc());
            context.startActivity(intent);
        });
    }
    public void filter(String keyWord) {
        keyWord = keyWord.toLowerCase().trim();
        listDanhMucSearch.clear();
        if (keyWord.isEmpty()) {
            listDanhMucSearch.addAll(listDanhMuc);
        } else {
            for (DanhMuc ma : listDanhMuc) {
                String maMonAn = String.valueOf(ma.getMaDanhMuc());
                if (maMonAn.toLowerCase().contains(keyWord) || ma.getTenDanhMuc().toLowerCase().contains(keyWord)) {
                    listDanhMucSearch.add(ma);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listDanhMucSearch.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageNhomMonInTrangChu;
        TextView tenNhomMon;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewNhomMon);
            imageNhomMonInTrangChu = itemView.findViewById(R.id.ImageNhomMonInTrangChu);
            tenNhomMon = itemView.findViewById(R.id.txtTenNhomMonInTrangChu);
        }
    }
}
