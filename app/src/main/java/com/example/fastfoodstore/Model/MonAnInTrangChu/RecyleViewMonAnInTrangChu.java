package com.example.fastfoodstore.Model.MonAnInTrangChu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodstore.Model.MonAn.MonAn;
import com.example.fastfoodstore.R;
import com.example.fastfoodstore.Views.MonAnInTrangChu.XemChiTietMonAn1;

import java.util.ArrayList;

public class RecyleViewMonAnInTrangChu extends RecyclerView.Adapter<RecyleViewMonAnInTrangChu.onMyViewHolder> {
    private Context context;
    private ArrayList<MonAn> listMonAn;
    private ArrayList<MonAn> listMonAnSearch;

    public RecyleViewMonAnInTrangChu(Context context, ArrayList<MonAn> listMonAn) {
        this.context = context;
        this.listMonAn = listMonAn;
        this.listMonAnSearch = new ArrayList<>(listMonAn);
    }
    @NonNull
    @Override
    public onMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_view_ban_mon_an, parent, false);
        return new onMyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull onMyViewHolder holder, int position) {
        MonAn monAn = listMonAnSearch.get(position);

        holder.txtTenMon.setText(monAn.getTenMonAn());
        holder.txtDonGia.setText(monAn.getGiaTien() + "");

        byte[] imageBytes = monAn.getImageMonAn();
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.imageMonAn.setImageBitmap(bitmap);
        } else {
            holder.imageMonAn.setImageResource(R.drawable.add_mon_an); // ảnh mặc định
        }
        holder.cardView.setOnClickListener(v->{
            MonAn xemChiTietMonAn  = listMonAn.get(position);
            Intent intent = new Intent(context, XemChiTietMonAn1.class);
            intent.putExtra("IdMonAnXemChiTiet",xemChiTietMonAn.getMaMonAn());
            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return listMonAnSearch.size();
    }
    public class onMyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageMonAn;
        TextView txtTenMon, txtDonGia;
        CardView cardView;
        public onMyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewMonAnBan);
            imageMonAn = itemView.findViewById(R.id.imageMonAn);
            txtTenMon = itemView.findViewById(R.id.tenMonAn);
            txtDonGia = itemView.findViewById(R.id.giaTien);
        }
    }
}
