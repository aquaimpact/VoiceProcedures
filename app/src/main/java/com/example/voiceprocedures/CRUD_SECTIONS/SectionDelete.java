package com.example.voiceprocedures.CRUD_SECTIONS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voiceprocedures.CRUD_SUBCHAPTER.SubChapterDelete;
import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.MainActivity;
import com.example.voiceprocedures.R;

public class SectionDelete extends AppCompatActivity {

    DatabaseHelper db;
    SharedPreferences prf;
    TextView sectID, sectName, subchaptLinked, chaptLinked, translinked;
    Button confirmDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_delete);

        this.setTitle("Delete Sub Chapter Information");

        db = new DatabaseHelper(this);
        prf = getSharedPreferences("sectionCreationDetails",MODE_PRIVATE);
        sectID = (TextView) findViewById(R.id.detailsectIDD);
        sectName = (TextView) findViewById(R.id.detailsectNameD);
        subchaptLinked = (TextView) findViewById(R.id.detailsectlinkedD);
        chaptLinked = (TextView) findViewById(R.id.detailchaptslinkedD);
        translinked = findViewById(R.id.detailtransSECtlinkedD);
        confirmDelete = (Button) findViewById(R.id.confirmDeletesectDs);

        final String sectNames = prf.getString("sectName", null);


        Cursor cursor = db.sectDetails(sectNames);
        if (cursor.getCount()  == 0){
            System.out.println("NULL");
        }

        cursor.moveToFirst();
        sectID.setText(cursor.getString(5));
        sectName.setText(cursor.getString(cursor.getColumnIndex("sectionName")));
        subchaptLinked.setText(cursor.getString(cursor.getColumnIndex("subchapterName")));
        chaptLinked.setText(cursor.getString(cursor.getColumnIndex("chapterName")));

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

        confirmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deletesect(sectNames);
//                db.resetrows();
                Toast.makeText(SectionDelete.this, "Successfully Deleted! Returning Back..." , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SectionDelete.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
