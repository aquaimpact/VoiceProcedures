package com.example.voiceprocedures.CRUD_VOICERECORDING;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.R;

public class VoiceClipsDetail extends AppCompatActivity {

    DatabaseHelper db;
    SharedPreferences prf;
    TextView voiceID, stuLinked, transLinked, datetime, filepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_clips_detail);

        db = new DatabaseHelper(this);
        prf = getSharedPreferences("voiceCreationDetails",MODE_PRIVATE);
        voiceID = (TextView) findViewById(R.id.detailvoiceID);
        stuLinked = (TextView) findViewById(R.id.detailpt1Linked);
        transLinked = (TextView)findViewById(R.id.detailpt2Linked);
        datetime = (TextView) findViewById(R.id.detaildatevoice);
        filepath = (TextView) findViewById(R.id.detailclipPath);

        String voiceNames = prf.getString("voiceName", null);

        Cursor cursor = db.voiceDetails(voiceNames);
        if (cursor.getCount()  == 0){
            System.out.println("NULL");
        }
        cursor.moveToFirst();
        voiceID.setText(cursor.getString(cursor.getColumnIndex("recordingID")));
        stuLinked.setText(cursor.getString(cursor.getColumnIndex("studentName")));
        transLinked.setText(cursor.getString(cursor.getColumnIndex("transcriptName")));
        datetime.setText(cursor.getString(cursor.getColumnIndex("datetime")));
        filepath.setText(cursor.getString(cursor.getColumnIndex("recordingPath")));
    }
}
