package com.example.fastfoodstore.Model.MonAn;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodstore.Model.DanhMuc.MySqliteDBDanhMuc;
import com.example.fastfoodstore.Model.NhanVien.NhanVien;
import com.example.fastfoodstore.R;
import com.example.fastfoodstore.Views.MonAn.edit_mon_an;

import java.util.ArrayList;

public class RecycleViewMonAn extends RecyclerView.Adapter<RecycleViewMonAn.onMyViewHolder> {
    private Context context;
    private ArrayList<MonAn> listMonAn;
    private ArrayList<MonAn> listMonAnSearch;
    RecycleViewMonAn recycleViewMonAn;
    public RecycleViewMonAn(Context context, ArrayList<MonAn> listMonAn) {
        this.context = context;
        this.listMonAn = listMonAn;
        this.listMonAnSearch = new ArrayList<>(listMonAn);
    }
    @NonNull
    @Override
    public onMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_in_recycleview_monan, parent, false);
        return new RecycleViewMonAn.onMyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull onMyViewHolder holder, int position) {

          MonAn monAn = listMonAnSearch.get(position);
          holder.txtMaMonAn.setText(String.valueOf(monAn.getMaMonAn()));
          holder.txtTenMon.setText(monAn.getTenMonAn());
          holder.txtDonGia.setText(String.valueOf(monAn.getGiaTien()));
          holder.txtTenNhomMon.setText(monAn.getTenNhomMon());
          holder.txtMoTaChiTiet.setText(monAn.getMoTaChiTiet());
          byte[] imageBytes = monAn.getImageMonAn();

        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
        holder.imageMonAn.setImageBitmap(bitmap);
        holder.layoutChiTiet.setVisibility(monAn.getExpanded() ? View.VISIBLE : View.GONE);
        holder.itemView.setOnClickListener(v->{
            monAn.setExpanded(!monAn.getExpanded());
            notifyItemChanged(position);
        });

        holder.imageDelete.setOnClickListener(v->{
            MonAn deleteMon = listMonAn.get(position);
            int idDelete = deleteMon.getMaMonAn();
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có chắc chắn muốn xóa món này không ?")
                    .setNegativeButton("Đồng ý ",(dialog, which) -> {
                        MySqliteDBDanhMuc db  = new MySqliteDBDanhMuc(context);
                        db.deleteDanhMuc(idDelete);
                        Toast.makeText(context, "Xóa thành công món vừa chọn", Toast.LENGTH_SHORT).show();
                        listMonAn.remove(position);
                        listMonAnSearch.remove(position);
                        notifyItemChanged(position);
                        notifyItemRangeChanged(position,listMonAnSearch.size());
                    })
                    .setPositiveButton("Hủy bỏ",null).show();
        });
        holder.imageEdit.setOnClickListener(v->{
            MonAn monEdit =listMonAn.get(position);
            Intent intent = new Intent(context, edit_mon_an.class);
            intent.putExtra("IdMonAnEdit",monEdit.getMaMonAn());
            context.startActivity(intent);
        });
     }

    @Override
    public int getItemCount() {

        return listMonAnSearch.size();
    }


    public void filter(String keyWord) {
        keyWord = keyWord.toLowerCase().trim();
        listMonAnSearch.clear();
        if (keyWord.isEmpty()) {
            listMonAnSearch.addAll(listMonAn);
        } else {
            for (MonAn ma : listMonAn) {
                String maMonAn = String.valueOf(ma.getMaMonAn());
                if (maMonAn.toLowerCase().contains(keyWord) || ma.getTenMonAn().toLowerCase().contains(keyWord)) {
                    listMonAnSearch.add(ma);
                }
            }
        }
        notifyDataSetChanged();
    }


    public class onMyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layoutChiTiet;
        ImageView imageMonAn,imageEdit,imageDelete;
        TextView txtTenMon,txtDonGia,txtTenNhomMon,txtMoTaChiTiet,txtMaMonAn;
        public onMyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaMonAn = itemView.findViewById(R.id.maMon);
            imageMonAn = itemView.findViewById(R.id.imageMonAn);
            imageEdit = itemView.findViewById(R.id.imgEdit);
            imageDelete = itemView.findViewById(R.id.imgDelete);
            txtTenMon = itemView.findViewById(R.id.tenMonAn);
            txtDonGia = itemView.findViewById(R.id.giaTien);
           txtTenNhomMon = itemView.findViewById(R.id.tenNhomMon);
           txtMoTaChiTiet = itemView.findViewById(R.id.txtMoTaChiTiet);
           layoutChiTiet = itemView.findViewById(R.id.layoutChiTiet);
        }
    }
}
