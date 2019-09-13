package com.example.voiceprocedures.Main_Components;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.MainActivity;
import com.example.voiceprocedures.R;

import java.io.BufferedReader;
import java.io.StringReader;

public class FinishScreen extends AppCompatActivity {

    TextView finaltrans;
    Button goback,gofin;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_screen);

        finaltrans = findViewById(R.id.finaltrans);
        goback = findViewById(R.id.goback);
        gofin = findViewById(R.id.gofin);

        Intent intent = getIntent();
        final String transID = intent.getStringExtra("ID");
        final String name = intent.getStringExtra("NAME");
        final String usertype = intent.getStringExtra("speak");

        db = new DatabaseHelper(this);

        Cursor cursor = db.transdetailsid(transID);

        cursor.moveToFirst();

        final String in = cursor.getString(cursor.getColumnIndex("transcript"));
        Integer len = 0;
        BufferedReader reader = new BufferedReader(new StringReader(in));

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

//        System.out.println(lol);
        finaltrans.setText(lol);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinishScreen.this, PracticePage.class);
                intent.putExtra("ID", transID);
                intent.putExtra("NAME", name);
                intent.putExtra("speak",usertype );
                startActivity(intent);
            }
        });

        gofin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinishScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
