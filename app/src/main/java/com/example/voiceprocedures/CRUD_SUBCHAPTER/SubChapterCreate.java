package com.example.voiceprocedures.CRUD_SUBCHAPTER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voiceprocedures.CRUD_CHAPTER.CreateChapter;
import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.MainActivity;
import com.example.voiceprocedures.R;

import java.util.List;

public class SubChapterCreate extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseHelper db;

    EditText subchaptName;
    TextView results;
    Spinner chaptlinkedto, translinkedto;
    Button createSubChapt;

    Integer value2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_chapter_create);

        this.setTitle("Create Sub Chapter");
        value2 = null;

        subchaptName = (EditText) findViewById(R.id.subchaptname);
        subchaptName.setText(null);

        createSubChapt = (Button) findViewById(R.id.createsubChapter);
        chaptlinkedto = (Spinner) findViewById(R.id.chapterlinked);
        translinkedto= findViewById(R.id.translinkedSC);

        results = (TextView) findViewById(R.id.wowSC);
        results.setText(null);

        db = new DatabaseHelper(this);

        List<String> items = db.allchapterdatas();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chaptlinkedto.setOnItemSelectedListener(this);
        chaptlinkedto.setAdapter(dataAdapter);

        List<String> item2 = db.alltransdatas();
        item2.add(0, "Not Directly Linked To Any Transcript");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, item2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        translinkedto.setOnItemSelectedListener(this);
        translinkedto.setAdapter(dataAdapter2);
        translinkedto.setSelection(0);


        createSubChapt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chaptname = subchaptName.getText().toString().trim();
                String result = results.getText().toString().trim();

                if (chaptname.length() > 0 || result.length() > 0){
                    long val = db.addSubChapter(result,chaptname, value2);
                    if(val > 0){
                        Toast.makeText(SubChapterCreate.this, "Successfully Created Sub Chapter!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(SubChapterCreate.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }else{
                        Toast.makeText(SubChapterCreate.this, "Sub Chapter Creation Error!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(SubChapterCreate.this, "One of the important FIELDS has not been filled in!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner item
        Spinner spin = (Spinner)parent;
        // On selecting a spinner item
        if(spin.getId() == R.id.chapterlinked){
            String subchaptlabel = parent.getItemAtPosition(position).toString();
            Cursor stu = db.chaptDetails(subchaptlabel);
            stu.moveToFirst();
            results.setText(stu.getString(stu.getColumnIndex("ID")));
//            System.out.println("The Text result1 is:" + results1.getText().toString().trim());
        }
        else if (spin.getId() == R.id.translinkedSC){
            String translabel = parent.getItemAtPosition(position).toString();
            System.out.println(translabel);
            if (!translabel.equals("Not Directly Linked To Any Transcript")){
                Cursor trans = db.transDetails2(translabel);
                trans.moveToFirst();
                value2 = Integer.parseInt(trans.getString(trans.getColumnIndex("transcriptID")));
            }else{
                value2 = null;
            }

//            System.out.println("The Text result2 is:" + results2.getText().toString().trim());
        }
        // Showing selected spinner item
//        Toast.makeText(parent.getContext(), "You selected: " + label,
//                Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
