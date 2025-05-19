package com.example.fastfoodstore.Views.GioHang;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.service.chooser.ChooserAction;
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

import com.example.fastfoodstore.Model.DanhMuc.MySqliteDBDanhMuc;
import com.example.fastfoodstore.Model.MonAn.MonAn;
import com.example.fastfoodstore.Model.NhanVien.RecycleViewNhanVien;
import com.example.fastfoodstore.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class RecycleViewGioHang extends RecyclerView.Adapter<RecycleViewGioHang.MyViewHolder> {
    Context context;
    ArrayList<MonAn> listGioHang = new ArrayList<>();
    public interface OnQuantityChangeListener {
        void onQuantityChanged();
    }

    public RecycleViewGioHang(Context context, ArrayList<MonAn> listGioHang) {
        this.context = context;
        this.listGioHang = listGioHang;
    }
    private OnQuantityChangeListener quantityChangeListener;

    public void setOnQuantityChangeListener(OnQuantityChangeListener listener) {
        this.quantityChangeListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_in_giohang, parent, false);
        return new RecycleViewGioHang.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
           MonAn monAn = listGioHang.get(position);
           holder.tenMonInGioHang.setText(monAn.getTenMonAn());
           holder.GiaInGioHang.setText(String.valueOf(monAn.getGiaTien()));
           holder.soLuongInGioHang.setText(String.valueOf(monAn.getSoLuong()));
           byte[] imageBytes = monAn.getImageMonAn();
           Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
           holder.imageMonAnGioHang.setImageBitmap(bitmap);
        holder.buttonXoa.setOnClickListener(v -> {
            MonAn deleteMon = listGioHang.get(position);
            int idDelete = deleteMon.getMaMonAn();
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có chắc chắn muốn xóa món hàng này không ?")
                    .setNegativeButton("Đồng ý", (dialog, which) -> {
                        MySqliteDBDanhMuc db = new MySqliteDBDanhMuc(context);
                        db.deleteMonAnInGioHan(idDelete);
                        Toast.makeText(context, "Xóa thành công món vừa chọn", Toast.LENGTH_SHORT).show();
                        listGioHang.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, listGioHang.size());

                        if (quantityChangeListener!= null) {
                            quantityChangeListener.onQuantityChanged();
                        }
                    })
                    .setPositiveButton("Hủy bỏ", null)
                    .show();
        });

        holder.buttonAdd.setOnClickListener(v -> {
            int currentSoLuong = Integer.parseInt(holder.soLuongInGioHang.getText().toString());
            int newSoLuong = TangSoLuong(currentSoLuong);
            holder.soLuongInGioHang.setText(String.valueOf(newSoLuong));
            monAn.setSoLuong(newSoLuong);
            MySqliteDBDanhMuc db = new MySqliteDBDanhMuc(context);
            db.capNhatSoLuongGioHang(monAn.getMaMonAn(), newSoLuong);

            if (quantityChangeListener != null) {
                quantityChangeListener.onQuantityChanged();
            }
        });

        holder.buttonTru.setOnClickListener(v -> {
            int currentSoLuong = Integer.parseInt(holder.soLuongInGioHang.getText().toString());
            int newSoLuong = giamSL(currentSoLuong);
            holder.soLuongInGioHang.setText(String.valueOf(newSoLuong));

            monAn.setSoLuong(newSoLuong);
            MySqliteDBDanhMuc db = new MySqliteDBDanhMuc(context);
            db.capNhatSoLuongGioHang(monAn.getMaMonAn(), newSoLuong);

            if (quantityChangeListener != null) {
                quantityChangeListener.onQuantityChanged();
            }
        });
    }
    public int TangSoLuong(int SL){
        SL = SL + 1;
        return SL;
    }
    public int giamSL(int SL){
        if(SL > 0){
            SL = SL - 1;
        }
        return SL;
    }
    @Override
    public int getItemCount() {
        return listGioHang.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView buttonXoa,buttonAdd,buttonTru;
        ImageView imageMonAnGioHang;
        TextView tenMonInGioHang,soLuongInGioHang,GiaInGioHang;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonAdd = itemView.findViewById(R.id.buttonAddInGioHang);
            buttonTru = itemView.findViewById(R.id.buttonTruInGipHang);
            buttonXoa = itemView.findViewById(R.id.buttonXoaInGioHang);
            imageMonAnGioHang = itemView.findViewById(R.id.imageInGioHang);
            tenMonInGioHang = itemView.findViewById(R.id.nameMonAnInGioHang);
            soLuongInGioHang = itemView.findViewById(R.id.soLuongMonAnInGioHang);
            GiaInGioHang = itemView.findViewById(R.id.giaMonAnInGioHang);
        }
    }

}
