package com.example.voiceprocedures.CRUD_CHAPTER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voiceprocedures.CRUD_STUDENT.StudentEdit;
import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.MainActivity;
import com.example.voiceprocedures.R;

import java.util.ArrayList;
import java.util.List;

public class ChapterEdit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseHelper db;
    SharedPreferences prf;
    TextView chaptNames;
    Button edit;
    Spinner commstype;
    private Integer value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_edit);

        this.setTitle("Edit Chapter Information");

        db = new DatabaseHelper(this);
        prf = getSharedPreferences("chapterCreationDetails", MODE_PRIVATE);
        chaptNames = (TextView) findViewById(R.id.chaptnameq);
        edit = (Button) findViewById(R.id.editchapt);
        commstype = (Spinner) findViewById(R.id.chaptercommsE);

        final String chaptName = prf.getString("chaptName", null);

        Cursor cursor = db.chaptDetails(chaptName);
        if (cursor.getCount() == 0) {
            System.out.println("NULL");
        }
        cursor.moveToFirst();
        chaptNames.setText(cursor.getString(cursor.getColumnIndex("chapterName")));

        List<String> items = new ArrayList<String>();
        items.add("External Communications");
        items.add("On-board Communications");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        commstype.setOnItemSelectedListener(this);
        commstype.setAdapter(dataAdapter);

        int i= items.indexOf(cursor.getString(cursor.getColumnIndex("communicationType")));

        System.out.println(i);

        commstype.setAdapter(dataAdapter);
        commstype.setSelection(i);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chaptnamee = chaptNames.getText().toString().trim();

                if (chaptnamee.length() > 0) {

                    db.editChapter(chaptName, chaptnamee, value);

                    Toast.makeText(ChapterEdit.this, "Successfully Edited Chapter!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ChapterEdit.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(ChapterEdit.this, "One of the important Fields has not been filled in!", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        String label = parent.getItemAtPosition(position).toString();
        if (label.equals("External Communications")){
            value = 0;
        }else if (label.equals("On-board Communications")) {
            value = 1;
//            Toast.makeText(this, label, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
