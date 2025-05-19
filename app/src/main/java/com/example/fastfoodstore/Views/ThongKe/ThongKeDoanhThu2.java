package com.example.fastfoodstore.Views.ThongKe;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fastfoodstore.Model.DoanhThu.MySqliteDBDoanhThu;
import com.example.fastfoodstore.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ThongKeDoanhThu2 extends AppCompatActivity {
    Toolbar toolbar;
    private List<String> xValues = Arrays.asList("Maths","Science","English","IT");
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

        toolbar = findViewById(R.id.toolbarDoanhThu);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Thống kê doanh thu");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        MySqliteDBDoanhThu db = new MySqliteDBDoanhThu(this);
        PieChart pieChart = findViewById(R.id.chart);

        List<PieEntry> entries = db.getPieEntriesDoanhThuTheoMaNhomMon();

        PieDataSet pieDataSet = new PieDataSet(entries, "");

        pieDataSet.setSliceSpace(4f);
        pieDataSet.setValueTextSize(14f);
        pieDataSet.setValueTextColor(Color.WHITE);

        // Dữ liệu cho PieChart
        PieData pieData = new PieData(pieDataSet);

        // Hiển thị giá trị dạng phần trăm (%)
        pieChart.setUsePercentValues(true);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        Legend legend = pieChart.getLegend();
        pieChart.setData(pieData);
        // Hiển thị tên nhãn trên từng lát cắt
        pieChart.setDrawEntryLabels(true);
        pieChart.setEntryLabelColor(Color.WHITE); // Màu chữ tên lát cắt (trắng để nổi trên màu sắc vật liệu)
        pieChart.setEntryLabelTextSize(14f);
        legend.setForm( Legend.LegendForm.SQUARE); // Dạng ô vuông
        legend.setFormSize(30f);
        List<Integer> generatedColors = new ArrayList<>();
        Random rnd = new Random();
        for (int i = 0; i < entries.size(); i++) {
            // Tạo màu RGB ngẫu nhiên, có thể thêm alpha (độ trong suốt) nếu muốn
            int color = Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            generatedColors.add(color);
        }
        pieDataSet.setColors(generatedColors);

        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1000);
        legend.setEnabled(true);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        legend.setDrawInside(false);

        pieChart.invalidate();

    }
}
