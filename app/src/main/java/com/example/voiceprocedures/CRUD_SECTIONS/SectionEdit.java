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
    Spinner subchaptlinkedto, transSUBCHAPTlinkedE;
    Button edit;
    Integer ii, value2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_edit);

        this.setTitle("Edit Section Information");

        sectName = (EditText) findViewById(R.id.sectnameE);
        edit = (Button) findViewById(R.id.editsect);
        subchaptlinkedto = (Spinner) findViewById(R.id.sectlinkedE);
        results = (TextView) findViewById(R.id.wowSectE);
        transSUBCHAPTlinkedE = findViewById(R.id.translollinkedE);
        db = new DatabaseHelper(this);

        List<String> items = db.allsubchapterdatas();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subchaptlinkedto.setOnItemSelectedListener(this);


        List<String> item2 = db.alltransdatas();
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, item2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transSUBCHAPTlinkedE.setOnItemSelectedListener(this);

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

        String transid = cursors.getString(cursors.getColumnIndex("transcriptID"));
        Cursor trans = db.transdetailsid(transid);
        if (trans.getCount()  == 0){
            System.out.println("NULL");
        }
        trans.moveToFirst();
        String txt = trans.getString(trans.getColumnIndex("transcriptName"));
        ii = item2.indexOf(txt);

        transSUBCHAPTlinkedE.setAdapter(dataAdapter2);
        transSUBCHAPTlinkedE.setSelection(ii);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chaptnamee = sectName.getText().toString().trim();
                String result = results.getText().toString().trim();

                if (chaptnamee.length() > 0) {

                    db.editSection(subchaptNames,chaptnamee, result, value2);

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
        Spinner spin = (Spinner)parent;
        // On selecting a spinner item
        if(spin.getId() == R.id.sectlinkedE){
            String subchaptlabel = parent.getItemAtPosition(position).toString();
            Cursor cursor = db.subchaptDetails(subchaptlabel);
            cursor.moveToFirst();
            results.setText(cursor.getString(3));
//            System.out.println("The Text result1 is:" + results1.getText().toString().trim());
        }
        else if (spin.getId() == R.id.translollinkedE){
            String translabel = parent.getItemAtPosition(position).toString();
            System.out.println(translabel);
            Cursor trans = db.transDetails2(translabel);
            trans.moveToFirst();
            value2 = Integer.parseInt(trans.getString(trans.getColumnIndex("transcriptID")));

//            System.out.println("The Text result2 is:" + results2.getText().toString().trim());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
