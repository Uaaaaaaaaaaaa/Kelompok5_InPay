package com.example.aplikasiloginmodern;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;
import com.squareup.picasso.Picasso;

public class DetailPantiActivity extends AppCompatActivity {

    private ImageView imgDetailPhoto;
    private TextView tvDetailTitle, tvDetailAlamat, tvDetailRekening;
    private DatabaseHelper databaseHelper;
    private String pantiId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_panti);

        // Ambil ID dari intent (wajib)
        pantiId = getIntent().getStringExtra("pantiId");

        if (pantiId == null || pantiId.trim().isEmpty()) {
            Toast.makeText(this, "ID panti tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Setup view
        imgDetailPhoto = findViewById(R.id.img_detail_photo);
        tvDetailTitle = findViewById(R.id.tv_detail_title);
        tvDetailAlamat = findViewById(R.id.tv_detail_alamat);
        tvDetailRekening = findViewById(R.id.tv_detail_rekening);

        databaseHelper = new DatabaseHelper();

        loadDetailPanti();
    }

    private void loadDetailPanti() {
        databaseHelper.getPantiById(pantiId, new DatabaseHelper.PantiCallback() {
            @Override
            public void onSuccess(Panti p) {

                if (p == null) {
                    Toast.makeText(DetailPantiActivity.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                    return;
                }

                tvDetailTitle.setText(p.getNamaPanti());
                tvDetailAlamat.setText("Alamat: " + p.getAlamatPanti());
                tvDetailRekening.setText("Rekening: " + (p.getDeskripsiPanti() == null ? "-" : p.getDeskripsiPanti()));

                if (p.getFotoUrl() != null && !p.getFotoUrl().isEmpty()) {
                    Picasso.get()
                            .load(p.getFotoUrl())
                            .fit()
                            .centerCrop()
                            .into(imgDetailPhoto);
                }
            }

            @Override
            public void onFailure(DatabaseError error) {
                Toast.makeText(DetailPantiActivity.this, "Gagal memuat detail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
