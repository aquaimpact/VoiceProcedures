package com.example.voiceprocedures.CRUD_TRANSCRIPT;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.R;

import java.io.File;

public class TranscriptDetails extends AppCompatActivity {

    DatabaseHelper db;
    SharedPreferences prf;
    TextView transID, transName, transTxt, sectLinked, subchaptLinked, chaptLinked;
    ImageView transImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transcript_details);

        this.setTitle("Detailed Transcript Information");

        db = new DatabaseHelper(this);
        prf = getSharedPreferences("transCreationDetails",MODE_PRIVATE);
        transID = (TextView) findViewById(R.id.detailtransID);
        transName = (TextView) findViewById(R.id.detailtransName);
        transTxt = (TextView)findViewById(R.id.detailtransTxt);
        transImg = (ImageView) findViewById(R.id.detailtransimg);
        sectLinked = (TextView) findViewById(R.id.detailtransLinked);
        subchaptLinked = (TextView) findViewById(R.id.detailtransLinked2);
        chaptLinked = (TextView) findViewById(R.id.detailtransLinked3);

        String transNames = prf.getString("transName", null);

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

    }
}
