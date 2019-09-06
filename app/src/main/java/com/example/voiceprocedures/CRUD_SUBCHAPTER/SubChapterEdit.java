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
    Spinner chaptlinkedto, transSUBCHAPTlinkedE;
    Button edit;
    Integer ii;
    Integer value2;
    String value1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_chapter_edit);

        this.setTitle("Edit Sub Chapter Information");

        subchaptName = (EditText) findViewById(R.id.subchaptnameE);
        edit = (Button) findViewById(R.id.editsubChapter);
        chaptlinkedto = (Spinner) findViewById(R.id.chapterlinkedE);
        transSUBCHAPTlinkedE = findViewById(R.id.transSUBCHAPTlinkedE);
        db = new DatabaseHelper(this);

        prf = getSharedPreferences("subchapterCreationDetails", MODE_PRIVATE);

        List<String> items = db.allchapterdatas();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chaptlinkedto.setOnItemSelectedListener(this);

        List<String> item2 = db.alltransdatas();
        item2.add(0, "Not Directly Linked To Any Transcript");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, item2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transSUBCHAPTlinkedE.setOnItemSelectedListener(this);

        final String subchaptNames = prf.getString("subchaptName", null);

        Cursor cursors = db.subchaptDetails(subchaptNames);
        cursors.moveToFirst();

        int i= items.indexOf(cursors.getString(cursors.getColumnIndex("chapterName")));

        chaptlinkedto.setAdapter(dataAdapter);
        chaptlinkedto.setSelection(i);

        if (cursors.getString(cursors.getColumnIndex("transcriptID")) == null){
            ii = 0;
        }else{
            String transid = cursors.getString(cursors.getColumnIndex("transcriptID"));
            Cursor trans = db.transdetailsid(transid);
            if (trans.getCount()  == 0){
                System.out.println("NULL");
            }
            trans.moveToFirst();
            String txt = trans.getString(trans.getColumnIndex("transcriptName"));
            ii = item2.indexOf(txt);
        }

        transSUBCHAPTlinkedE.setAdapter(dataAdapter2);
        transSUBCHAPTlinkedE.setSelection(ii);

        subchaptName.setText(subchaptNames);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chaptnamee = subchaptName.getText().toString().trim();

                if (chaptnamee.length() > 0) {

                    System.out.println("Value1: " + subchaptNames);
                    System.out.println("Value2: " + chaptnamee);
                    System.out.println("chaptID: " + value1);
                    System.out.println("transID: " + value2);

                    db.editSubChapter(subchaptNames,chaptnamee, value1, value2);

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
        Spinner spin = (Spinner)parent;
        // On selecting a spinner item
        if(spin.getId() == R.id.chapterlinkedE){
            String subchaptlabel = parent.getItemAtPosition(position).toString();
            Cursor stu = db.chaptDetails(subchaptlabel);
            stu.moveToFirst();
            value1 = stu.getString(stu.getColumnIndex("ID"));
//            System.out.println("The Text result1 is:" + results1.getText().toString().trim());
        }
        else if (spin.getId() == R.id.transSUBCHAPTlinkedE){
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
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
