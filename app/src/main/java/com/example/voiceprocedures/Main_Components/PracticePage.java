package com.example.voiceprocedures.Main_Components;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

import static com.example.voiceprocedures.HelperFunctions.copyFile;

public class PracticePage extends AppCompatActivity {

    DatabaseHelper db;
    ImageView imgstrans;
    TextView txt1, txtp2, P1s, P2s;
    Button rec1, play1, rec2, play2, back, next;
    LinearLayout recordingbarforp1, recordingbarforp2;
    Integer recs1, recs2;
    File file;
    String transID;
    SharedPreferences prf;
    String name;

    Integer numberoftimes;

    private MediaRecorder mediaRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_page);

        imgstrans = findViewById(R.id.imgstrans);
        txt1 = findViewById(R.id.txt1);
        txtp2 = findViewById(R.id.txtp2);
        rec1 = findViewById(R.id.rec1);
        play1 = findViewById(R.id.play1);
        rec2 = findViewById(R.id.rec2);
        play2 = findViewById(R.id.play2);
        recordingbarforp1 = findViewById(R.id.recordingbarforp1);
        recordingbarforp2 = findViewById(R.id.recordingbarforp2);
        back = findViewById(R.id.back);
        next = findViewById(R.id.next);
        P1s = findViewById(R.id.P1s);
        P2s = findViewById(R.id.P2s);
        mediaRecorder = new MediaRecorder();

        db = new DatabaseHelper(this);

        numberoftimes = 0;

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PackageManager.PERMISSION_GRANTED);

        Intent intent = getIntent();
        transID = intent.getStringExtra("ID");
        name = intent.getStringExtra("NAME");
        final String usertype = intent.getStringExtra("speak");
        this.setTitle(name);

        recs1 = 0;
        recs2 = 0;

        prf = getSharedPreferences("user_details",MODE_PRIVATE);

        if(usertype.equals("a")){
            recordingbarforp2.setVisibility(View.GONE);
            P1s.setTextColor(Color.parseColor("#FF0000"));
            P1s.setTypeface(Typeface.DEFAULT_BOLD);
            txt1.setBackgroundResource(R.drawable.borders4selected);
        }
        else if(usertype.equals("b")){
            recordingbarforp1.setVisibility(View.GONE);
            P2s.setTextColor(Color.parseColor("#FF0000"));
            P2s.setTypeface(Typeface.DEFAULT_BOLD);
            txtp2.setBackgroundResource(R.drawable.borders4selected);
        }

        Cursor cursor = db.transdetailsid(transID);
        if(cursor == null){
            Log.i("PracPage", "Error in fetching data! cursor == null!");
            Toast.makeText(this, "ERROR IN FETCHING TASKS", Toast.LENGTH_SHORT).show();
        }
        cursor.moveToFirst();

        try {
            File imgfile = new File(cursor.getString(cursor.getColumnIndex("image")));
            if (imgfile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgfile.getAbsolutePath());

                imgstrans.setImageBitmap(myBitmap);
            }
        }catch (NullPointerException e){
            imgstrans.setBackgroundResource(R.drawable.lol1);
        }

        final boolean finish = true;
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean result = files();

                if (!result){
                    return;
                }

                if (finish){
                    Intent intent = new Intent(PracticePage.this, FinishScreen.class);
                    intent.putExtra("ID", transID);
                    intent.putExtra("NAME", name);
                    intent.putExtra("speak",usertype );
                    startActivity(intent);
                    finish();
                }
            }
        });

        rec1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recs1 == 0){
                    rec1.setText("Stop Recording");
                    record();
                    recs1 = 1;
                }else{
                    rec1.setText("Record");
                    stop();
                    recs1 = 0;
                }
            }
        });

        rec2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recs2 == 0){
                    rec2.setText("Stop Recording");
                    record();
                    recs2 = 1;
                }else{
                    rec2.setText("Record");
                    stop();
                    recs2 = 0;
                }
            }
        });



        // Main Transcript Function
        final String in = cursor.getString(cursor.getColumnIndex("transcript"));
        Integer counter = 0;
        BufferedReader reader = new BufferedReader(new StringReader(in));
        BufferedReader reader2 = new BufferedReader(new StringReader(in));

        List<SpeakerClass> speaker = new ArrayList<SpeakerClass>();
        List<AnswererClass> answerer = new ArrayList<AnswererClass>();

        try{

            String line = reader.readLine();
            reader2.readLine();
            String nxtline = reader2.readLine();

            while (line != null){

                if(line.substring(0,1).trim().equals("S")){

                    if (!nxtline.substring(0,1).trim().equals("S")){
                        counter += 1;
                    }

                    SpeakerClass speakerClass = new SpeakerClass();
                    speakerClass.setSpeaker(line.substring(1, 3));
                    speakerClass.setText(line.substring(4));
                    speakerClass.setMainspeakerlinkedto(counter);
                    speaker.add(speakerClass);

                }else if (line.substring(0,1).trim().equals("A")){

                    AnswererClass answererClass = new AnswererClass();
                    answererClass.setSpeaker(line.substring(1, 3));
                    answererClass.setText(line.substring(4));
                    answererClass.setLinedtospeak(counter);

                    answerer.add(answererClass);
                    
                }

                line = reader.readLine();
                nxtline = reader2.readLine();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        for(int i = 0; i < speaker.size(); i++){
            SpeakerClass firstspeaker = speaker.get(i);
            String speakerName = firstspeaker.getSpeaker();
            String txt = firstspeaker.getText();
            int linked = firstspeaker.getMainspeakerlinkedto();

//            System.out.println(speakerName + ": " + txt);
//            System.out.println(linked);
        }

        for(int i = 0; i < answerer.size(); i++){
            AnswererClass second_speaker = answerer.get(i);
            String speaker2 = second_speaker.getSpeaker();
            String txt2 = second_speaker.getText();
            int linked2 = second_speaker.getLinedtospeak();
//
            System.out.println(speaker2 + ": " + txt2);
            System.out.println(linked2);

        }


    }

    private void stop() {

        mediaRecorder.stop();
        mediaRecorder.release();

        Toast.makeText(this, "Successfully recorded! Click continue to save!", Toast.LENGTH_SHORT).show();

    }

    private void record(){
        try {
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            file = new File(path, "/Audioclip.mp3");

            System.out.println(file);

            mediaRecorder.setOutputFile(file);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.HE_AAC);

            mediaRecorder.prepare();

            mediaRecorder.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean files(){
        String locations = file.toString();

        Cursor cursor = db.counterVoice(transID);
        cursor.moveToFirst();
        String count = cursor.getString(cursor.getColumnIndex("COUNT(transcriptID)"));
        String counter;

        File files = new File(locations);
        if (count.equals("0")) {
            counter = "";
        }else{
            counter = "_" + count;
        }

        String filename = locations.substring(locations.lastIndexOf("/") + 1);
        filename = filename.substring(0, filename.lastIndexOf(".")) + counter + "_" + name;

        File files2 = new File("/data/data/com.example.voiceprocedures/VoiceRecordings/" + filename + ".mp3");

        try {
            copyFile(files, files2);
        }catch (Exception e){
            e.printStackTrace();
        }

        locations = files2.getAbsolutePath();


        //Confirmation for the equals does not work.
        String sturesult = prf.getString("ID", null);

        System.out.println(sturesult);

        long val = db.addVoice(filename, sturesult, transID, locations);

        if(val > 0){
            Toast.makeText(PracticePage.this, "Successfully Created Voice CLip!", Toast.LENGTH_SHORT).show();
            return true;

        }else{
            Toast.makeText(PracticePage.this, "Voice Clip Creation Error!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
