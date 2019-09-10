package com.example.voiceprocedures.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.Main_Components.Subchapters;
import com.example.voiceprocedures.R;
import com.example.voiceprocedures.RecyclerView.Chapters;
import com.example.voiceprocedures.RecyclerView.RecyclerViewAdapterChapt;

import java.util.List;

public class ChapterFragment extends Fragment implements RecyclerViewAdapterChapt.onChaptClickListener {

    RecyclerView recyclerView;
    DatabaseHelper db;
    RecyclerViewAdapterChapt adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.frag_chapter, container, false);

        recyclerView = RootView.findViewById(R.id.recyclerchapt);

        getActivity().setTitle("External Communications");

        db = new DatabaseHelper(getActivity());
        adapter = new RecyclerViewAdapterChapt(getContext(), db.allchapterdataM(0), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return RootView;
    }

    @Override
    public void onChaptClick(int position) {
        List<Chapters> items = db.allchapterdataM(0);
        String ID = items.get(position).getId();
        String Name = items.get(position).getChaptname();
        Intent intent = new Intent(getContext(), Subchapters.class);
        intent.putExtra("ID", ID);
        intent.putExtra("NAME", Name);
        startActivity(intent);
    }
}
