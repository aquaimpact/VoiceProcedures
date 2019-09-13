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
        final String transID = intent.getStringExtra("ID");
        final String name = intent.getStringExtra("NAME");

        headertrans = findViewById(R.id.headertrans);
        transtxt = findViewById(R.id.transtxt);
        proceed = findViewById(R.id.proceed);

        Cursor cursor = db.transdetailsid(transID);

        cursor.moveToFirst();

        final String in = cursor.getString(cursor.getColumnIndex("transcript"));
        Integer len = 0;
        BufferedReader reader = new BufferedReader(new StringReader(in));

        headertrans.setText(name);

        String lol = "";

        // Text Conversion
        try {
            String line = reader.readLine();
            while (line != null){
                String[] p1;
                String p11;
                String[] p2;
                String p22;
                if(line.substring(1, 3).equals("P1")){
                    if(line.substring(4, 6).trim().equals("~")){
                        p1 = line.split(" ~");
                        p11 = "\t\t\t" + "- " + p1[1] + "\n";
                        lol += p1[0] + p11;
                    }else {
                        lol += line + "\n";

                    }
                }else if (line.substring(1, 3).equals("P2")){
                    if(line.substring(4, 6).trim().equals("~")){
                        p2 = line.split(" ~");
                        p22 = "\t\t\t" + "- " + p2[1] + "\n";
                        lol += p2[0] + p22;
                    }else {
                        lol += line + "\n";
                    }
                }
                line = reader.readLine();



            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        lol = lol.replace("S", "");
        lol = lol.replace("A", "");
//        lol = lol.replace("P1", "Speaker A");
//        lol = lol.replace("P2", "Speaker B");
//        System.out.println(lol);
        transtxt.setText(lol);

        build1 = new AlertDialog.Builder(this);
        build1.setMessage("Practice as speaker:");
        build1.setTitle("Choose Speaker to begin");
        build1.setCancelable(true);

        build1.setPositiveButton("Speaker B", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(DescriptionTranscript.this, PracticePage.class);
                intent.putExtra("speak", "b");
                intent.putExtra("ID", transID);
                intent.putExtra("NAME", name);
                startActivity(intent);
            }
        });

        build1.setNegativeButton("Speaker A", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(DescriptionTranscript.this, PracticePage.class);
                intent.putExtra("speak", "a");
                intent.putExtra("ID", transID);
                intent.putExtra("NAME", name);
                startActivity(intent);
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
