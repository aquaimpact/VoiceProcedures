package com.example.voiceprocedures.Main_Components;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.R;
import com.example.voiceprocedures.RecyclerView.RecyclerViewAdapterChapt;
import com.example.voiceprocedures.RecyclerView.RecyclerViewAdapterSubchapt;

public class Subchapters extends AppCompatActivity implements RecyclerViewAdapterSubchapt.onsSubChaptClickListener {

    RecyclerView recyclerView;
    DatabaseHelper db;
    RecyclerViewAdapterSubchapt adapter;
    TextView chaptnameFSubchapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subchapters);

        recyclerView = findViewById(R.id.recyclersubchapt);
        chaptnameFSubchapt = findViewById(R.id.headerchapts);

        db = new DatabaseHelper(this);
        Intent intent = getIntent();
        String chaptID = intent.getStringExtra("ID");
        String chaptname = intent.getStringExtra("NAME");
        chaptnameFSubchapt.setText(chaptname);

        adapter = new RecyclerViewAdapterSubchapt(this, db.allsubchapterdataM(chaptID), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onSubChaptClick(int position) {

    }
}
