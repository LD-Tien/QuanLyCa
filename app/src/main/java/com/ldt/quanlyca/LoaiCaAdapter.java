package com.ldt.quanlyca;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoaiCaAdapter extends RecyclerView.Adapter<LoaiCaAdapter.SinhVienViewHolder>{
    private Context context;
    private List<LoaiCa> loaiCaList;
    private List<LoaiCa> loaiCaList2;
    private OnItemListener onMovieListener;
    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    public LoaiCaAdapter(FragmentActivity context, OnItemListener onMovieListener) {
        this.context = context;
        this.onMovieListener = onMovieListener;
    }

    public void setData(List<LoaiCa> loaiCaList) {
        this.loaiCaList = loaiCaList;
        this.loaiCaList2 = loaiCaList;
        notifyDataSetChanged(); // bin/ load dữ liệu vào MovieAdapter
    }

    @NonNull
    @Override
    public SinhVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loaica,parent,false);
        return new SinhVienViewHolder(view, onMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SinhVienViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);
        LoaiCa loaiCa = loaiCaList.get(position);
        if(loaiCa == null) {
            return;
        }
        Glide.with(holder.image.getContext())
                .load(loaiCa.getUrlImage())
                .placeholder(R.mipmap.ic_launcher) // ảnh mặt định
                .circleCrop()
                .error(R.mipmap.ic_launcher) // ảnh khi lỗi
                .into(holder.image);
        holder.tvTenKH.setText("Tên KH: " + loaiCa.getTenKH());
        holder.tvTenThuong.setText("Tên thường: " +loaiCa.getTenThuong());
        holder.tvMauSac.setText("Màu sắc: " + loaiCa.getMauSac());

//        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, UpdateSinhVienActivity.class);
//                intent.putExtra("SinhVien", sinhVienList.get(position));
//                context.startActivity(intent);
//            }
//        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa loài cá");
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mData.child("LoaiCa").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                String msv = snapshot.getValue(LoaiCa.class).getTenKH();
                                if(loaiCaList.get(position).getTenKH().equals(msv)) {
                                    mData.child("LoaiCa").child(snapshot.getKey()).removeValue()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                            });
                                }
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(loaiCaList != null)
            return loaiCaList.size();
        return 0;
    }

    public class SinhVienViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTenKH, tvTenThuong, tvMauSac;
        private ImageButton btnDelete;
        private ImageView image;
        OnItemListener onItemListener;
        public SinhVienViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            this.onItemListener = onItemListener;
            image = itemView.findViewById(R.id.img);
            tvTenKH = itemView.findViewById(R.id.tvTenKhoaHoc);
            tvTenThuong = itemView.findViewById(R.id.tvTenThuong);
            tvMauSac = itemView.findViewById(R.id.tvMauSac);
            btnDelete = itemView.findViewById(R.id.btnDelete);
//            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemListener {
        void onItemClick(int position);
    }

    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if(charSequence == null || charSequence.length() == 0) {
                    filterResults.count = loaiCaList2.size();
                    filterResults.values = loaiCaList2;
                } else {
                    String searchChr = charSequence.toString().toLowerCase();
                    List<LoaiCa> resultData = new ArrayList<>();
                    for(LoaiCa sv: loaiCaList2) {
                        if(sv.getTenThuong().toLowerCase().contains(searchChr)){
                            resultData.add(sv);
                        }
                        filterResults.count = resultData.size();
                        filterResults.values = resultData;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                loaiCaList = (List<LoaiCa>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

}
