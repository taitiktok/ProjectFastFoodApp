package com.example.fastfoodstore.Views.Fragement;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.fastfoodstore.Model.DanhMuc.DanhMuc;
import com.example.fastfoodstore.Model.DanhMuc.MySqliteDBDanhMuc;
import com.example.fastfoodstore.R;
import com.example.fastfoodstore.Views.Main.MainActivity;
import com.example.fastfoodstore.Views.TrangChu.RecycleViewNhomMonInTrangChu;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrangChu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrangChu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerView;
    EditText editText;
    RecycleViewNhomMonInTrangChu recycleViewNhomMonInTrangChu;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TrangChu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrangChu.
     */
    // TODO: Rename and change types and number of parameters
    public static TrangChu newInstance(String param1, String param2) {
        TrangChu fragment = new TrangChu();
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
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);
        ImageSlider imageSlider = view.findViewById(R.id.image_slider);
        recyclerView = view.findViewById(R.id.recycleNhomMon);
        editText = view.findViewById(R.id.timkiem);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (recycleViewNhomMonInTrangChu != null) {
                    recycleViewNhomMonInTrangChu.filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        List<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.slider3, null, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.slider4, null, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.slider2, null, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.slider3, null, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.slider4, null, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.slider2, null, ScaleTypes.FIT));
        imageSlider.setImageList(imageList, ScaleTypes.FIT);
        SetAdapter();// hoặc ScaleTypes.CENTER_CROP
        return view;
    }
    public void SetAdapter(){
        MySqliteDBDanhMuc db = new MySqliteDBDanhMuc(getContext());
        ArrayList<DanhMuc> listDanhMuc = db.getDanhMuc();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recycleViewNhomMonInTrangChu = new RecycleViewNhomMonInTrangChu(listDanhMuc, getContext());  // GÁN VÀO BIẾN THÀNH VIÊN
        recyclerView.setAdapter(recycleViewNhomMonInTrangChu);
    }


    @Override
    public void onResume() {
        super.onResume();
        SetAdapter();
    }
}