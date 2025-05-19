package com.example.fastfoodstore.Model.NhanVien;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodstore.R;
import com.example.fastfoodstore.Views.NhanVien.EditNhanVien;

import java.util.ArrayList;

public class RecycleViewNhanVien extends RecyclerView.Adapter<RecycleViewNhanVien.MyViewHolder> {

    @NonNull
    private Context context;
    private ArrayList<NhanVien> listNhanVien;
    private ArrayList<NhanVien> listNhanVienSearch;
    public RecycleViewNhanVien(@NonNull Context context, ArrayList<NhanVien> listNhanVien) {
        this.context = context;
        this.listNhanVien = listNhanVien;
        this.listNhanVienSearch = new ArrayList<>(listNhanVien); // Khởi tạo listNhanVienSearch
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_in_recycleview_nhanvien, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NhanVien nhanVien = listNhanVienSearch.get(position);
        holder.tenNv.setText(nhanVien.getTenNhanVien());
        holder.maNv.setText(String.valueOf(nhanVien.getMaNhanVien()));
        holder.soDienThoai.setText(nhanVien.getSoDienThoai());
        holder.diaChi.setText(nhanVien.getDiaChi());
        holder.gioiTinh.setText(nhanVien.getGioiTinh());

        byte[] imageBytes = nhanVien.getImageNhanVien();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.imageNhanVien.setImageBitmap(bitmap);
        holder.layoutChiTiet.setVisibility(nhanVien.getExpanded() ? View.VISIBLE : View.GONE);
        holder.itemView.setOnClickListener(v -> {
            nhanVien.setExpanded(!nhanVien.getExpanded());
            notifyItemChanged(position);
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditNhanVien.class);
                NhanVien nvEdit = listNhanVienSearch.get(position);
                intent.putExtra("idFix", nvEdit.getMaNhanVien());
                context.startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có chắc chắn muốn xóa nhân viên này không?")
                    .setNegativeButton("Đồng ý", (dialog, which) -> {
                        int idDelete = listNhanVienSearch.get(position).getMaNhanVien();
                        MySqliteDbNhanVien db = new MySqliteDbNhanVien(context);
                        db.deleteNhanVien(idDelete);
                        listNhanVien.remove(position);
                        listNhanVienSearch.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, listNhanVienSearch.size());
                    })
                    .setPositiveButton("Hủy bỏ", null)
                    .show();
        });
    }
    public void filter(String keyWord) {
        keyWord = keyWord.toLowerCase().trim();
        listNhanVienSearch.clear();
        if (keyWord.isEmpty()) {
            listNhanVienSearch.addAll(listNhanVien);
        } else {
            for (NhanVien nv : listNhanVien) {
                String maNv = String.valueOf(nv.getMaNhanVien());
                if (maNv.toLowerCase().contains(keyWord) || nv.getTenNhanVien().toLowerCase().contains(keyWord)) {
                    listNhanVienSearch.add(nv);
                }
            }
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listNhanVienSearch.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageNhanVien;
        LinearLayout layoutChiTiet;
        ImageView delete,edit;
        TextView maNv, tenNv, diaChi, soDienThoai, gioiTinh;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutChiTiet = itemView.findViewById(R.id.layoutChiTiet);
            imageNhanVien = itemView.findViewById(R.id.imageNhanVien);
            maNv = itemView.findViewById(R.id.maNv);
            tenNv = itemView.findViewById(R.id.tenNv);
            gioiTinh = itemView.findViewById(R.id.gioiTinh);
            diaChi = itemView.findViewById(R.id.diaChi);
            soDienThoai = itemView.findViewById(R.id.soDienThoai);
            edit = itemView.findViewById(R.id.imgEdit);
            delete = itemView.findViewById(R.id.imgDelete);
        }
    }
    public void setData(ArrayList<NhanVien> newList) {
        this.listNhanVien = new ArrayList<>(newList);
        this.listNhanVienSearch = new ArrayList<>(newList);
        notifyDataSetChanged();
    }
}
