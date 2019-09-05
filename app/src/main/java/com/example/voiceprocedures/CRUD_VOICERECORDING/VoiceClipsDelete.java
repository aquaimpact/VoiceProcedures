package com.example.voiceprocedures.CRUD_VOICERECORDING;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voiceprocedures.CRUD_TRANSCRIPT.TranscriptDelete;
import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.MainActivity;
import com.example.voiceprocedures.R;

public class VoiceClipsDelete extends AppCompatActivity {

    DatabaseHelper db;
    SharedPreferences prf;
    TextView voiceID, stuLinked, transLinked, datetime, filepath;
    Button btndeletevoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_clips_delete);

        db = new DatabaseHelper(this);
        prf = getSharedPreferences("voiceCreationDetails",MODE_PRIVATE);
        voiceID = (TextView) findViewById(R.id.detailvoiceIDD);
        stuLinked = (TextView) findViewById(R.id.detailpt1LinkedD);
        transLinked = (TextView)findViewById(R.id.detailpt2LinkedD);
        datetime = (TextView) findViewById(R.id.detaildatevoiceD);
        filepath = (TextView) findViewById(R.id.detailclipPathD);
        btndeletevoice = (Button) findViewById(R.id.confirmDeletevoice);

        final String voiceNames = prf.getString("voiceName", null);

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

        btndeletevoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deletevoice(voiceNames);
                Toast.makeText(VoiceClipsDelete.this, "Successfully Deleted! Returning Back..." , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(VoiceClipsDelete.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
