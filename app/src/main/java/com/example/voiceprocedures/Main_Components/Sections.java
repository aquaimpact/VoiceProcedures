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
import com.example.voiceprocedures.RecyclerView.RecyclerViewAdapterSubchapt;
import com.example.voiceprocedures.RecyclerView.Subchapt;

import java.util.List;

public class Sections extends AppCompatActivity implements RecyclerViewAdapterSubchapt.onsSubChaptClickListener{

    RecyclerView recyclerView;
    DatabaseHelper db;
    RecyclerViewAdapterSubchapt adapter;
    TextView chaptnameFSubchapt;
    private String subchaptID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sections);

        recyclerView = findViewById(R.id.recyclersect);
        chaptnameFSubchapt = findViewById(R.id.headersubchapts);

        this.setTitle("Sections");

        db = new DatabaseHelper(this);
        Intent intent = getIntent();
        subchaptID = intent.getStringExtra("ID");
        String chaptname = intent.getStringExtra("NAME");
        chaptnameFSubchapt.setText(chaptname);

        adapter = new RecyclerViewAdapterSubchapt(this, db.allsectdataM(subchaptID), this, 0);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onSubChaptClick(int position) {
        List<Subchapt> items = db.allsectdataM(subchaptID);
        Subchapt main = items.get(position);
        String id = main.getID();
        String transID = main.getTransid();
        String name = main.getSubchaptname();
        if (transID != null) {
            Intent intent = new Intent(this, DescriptionTranscript.class);
            intent.putExtra("ID", transID);
            intent.putExtra("NAME", name);
            startActivity(intent);
        }else{
            Toast.makeText(this, "There is nothing here! Coming soon", Toast.LENGTH_SHORT).show();
        }
    }
}
