package com.example.voiceprocedures.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.R;
import com.example.voiceprocedures.RecyclerView.RecyclerViewAdapterChapt;

public class SubChapterFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseHelper db;
    RecyclerViewAdapterChapt adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View RootView = inflater.inflate(R.layout.frag_subchapter, container, false);

        recyclerView = RootView.findViewById(R.id.recyclerchapt1);

        getActivity().setTitle("On-Board Communications");

        db = new DatabaseHelper(getActivity());
        adapter = new RecyclerViewAdapterChapt(getContext(), db.allchapterdataM(1));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return RootView;
    }

}
