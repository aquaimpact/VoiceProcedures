package com.example.voiceprocedures.CRUD_CHAPTER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.MainActivity;
import com.example.voiceprocedures.R;

public class CreateChapter extends AppCompatActivity {

    DatabaseHelper db;

    EditText chaptName;
    Button createChapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chapter);

        this.setTitle("Create Chapter");

        chaptName = (EditText) findViewById(R.id.chaptername);
        createChapt = (Button) findViewById(R.id.createChapter);

        chaptName.setText(null);

        db = new DatabaseHelper(this);

        createChapt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chaptname = chaptName.getText().toString().trim();

                if (chaptname.length() > 0){
                    long val = db.addChapter(chaptname);
                    if(val > 0){
                        Toast.makeText(CreateChapter.this, "Successfully Created Chapter!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(CreateChapter.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }else{
                        Toast.makeText(CreateChapter.this, "Chapter Creation Error!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(CreateChapter.this, "One of the important FIELDS has not been filled in!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
