package com.example.voiceprocedures.CRUD_TRANSCRIPT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voiceprocedures.CRUD_CHAPTER.ChapterDelete;
import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.MainActivity;
import com.example.voiceprocedures.R;

import java.awt.font.TextAttribute;
import java.io.File;

public class TranscriptDelete extends AppCompatActivity {

    DatabaseHelper db;
    SharedPreferences prf;
    TextView transID, transName, transTxt, sectLinked, subchaptLinked, chaptLinked;
    ImageView transImg;
    Button confirmDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transcript_delete);

        this.setTitle("Delete Transcript");

        db = new DatabaseHelper(this);
        prf = getSharedPreferences("transCreationDetails",MODE_PRIVATE);
        transID = (TextView) findViewById(R.id.detailtransIDD);
        transName = (TextView) findViewById(R.id.detailtransNameD);
        transTxt = (TextView)findViewById(R.id.detailtransTxtD);
        transImg = (ImageView) findViewById(R.id.detailtransimgD);
        sectLinked = (TextView) findViewById(R.id.detailtransLinkedD);
        subchaptLinked = (TextView) findViewById(R.id.detailtransLinked2D);
        chaptLinked = (TextView) findViewById(R.id.detailtransLinked3D);
        confirmDelete = (Button) findViewById(R.id.confirmDeletetrans);

        final String transNames = prf.getString("transName", null);

        Cursor cursor = db.transDetails(transNames);
        if (cursor.getCount()  == 0){
            System.out.println("NULL");
        }
        cursor.moveToFirst();
        transID.setText(cursor.getString(cursor.getColumnIndex("transcriptID")));
        transName.setText(cursor.getString(cursor.getColumnIndex("transcriptName")));
        transTxt.setText(cursor.getString(cursor.getColumnIndex("transcript")));
        sectLinked.setText(cursor.getString(cursor.getColumnIndex("sectionName")));
        subchaptLinked.setText(cursor.getString(cursor.getColumnIndex("subchapterName")));
        chaptLinked.setText(cursor.getString(cursor.getColumnIndex("chapterName")));

        try {
            File imgfile = new File(cursor.getString(cursor.getColumnIndex("image")));
            if (imgfile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgfile.getAbsolutePath());

                transImg.setImageBitmap(myBitmap);
            }
        }catch (NullPointerException e){
            transImg.setBackgroundResource(R.drawable.noimg);
        }

//        final String transID = cursor.getString(cursor.getColumnIndex("transcriptID"));

        confirmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deletetrans(transNames);
//                db.resetrows();
                Toast.makeText(TranscriptDelete.this, "Successfully Deleted! Returning Back..." , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TranscriptDelete.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
