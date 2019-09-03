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

import com.example.voiceprocedures.CRUD_STUDENT.StudentEdit;
import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.MainActivity;
import com.example.voiceprocedures.R;

public class ChapterEdit extends AppCompatActivity {

    DatabaseHelper db;
    SharedPreferences prf;
    TextView chaptNames;
    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_edit);

        this.setTitle("Edit Chapter Information");

        db = new DatabaseHelper(this);
        prf = getSharedPreferences("chapterCreationDetails", MODE_PRIVATE);
        chaptNames = (TextView) findViewById(R.id.chaptnameq);
        edit = (Button) findViewById(R.id.editchapt);

        final String chaptName = prf.getString("chaptName", null);

        Cursor cursor = db.chaptDetails(chaptName);
        if (cursor.getCount() == 0) {
            System.out.println("NULL");
        }
        cursor.moveToFirst();
        chaptNames.setText(cursor.getString(cursor.getColumnIndex("chapterName")));

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chaptnamee = chaptNames.getText().toString().trim();

                if (chaptnamee.length() > 0) {

                    db.editChapter(chaptName, chaptnamee);

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
}
