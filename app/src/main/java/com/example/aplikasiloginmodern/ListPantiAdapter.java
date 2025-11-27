package com.example.aplikasiloginmodern;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListPantiAdapter extends RecyclerView.Adapter<ListPantiAdapter.ViewHolder> {

    private Context context;
    private List<Panti> pantiList = new ArrayList<>();

    public ListPantiAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Panti> list) {
        this.pantiList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_row_panti, parent, false);  // gunakan layout milikmu
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Panti p = pantiList.get(position);

        // Set data
        holder.txtNama.setText(p.getNamaPanti());
        holder.txtAlamat.setText(p.getAlamatPanti());
        holder.txtRekening.setText(p.getDeskripsiPanti()); // boleh diganti sesuai field yg kamu mau

        // Jika ada URL foto → load dari internet
        if (p.getFotoUrl() != null && !p.getFotoUrl().isEmpty()) {
            Picasso.get()
                    .load(p.getFotoUrl())
                    .fit()
                    .centerCrop()
                    .into(holder.imgPanti);
        }
        // Jika tidak ada → gunakan gambar lokal
        else {
            holder.imgPanti.setImageResource(R.drawable.ulujadi_alikhlas);
        }

        // Klik card → buka detail
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailPantiActivity.class);
            intent.putExtra("pantiId", p.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return pantiList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNama, txtAlamat, txtRekening;
        ImageView imgPanti;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPanti = itemView.findViewById(R.id.img_item_photo);
            txtNama = itemView.findViewById(R.id.tv_item_title);
            txtAlamat = itemView.findViewById(R.id.tv_item_alamat);
            txtRekening = itemView.findViewById(R.id.tv_item_rekening);
        }
    }
}
