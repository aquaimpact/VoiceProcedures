package com.example.voiceprocedures.Main_Components;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spannable;
import android.widget.TextView;

import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Arrays;

public class DescriptionTranscript extends AppCompatActivity {

    DatabaseHelper db;
    TextView headertrans, transtxt;

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

        Cursor cursor = db.transdetailsid(transID);

        cursor.moveToFirst();

        headertrans.setText(name);
        transtxt.setText(cursor.getString(cursor.getColumnIndex("transcript")));

        String in = cursor.getString(cursor.getColumnIndex("transcript"));
        Integer len = 0;
        BufferedReader reader = new BufferedReader(new StringReader(in));

        try {
            String line = reader.readLine();
            while (line != null){

                if(line.substring(0, 2).equals("P1")){

                    //TODO: Put P1 TXT and P2 TXT in to 2 sepeate lists.

                }
                line = reader.readLine();
            }
            System.out.println(reader.readLine());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
