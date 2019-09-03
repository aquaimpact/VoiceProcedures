package com.example.voiceprocedures.CRUD_SECTIONS;

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

import com.example.voiceprocedures.CRUD_SUBCHAPTER.SubChapterEdit;
import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.MainActivity;
import com.example.voiceprocedures.R;

import java.util.List;

public class SectionEdit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseHelper db;
    SharedPreferences prf;
    EditText sectName;
    TextView results;
    Spinner subchaptlinkedto;
    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_edit);

        this.setTitle("Edit Section Information");

        sectName = (EditText) findViewById(R.id.sectnameE);
        edit = (Button) findViewById(R.id.editsect);
        subchaptlinkedto = (Spinner) findViewById(R.id.sectlinkedE);
        results = (TextView) findViewById(R.id.wowSectE);
        db = new DatabaseHelper(this);

        List<String> items = db.allsubchapterdatas();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        subchaptlinkedto.setOnItemSelectedListener(this);

        prf = getSharedPreferences("sectionCreationDetails", MODE_PRIVATE);

        final String subchaptNames = prf.getString("sectName", null);

        Cursor cursors = db.sectDetails(subchaptNames);
        cursors.moveToFirst();

//        System.out.println(cursors.getString(cursors.getColumnIndex("chapterName")));

        int i= items.indexOf(cursors.getString(cursors.getColumnIndex("subchapterName")));

        System.out.println(i);

        subchaptlinkedto.setAdapter(dataAdapter);
        subchaptlinkedto.setSelection(i);

        sectName.setText(subchaptNames);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chaptnamee = sectName.getText().toString().trim();
                String result = results.getText().toString().trim();

                if (chaptnamee.length() > 0) {

                    db.editSection(subchaptNames,chaptnamee, result);

                    Toast.makeText(SectionEdit.this, "Successfully Edited Section!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SectionEdit.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(SectionEdit.this, "One of the important Fields has not been filled in!", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String label = parent.getItemAtPosition(position).toString();

        Cursor cursor = db.subchaptDetails(label);

        cursor.moveToFirst();

        results.setText(cursor.getString(2));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
