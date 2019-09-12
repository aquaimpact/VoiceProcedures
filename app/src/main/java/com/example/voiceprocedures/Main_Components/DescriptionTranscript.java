package com.example.voiceprocedures.Main_Components;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spannable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DescriptionTranscript extends AppCompatActivity {

    DatabaseHelper db;
    TextView headertrans, transtxt;
    Button proceed;
    AlertDialog.Builder build1;
    AlertDialog person1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_transcript);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        String transID = intent.getStringExtra("ID");
        String name = intent.getStringExtra("NAME");

        headertrans = findViewById(R.id.headertrans);
        transtxt = findViewById(R.id.transtxt);
        proceed = findViewById(R.id.proceed);

        Cursor cursor = db.transdetailsid(transID);

        cursor.moveToFirst();

        headertrans.setText(name);
        transtxt.setText(cursor.getString(cursor.getColumnIndex("transcript")));

        String in = cursor.getString(cursor.getColumnIndex("transcript"));
        Integer len = 0;
        BufferedReader reader = new BufferedReader(new StringReader(in));

        List<String> per1 = new ArrayList<String>();
        List<String> per2 = new ArrayList<String>();

        try {
            String line = reader.readLine();
            while (line != null){

                if(line.substring(0, 2).equals("P1")){
                    per1.add(line);
                }else if (line.substring(0, 2).equals("P2")){
                    per2.add(line);
                }
                line = reader.readLine();
            }
            System.out.println(reader.readLine());
        }catch (Exception e){
            e.printStackTrace();
        }

        build1 = new AlertDialog.Builder(this);
        build1.setMessage("Practice as speaker:");
        build1.setTitle("Choose Speaker to begin");
        build1.setCancelable(true);

        build1.setPositiveButton("Speaker B", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(DescriptionTranscript.this, "You clicked B", Toast.LENGTH_SHORT).show();
            }
        });

        build1.setNegativeButton("Speaker A", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(DescriptionTranscript.this, "You clicked A", Toast.LENGTH_SHORT).show();
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                person1 = build1.create();
                person1.show();
            }
        });
    }
}
