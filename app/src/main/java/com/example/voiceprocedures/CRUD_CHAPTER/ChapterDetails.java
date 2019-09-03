package com.example.voiceprocedures.CRUD_CHAPTER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.R;

public class ChapterDetails extends AppCompatActivity {

    DatabaseHelper db;
    SharedPreferences prf;
    TextView chaptID, chaptName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_details);

        this.setTitle("Detailed Chapter Information");

        db = new DatabaseHelper(this);
        prf = getSharedPreferences("chapterCreationDetails",MODE_PRIVATE);
        chaptID = (TextView) findViewById(R.id.detailchaptID);
        chaptName = (TextView) findViewById(R.id.detailchaptName);

        String chaptNames = prf.getString("chaptName", null);

        Cursor cursor = db.chaptDetails(chaptNames);
        if (cursor.getCount()  == 0){
            System.out.println("NULL");
        }
        cursor.moveToFirst();
        chaptID.setText(cursor.getString(cursor.getColumnIndex("ID")));
        chaptName.setText(cursor.getString(cursor.getColumnIndex("chapterName")));

    }
}
