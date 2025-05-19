package com.example.fastfoodstore.ViewModels;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.fastfoodstore.Views.DoanhThu.DoanhThu;
import com.example.fastfoodstore.Views.GioHang.GioHang;
import com.example.fastfoodstore.Views.Fragement.TaiKhoan;
import com.example.fastfoodstore.Views.Fragement.TrangChu;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
          switch (position){
              case 0:
                  return new TrangChu();
              case 1:
                  return new GioHang();
              case 2:
                  return new DoanhThu();
              case 3:
                  return new TaiKhoan();
              default:
                  return new TrangChu();
          }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
