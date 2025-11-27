package com.example.aplikasiloginmodern;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ListPantiAdapter pantiAdapter;
    private List<Panti> pantiList;
    private DatabaseHelper databaseHelper;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewPanti);
        progressBar = view.findViewById(R.id.progressBar);

        databaseHelper = new DatabaseHelper();
        pantiList = new ArrayList<>();

        pantiAdapter = new ListPantiAdapter(getContext());
        pantiAdapter.setData(pantiList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(pantiAdapter);

        loadData();

        return view;
    }

    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);

        databaseHelper.getAllPanti(new DatabaseHelper.PantiListCallback() {
            @Override
            public void onSuccess(ArrayList<Panti> list) {
                progressBar.setVisibility(View.GONE);

                pantiList.clear();
                pantiList.addAll(list);

                pantiAdapter.setData(pantiList);
            }

            @Override
            public void onFailure(Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
