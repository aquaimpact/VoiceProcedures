package com.example.voiceprocedures.CRUD_SUBCHAPTER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voiceprocedures.CRUD_CHAPTER.ChapterDelete;
import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.MainActivity;
import com.example.voiceprocedures.R;

public class SubChapterDelete extends AppCompatActivity {

    DatabaseHelper db;
    SharedPreferences prf;
    TextView subchaptID, subchaptName, subchaptLinked;
    Button confirmDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_chapter_delete);

        this.setTitle("Delete Sub Chapter Information");

        db = new DatabaseHelper(this);
        prf = getSharedPreferences("subchapterCreationDetails",MODE_PRIVATE);
        subchaptID = (TextView) findViewById(R.id.detailsubchaptIDD);
        subchaptName = (TextView) findViewById(R.id.detailsubchaptNameD);
        subchaptLinked = (TextView) findViewById(R.id.detailsubchaptlinkedD);
        confirmDelete = (Button) findViewById(R.id.confirmDeletesubchaptD);

        final String chaptNames = prf.getString("subchaptName", null);


        Cursor cursor = db.subchaptDetails(chaptNames);
        if (cursor.getCount()  == 0){
            System.out.println("NULL");
        }

        cursor.moveToFirst();
        subchaptID.setText(cursor.getString(2));
        subchaptName.setText(cursor.getString(cursor.getColumnIndex("subchapterName")));
        subchaptLinked.setText(cursor.getString(cursor.getColumnIndex("chapterName")));

        confirmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deletesubChapter(chaptNames);
//                db.resetrows();
                Toast.makeText(SubChapterDelete.this, "Successfully Deleted! Returning Back..." , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SubChapterDelete.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
