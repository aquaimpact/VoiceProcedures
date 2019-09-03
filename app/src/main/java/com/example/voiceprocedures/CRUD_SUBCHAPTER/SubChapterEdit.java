package com.example.voiceprocedures.CRUD_SUBCHAPTER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.voiceprocedures.CRUD_CHAPTER.ChapterEdit;
import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.MainActivity;
import com.example.voiceprocedures.R;

import java.util.List;

public class SubChapterEdit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseHelper db;
    SharedPreferences prf;
    EditText subchaptName;
    TextView results;
    Spinner chaptlinkedto;
    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_chapter_edit);

        this.setTitle("Edit Sub Chapter Information");

        subchaptName = (EditText) findViewById(R.id.subchaptnameE);
        edit = (Button) findViewById(R.id.editsubChapter);
        chaptlinkedto = (Spinner) findViewById(R.id.chapterlinkedE);
        results = (TextView) findViewById(R.id.wowSCE);
        db = new DatabaseHelper(this);

        List<String> items = db.allchapterdatas();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        chaptlinkedto.setOnItemSelectedListener(this);

        prf = getSharedPreferences("subchapterCreationDetails", MODE_PRIVATE);

        final String subchaptNames = prf.getString("subchaptName", null);

        Cursor cursors = db.subchaptDetails(subchaptNames);
        cursors.moveToFirst();

//        System.out.println(cursors.getString(cursors.getColumnIndex("chapterName")));

        int i= items.indexOf(cursors.getString(cursors.getColumnIndex("chapterName")));

        System.out.println(i);

        chaptlinkedto.setAdapter(dataAdapter);
        chaptlinkedto.setSelection(i);

        subchaptName.setText(subchaptNames);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chaptnamee = subchaptName.getText().toString().trim();
                String result = results.getText().toString().trim();

                if (chaptnamee.length() > 0) {

                    db.editSubChapter(subchaptNames,chaptnamee, result);

                    Toast.makeText(SubChapterEdit.this, "Successfully Edited Sub Chapter!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SubChapterEdit.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(SubChapterEdit.this, "One of the important Fields has not been filled in!", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String label = parent.getItemAtPosition(position).toString();

        Cursor cursor = db.chaptDetails(label);

        cursor.moveToFirst();

        results.setText(cursor.getString(cursor.getColumnIndex("ID")));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
