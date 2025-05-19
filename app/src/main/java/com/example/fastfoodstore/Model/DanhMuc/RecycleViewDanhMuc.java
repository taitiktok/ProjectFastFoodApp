package com.example.fastfoodstore.Model.DanhMuc;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodstore.Model.MonAn.MonAn;
import com.example.fastfoodstore.Model.NhanVien.MySqliteDbNhanVien;
import com.example.fastfoodstore.Model.NhanVien.NhanVien;
import com.example.fastfoodstore.R;
import com.example.fastfoodstore.Views.DanhMuc.Edit_Nhom_Mon;
import com.example.fastfoodstore.Views.MonAn.edit_mon_an;

import java.util.ArrayList;

public class RecycleViewDanhMuc extends RecyclerView.Adapter<RecycleViewDanhMuc.MyViewHolder> {
    private Context context;
    private ArrayList<DanhMuc> listDanhMuc;
    private ArrayList<DanhMuc> listDanhMucSearch;

    public RecycleViewDanhMuc(Context context, ArrayList<DanhMuc> listDanhMuc) {
        this.context = context;
        this.listDanhMuc = listDanhMuc;
        this.listDanhMucSearch = new ArrayList<>(listDanhMuc); // Initialize search list with the full list
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_in_recycleview_danhmuc, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DanhMuc danhMuc = listDanhMucSearch.get(position); // Use the search list for displaying items
        holder.txtIdNhomMon.setText(String.valueOf(danhMuc.getMaDanhMuc()));
        holder.txtTenNhomMon.setText(danhMuc.getTenDanhMuc());
        byte[] imageBytes =danhMuc.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
        holder.imageDanhMuc.setImageBitmap(bitmap);
        // Delete button click listener
        holder.delete.setOnClickListener(v -> {

        });
        // Edit button click listener
        holder.edit.setOnClickListener(v -> {
            DanhMuc monEdit =listDanhMucSearch.get(position);
            Intent intent = new Intent(context, Edit_Nhom_Mon.class);
            intent.putExtra("IdNhomMonEdit",monEdit.getMaDanhMuc());
            context.startActivity(intent);
        });
        holder.delete.setOnClickListener(v->{
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có chắc chắn muốn xóa nhân viên này không?")
                    .setNegativeButton("Đồng ý", (dialog, which) -> {
                        int idDelete = listDanhMucSearch.get(position).getMaDanhMuc();
                        MySqliteDBDanhMuc db1 = new MySqliteDBDanhMuc(context);
                        db1.deleteDanhMuc(idDelete);
                        listDanhMucSearch.remove(position);
                        listDanhMuc.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,listDanhMucSearch.size());
                    })
                    .setPositiveButton("Hủy bỏ", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return listDanhMucSearch.size(); // Use the search list size
    }

    public void filter(String keyWord) {
        keyWord = keyWord.toLowerCase().trim();
        listDanhMucSearch.clear();
        if (keyWord.isEmpty()) {
            listDanhMucSearch.addAll(listDanhMuc); // Restore the original list when keyword is empty
        } else {
            for (DanhMuc dm : listDanhMuc) {
                String maNv = String.valueOf(dm.getMaDanhMuc());
                if (maNv.toLowerCase().contains(keyWord) || dm.getTenDanhMuc().toLowerCase().contains(keyWord)) {
                    listDanhMucSearch.add(dm);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenNhomMon, txtIdNhomMon;
        ImageView edit, delete,imageDanhMuc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            edit = itemView.findViewById(R.id.imageViewEdit);
            delete = itemView.findViewById(R.id.imageViewDelete);
            txtIdNhomMon = itemView.findViewById(R.id.idNhomMon);
            txtTenNhomMon = itemView.findViewById(R.id.txtTenNhomMon);
            imageDanhMuc = itemView.findViewById(R.id.imageDanhMuc);
        }
    }
    public void setData(ArrayList<DanhMuc> newList) {
        this.listDanhMuc= new ArrayList<>(newList);
        this.listDanhMucSearch = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

}
