package com.ldt.quanlyca;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

public class AddLoaiCaActivity extends AppCompatActivity {

    private EditText edtTenKH, edtTenThuong, edtMauSac, edtDacTinh, edtUrl;
    private Button btnPaste, btnBrowse, btnAdd, btnCancel;
    private ImageButton btnClear;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loai_ca);

//        mData = FirebaseDatabase.getInstance().getReference();

        anhXa();
        btnPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = clipBoard.getPrimaryClip();
                ClipData.Item item = clipData.getItemAt(0);
                edtUrl.setText(item.getText().toString());
            }
        });

        edtUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Glide.with(AddLoaiCaActivity.this)
                        .load(edtUrl.getText().toString())
                        .placeholder(R.mipmap.ic_launcher) // ảnh mặt định
                        .error(R.mipmap.ic_launcher) // ảnh khi lỗi
                        .into(image);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtUrl.setText("");
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkEdt())
                    Toast.makeText(AddLoaiCaActivity.this, "Not enter enough information!", Toast.LENGTH_SHORT).show();
                else
                    insertData();
            }
        });
    }

    private void insertData() {
        LoaiCa loaiCa = getDataLoaiCaFromActivity();
        FirebaseDatabase.getInstance().getReference().child("LoaiCa").push()
                .setValue(loaiCa)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddLoaiCaActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        clearEdt();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddLoaiCaActivity.this, "Error While Insertion", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private LoaiCa getDataLoaiCaFromActivity() {
        LoaiCa loaiCa = new LoaiCa();
        loaiCa.setTenKH(edtTenKH.getText().toString());
        loaiCa.setTenThuong(edtTenThuong.getText().toString());
        loaiCa.setMauSac(edtMauSac.getText().toString());
        loaiCa.setDacTinh(edtDacTinh.getText().toString());
        loaiCa.setUrlImage(edtUrl.getText().toString());
        return loaiCa;
    }

    private void clearEdt() {
        edtUrl.setText("");
        edtTenKH.setText("");
        edtTenThuong.setText("");
        edtMauSac.setText("");
        edtDacTinh.setText("");
    }

    private boolean checkEdt() {
        if(edtTenKH.getText().toString().equals("") || edtUrl.getText().toString().equals("") || edtTenThuong.getText().toString().equals("") || edtMauSac.getText().toString().equals("") || edtDacTinh.getText().toString().equals("")) {
            return false;
        }
        return true;
    }

    private void anhXa() {
        edtTenKH = findViewById(R.id.edtTenKH);
        edtTenThuong = findViewById(R.id.edtTenThuong);
        edtMauSac = findViewById(R.id.edtMauSac);
        edtDacTinh = findViewById(R.id.edtDacTinh);
        btnPaste = findViewById(R.id.btnPaste);
//        btnBrowse = findViewById(R.id.btnBrowse);
        btnClear = findViewById(R.id.btnClear);
        image = findViewById(R.id.image);
        edtUrl = findViewById(R.id.edtUrl);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);
    }
}