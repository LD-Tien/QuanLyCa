package com.ldt.quanlyca;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ChiTietLoaiCaActivity extends AppCompatActivity {

    private ImageView image;
    private TextView tvTenKH, tvTenThuong, tvMauSac, tvDacTinh;
    private LoaiCa loaiCa;
//    private ImageButton btnDelete, btnEdit, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_loai_ca);

        anhXa();
        setDataDetailsMovie();

    }

    private void anhXa() {
        image = findViewById(R.id.imgMovie);
        tvTenKH = findViewById(R.id.tvTenKhoaHoc);
        tvTenThuong = findViewById(R.id.tvTenThuong);
        tvMauSac = findViewById(R.id.tvMauSac);
        tvDacTinh = findViewById(R.id.tvDacTinh);
//        btnBack = findViewById(R.id.btnBack);
//        btnDelete = findViewById(R.id.btnDelete);
//        btnEdit = findViewById(R.id.btnEdit);
    }

    private void setDataDetailsMovie() {
        loaiCa = (LoaiCa) getIntent().getSerializableExtra("LoaiCa");
        Glide.with(this)
                .load(loaiCa.getUrlImage())
                .placeholder(R.mipmap.ic_launcher) // ảnh mặt định
                .error(R.mipmap.ic_launcher) // ảnh khi lỗi
                .into(image);
        tvTenKH.setText("Tên khoa học: " + loaiCa.getTenKH());
        tvTenThuong.setText(loaiCa.getTenThuong());
        tvMauSac.setText("Màu sắc: " + loaiCa.getMauSac());
        tvDacTinh.setText("Đặc tính: " + loaiCa.getDacTinh());
    }
}