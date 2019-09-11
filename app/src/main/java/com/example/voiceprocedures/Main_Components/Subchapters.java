package com.example.voiceprocedures.Main_Components;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.R;
import com.example.voiceprocedures.RecyclerView.RecyclerViewAdapterChapt;
import com.example.voiceprocedures.RecyclerView.RecyclerViewAdapterSubchapt;
import com.example.voiceprocedures.RecyclerView.Subchapt;

import java.util.List;

public class Subchapters extends AppCompatActivity implements RecyclerViewAdapterSubchapt.onsSubChaptClickListener {

    RecyclerView recyclerView;
    DatabaseHelper db;
    RecyclerViewAdapterSubchapt adapter;
    TextView chaptnameFSubchapt;
    private String chaptID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subchapters);

        recyclerView = findViewById(R.id.recyclersubchapt);
        chaptnameFSubchapt = findViewById(R.id.headerchapts);

        this.setTitle("Sub Chapters");

        db = new DatabaseHelper(this);
        Intent intent = getIntent();
        chaptID = intent.getStringExtra("ID");
        String chaptname = intent.getStringExtra("NAME");
        chaptnameFSubchapt.setText(chaptname);

        adapter = new RecyclerViewAdapterSubchapt(this, db.allsubchapterdataM(chaptID), this, 1);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onSubChaptClick(int position) {
        List<Subchapt> items = db.allsubchapterdataM(chaptID);
        Subchapt main = items.get(position);
        String id = main.getID();
        String name = main.getSubchaptname();
        String count = main.getNoOfSects();
        Integer counts = Integer.parseInt(count);

        if(counts > 0){
            Intent intent = new Intent(this, Sections.class);
            intent.putExtra("ID", id);
            intent.putExtra("NAME", name);
            startActivity(intent);
        }
    }
}
