package com.example.voiceprocedures.CRUD_CHAPTER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voiceprocedures.CRUD_STUDENT.StudentDelete;
import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.MainActivity;
import com.example.voiceprocedures.R;

public class ChapterDelete extends AppCompatActivity {

    DatabaseHelper db;
    SharedPreferences prf;
    TextView chaptID, chaptName;
    Button confirmDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_delete);

        this.setTitle("Delete Chaper Information");

        db = new DatabaseHelper(this);
        prf = getSharedPreferences("chapterCreationDetails",MODE_PRIVATE);
        chaptID = (TextView) findViewById(R.id.detailchaptIDs);
        chaptName = (TextView) findViewById(R.id.detailchaptNames);
        confirmDelete = (Button) findViewById(R.id.confirmDeletechapt);

        final String chaptNames = prf.getString("chaptName", null);


        Cursor cursor = db.chaptDetails(chaptNames);
        if (cursor.getCount()  == 0){
            System.out.println("NULL");
        }

        cursor.moveToFirst();
        chaptID.setText(cursor.getString(cursor.getColumnIndex("ID")));
        chaptName.setText(cursor.getString(cursor.getColumnIndex("chapterName")));
        
        confirmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteChapter(chaptNames);
//                db.resetrows();
                Toast.makeText(ChapterDelete.this, "Successfully Deleted! Returning Back..." , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChapterDelete.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
