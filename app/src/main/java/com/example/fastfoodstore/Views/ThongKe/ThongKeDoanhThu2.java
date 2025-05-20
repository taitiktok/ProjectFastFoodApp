package com.example.fastfoodstore.Views.ThongKe;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodstore.Model.DoanhThu.DoanhThu;
import com.example.fastfoodstore.Model.DoanhThu.MySqliteDBDoanhThu;
import com.example.fastfoodstore.Model.DoanhThu.RecycleViewDoanhThuTheoNhomMon;
import com.example.fastfoodstore.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ThongKeDoanhThu2 extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView1;
    private TextView tongTienDoanhThuTrongBieuDo;
    private MySqliteDBDoanhThu db;
    private RecycleViewDoanhThuTheoNhomMon recycleViewDoanhThuTheoNhomMon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_ke_doanh_thu2);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo database
        db = new MySqliteDBDoanhThu(this);

        // Ánh xạ và cập nhật tổng doanh thu
        tongTienDoanhThuTrongBieuDo = findViewById(R.id.TongDoanhThuInBieuDo);
        tongTienDoanhThuTrongBieuDo.setText(""+tinhTongDoanhThuTrongBieuDo()
        );

        // Thiết lập RecyclerView thống kê doanh thu
        recyclerView1 = findViewById(R.id.simple1);
        ArrayList<DoanhThu> listDoanhThuTheoNhomMon = db.getThongKeDoanhThuTheoMaNhomMon();
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recycleViewDoanhThuTheoNhomMon = new RecycleViewDoanhThuTheoNhomMon(this, listDoanhThuTheoNhomMon);
        recyclerView1.setAdapter(recycleViewDoanhThuTheoNhomMon);

        // Toolbar
        toolbar = findViewById(R.id.toolbarDoanhThu);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Thống kê doanh thu");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // Thiết lập PieChart
        PieChart pieChart = findViewById(R.id.chart);
        List<PieEntry> entries = db.getPieEntriesDoanhThuTheoMaNhomMon();

        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setSliceSpace(4f);
        pieDataSet.setValueTextSize(14f);
        pieDataSet.setValueTextColor(Color.WHITE);

        // Gán màu ngẫu nhiên cho từng phần
        List<Integer> generatedColors = new ArrayList<>();
        Random rnd = new Random();
        for (int i = 0; i < entries.size(); i++) {
            int color = Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            generatedColors.add(color);
        }
        pieDataSet.setColors(generatedColors);

        // Dữ liệu PieChart
        PieData pieData = new PieData(pieDataSet);
        pieChart.setUsePercentValues(true);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieChart.setData(pieData);

        // Tùy chỉnh giao diện
        pieChart.setDrawEntryLabels(true);
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTextSize(14f);
        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1000);

        // Thiết lập chú thích
        Legend legend = pieChart.getLegend();
        legend.setEnabled(true);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setFormSize(30f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);

        pieChart.invalidate(); // Vẽ lại biểu đồ
    }

    private double tinhTongDoanhThuTrongBieuDo() {
        ArrayList<DoanhThu> list = db.getThongKeDoanhThuTheoMaNhomMon();
        double sum = 0;
        for (DoanhThu tt : list) {
            sum += tt.getTongTien();
        }
        return sum;
    }
}
