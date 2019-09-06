package com.example.voiceprocedures.CRUD_SECTIONS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.R;

public class SectionDetails extends AppCompatActivity {

    DatabaseHelper db;
    SharedPreferences prf;
    TextView sectID, sectName, subchaptLinked, chaptlinked, translinked;

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
        translinked = findViewById(R.id.detailtransSECTlinked);

        String sectNames = prf.getString("sectName", null);
        System.out.println(sectNames);

        Cursor cursor = db.sectDetails(sectNames);
        if (cursor.getCount()  == 0){
            System.out.println("NULL");
        }
        cursor.moveToFirst();

        String transid = cursor.getString(cursor.getColumnIndex("transcriptID"));

        if (transid != null){
            Cursor trans = db.transdetailsid(transid);
            if (trans.getCount()  == 0){
                System.out.println("NULL");
            }
            trans.moveToFirst();
            translinked.setText(trans.getString(trans.getColumnIndex("transcriptName")));
        }else{
            translinked.setText("This sub-chapter is not directly linked to any transcripts!");
            translinked.setTextColor(Color.RED);
        }


        sectID.setText(cursor.getString(5));

        sectName.setText(cursor.getString(cursor.getColumnIndex("sectionName")));

        subchaptLinked.setText(cursor.getString(cursor.getColumnIndex("subchapterName")));

        chaptlinked.setText(cursor.getString(cursor.getColumnIndex("chapterName")));
    }
}
