package com.ldt.quanlyca;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListLoaiCaActivity extends AppCompatActivity implements LoaiCaAdapter.OnItemListener {
    private FloatingActionButton btnAddSV;
    private DatabaseReference mData;
    private List<LoaiCa> loaiCaList;
    private RecyclerView recyclerView;
    private LoaiCaAdapter loaiCaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_loai_ca);
        anhXa();

        loaiCaList = new ArrayList<>();
        mData = FirebaseDatabase.getInstance().getReference();

        loaiCaList = getDSLoaiCa();
        loaiCaAdapter = new LoaiCaAdapter(this, this);
        loaiCaAdapter.setData(loaiCaList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(loaiCaAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        btnAddSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListLoaiCaActivity.this, AddLoaiCaActivity.class);
                startActivity(intent);
            }
        });


    }

    private List<LoaiCa> getDSLoaiCa() {
        List<LoaiCa> dssv = new ArrayList<>();
        mData.child("LoaiCa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loaiCaList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dssv.add(dataSnapshot.getValue(LoaiCa.class));
                }
                loaiCaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return dssv;
    }

//    private void createData() {
//        List<SinhVien> sinhVienList = new ArrayList<>();
//        sinhVienList.add(new SinhVien("2050531200309", "Lê Đức Tiên", "122LTTD01", 10.0));
//        sinhVienList.add(new SinhVien("1111", "aaaaa", "aaa", 9.0));
//        sinhVienList.add(new SinhVien("2222", "bbbbb", "bbb", 8.0));
//        sinhVienList.add(new SinhVien("3333", "ccccc", "ccc", 7.0));
//
//        DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
//
//        for(SinhVien sv : sinhVienList) {
//            mData.child("DSSV").push().setValue(sv);
//        }
//    }

    private void anhXa() {
        btnAddSV = findViewById(R.id.floatingActionButtonAddSV);
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    public void onItemClick(int position) {
        LoaiCa loaiCa = loaiCaList.get(position);
        Intent intent = new Intent(this, ChiTietLoaiCaActivity.class);
        intent.putExtra("LoaiCa", loaiCa);
        startActivity(intent);
    }
}