package com.example.voiceprocedures.CRUD_SECTIONS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.R;

public class SectionDetails extends AppCompatActivity {

    DatabaseHelper db;
    SharedPreferences prf;
    TextView sectID, sectName, subchaptLinked, chaptlinked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_details);

        this.setTitle("Detailed Sub Chapter Information");

        db = new DatabaseHelper(this);
        prf = getSharedPreferences("sectionCreationDetails",MODE_PRIVATE);
        sectID = (TextView) findViewById(R.id.detailsectID);
        sectName = (TextView) findViewById(R.id.detailsectName);
        subchaptLinked = (TextView) findViewById(R.id.detailsectlinked);
        chaptlinked = (TextView) findViewById(R.id.detailchaptlinked);

        String sectNames = prf.getString("sectName", null);
        System.out.println(sectNames);


        Cursor cursor = db.sectDetails(sectNames);
        if (cursor.getCount()  == 0){
            System.out.println("NULL");
        }
        cursor.moveToFirst();


        sectID.setText(cursor.getString(5));
        System.out.println("LOL1");

        sectName.setText(cursor.getString(cursor.getColumnIndex("sectionName")));
        System.out.println("LOL2");

        subchaptLinked.setText(cursor.getString(cursor.getColumnIndex("subchapterName")));
        System.out.println("LOL3");

        chaptlinked.setText(cursor.getString(cursor.getColumnIndex("chapterName")));
        System.out.println("LOL4");
    }
}
