package com.example.voiceprocedures.CRUD_SUBCHAPTER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.R;

public class SubChapterDetails extends AppCompatActivity {

    DatabaseHelper db;
    SharedPreferences prf;
    TextView subchaptID, subchaptName, subchaptLinked, translinked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_chapter_details);

        this.setTitle("Detailed Sub Chapter Information");

        db = new DatabaseHelper(this);
        prf = getSharedPreferences("subchapterCreationDetails",MODE_PRIVATE);
        subchaptID = (TextView) findViewById(R.id.detailsubchaptID);
        subchaptName = (TextView) findViewById(R.id.detailsubchaptName);
        subchaptLinked = (TextView) findViewById(R.id.detailsubchaptlinked);
        translinked = findViewById(R.id.detailtranssubchaptlinked);

        String chaptNames = prf.getString("subchaptName", null);
        System.out.println(chaptNames);


        Cursor cursor = db.subchaptDetails(chaptNames);
        if (cursor.getCount()  == 0){
            System.out.println("NULL");
        }
        cursor.moveToFirst();
        subchaptID.setText(cursor.getString(2));
        subchaptName.setText(cursor.getString(cursor.getColumnIndex("subchapterName")));
        subchaptLinked.setText(cursor.getString(cursor.getColumnIndex("chapterName")));
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

    }
}
