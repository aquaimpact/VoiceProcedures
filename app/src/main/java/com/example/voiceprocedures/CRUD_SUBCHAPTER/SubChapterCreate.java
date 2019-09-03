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
    Spinner chaptlinkedto;
    Button createSubChapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_chapter_create);

        this.setTitle("Create Sub Chapter");

        subchaptName = (EditText) findViewById(R.id.subchaptname);
        subchaptName.setText(null);

        createSubChapt = (Button) findViewById(R.id.createsubChapter);

        chaptlinkedto = (Spinner) findViewById(R.id.chapterlinked);

        results = (TextView) findViewById(R.id.wowSC);
        results.setText(null);

        db = new DatabaseHelper(this);

        List<String> items = db.allchapterdatas();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);


        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        chaptlinkedto.setOnItemSelectedListener(this);

        chaptlinkedto.setAdapter(dataAdapter);

        createSubChapt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chaptname = subchaptName.getText().toString().trim();
                String result = results.getText().toString().trim();

                if (chaptname.length() > 0 || result.length() > 0){
                    long val = db.addSubChapter(result,chaptname);
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
        String label = parent.getItemAtPosition(position).toString();

        Cursor cursor = db.chaptDetails(label);

        cursor.moveToFirst();

        results.setText(cursor.getString(cursor.getColumnIndex("ID")));

        // Showing selected spinner item
//        Toast.makeText(parent.getContext(), "You selected: " + label,
//                Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
