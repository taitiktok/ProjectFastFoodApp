    package com.example.fastfoodstore.Views.MonAn;

    import android.content.Intent;
    import android.os.Bundle;
    import android.text.Editable;
    import android.text.TextWatcher;
    import android.util.Log;
    import android.widget.EditText;
    import android.widget.ImageView;

    import androidx.activity.EdgeToEdge;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.Toolbar;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.fastfoodstore.Model.DanhMuc.MySqliteDBDanhMuc;
    import com.example.fastfoodstore.Model.MonAn.MonAn;
    import com.example.fastfoodstore.Model.MonAn.RecycleViewMonAn;
    import com.example.fastfoodstore.R;

    import java.util.ArrayList;

    public class QuanLyMonAn extends AppCompatActivity {
        ImageView addMonAn;
        Toolbar toolbar;
        RecyclerView recyclerView;
        EditText searchMonAn;
        RecycleViewMonAn recycleViewMonAn;
        ArrayList<MonAn> listMonAn;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_quan_ly_mon_an);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
            mapping();
            setAdapter();
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Quản lý món");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            toolbar.setNavigationOnClickListener(v->{
                finish();
            });
            addMonAn.setOnClickListener(v->{
                Intent intent = new Intent(QuanLyMonAn.this, AddMonAn.class);
                startActivity(intent);
            });
            searchMonAn.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                     recycleViewMonAn.filter(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
        public void mapping(){
            toolbar = findViewById(R.id.toolbarQuanLyMonAn);
            addMonAn = findViewById(R.id.addMonAn);
            recyclerView = findViewById(R.id.recycleViewQlMA);
            searchMonAn = findViewById(R.id.txtSearchMonAn);
            listMonAn = new ArrayList<>();
        }
        public void setAdapter(){
            MySqliteDBDanhMuc db = new MySqliteDBDanhMuc(this);
            listMonAn = db.getDataMonAn();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recycleViewMonAn = new RecycleViewMonAn(this, listMonAn);
            recyclerView.setAdapter(recycleViewMonAn);
        }

        @Override
        protected void onResume() {
            super.onResume();
            setAdapter();
        }

        @Override
        protected void onStart() {
            Log.d("2w","dang ow onstat");
            super.onStart();
        }
    }