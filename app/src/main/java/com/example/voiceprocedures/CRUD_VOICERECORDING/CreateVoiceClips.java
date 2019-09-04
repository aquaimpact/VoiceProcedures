package com.example.voiceprocedures.CRUD_VOICERECORDING;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voiceprocedures.CRUD_TRANSCRIPT.CreateTranscript;
import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.MainActivity;
import com.example.voiceprocedures.R;
import com.example.voiceprocedures.pathURI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static com.example.voiceprocedures.HelperFunctions.copyFile;

public class CreateVoiceClips extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView importvoice, results1, results2, resultsVoice;
    private Button createvoice;
    DatabaseHelper db;
    private Spinner stulinkedto, translinkedto;

    private String location;
    //    private EditText transtext;
    public static final int requestcode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_voice_clips);

        db = new DatabaseHelper(this);

        importvoice = findViewById(R.id.importvoice);
        results1 = findViewById(R.id.wowvoice1);
        results2 = findViewById(R.id.wowvoice2);
        stulinkedto = findViewById(R.id.stulinkedV);
        translinkedto = findViewById(R.id.translinkedV);
        createvoice = findViewById(R.id.createvoice);
        resultsVoice = findViewById(R.id.resultsVoice);

        results1.setText(null);
        results2.setText(null);

        location = null;

        List<String> items = db.allstudatas();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stulinkedto.setOnItemSelectedListener(this);
        stulinkedto.setAdapter(dataAdapter);

        List<String> items2 = db.alltransdatas();

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        translinkedto.setOnItemSelectedListener(this);
        translinkedto.setAdapter(dataAdapter2);

        importvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent audioPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                audioPickerIntent.setType("audio/*");
                startActivityForResult(audioPickerIntent, requestcode);
            }
        });

        createvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String locations = location;
                String sturesult = results1.getText().toString().trim();
                String transresult = results2.getText().toString().trim();

                Cursor cursor = db.counterVoice(transresult);
                cursor.moveToFirst();
                String count = cursor.getString(cursor.getColumnIndex("COUNT(transcriptID)"));
                String counter;

                File files = new File(locations);
                if (count.equals("0")) {
                    counter = "";
                }else{
                    counter = "_" + count;
                }

                String filename = locations.substring(locations.lastIndexOf("/") + 1) + counter;

                File files2 = new File("/data/data/com.example.voiceprocedures/VoiceRecordings/" + filename);

                try {
                    copyFile(files, files2);
                }catch (Exception e){
                    e.printStackTrace();
                }

                locations = files2.getAbsolutePath();
                System.out.println(locations);


                //Confirmation for the equals does not work.
                if (results1.length() > 0 || results2.length() > 0){
                    long val = db.addVoice(filename, sturesult, transresult, locations);

                    if(val > 0){
                        Toast.makeText(CreateVoiceClips.this, "Successfully Created Voice CLip!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(CreateVoiceClips.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }else{
                        Toast.makeText(CreateVoiceClips.this, "Voice Clip Creation Error!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(CreateVoiceClips.this, "One of the important FIELDS has not been filled in!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        Spinner spin = (Spinner)parent;
        Spinner spin2 = (Spinner)parent;
        // On selecting a spinner item
        if(spin.getId() == R.id.stulinkedV){
            String stulabel = parent.getItemAtPosition(position).toString();
            Cursor stu = db.studentDetails(stulabel);
            stu.moveToFirst();
            results1.setText(stu.getString(stu.getColumnIndex("ID")));
//            System.out.println("The Text result1 is:" + results1.getText().toString().trim());
        }
        else if (spin.getId() == R.id.translinkedV){
            String translabel = parent.getItemAtPosition(position).toString();
            System.out.println(translabel);
            Cursor trans = db.transDetails2(translabel);
            trans.moveToFirst();
            results2.setText(trans.getString(trans.getColumnIndex("transcriptID")));
//            System.out.println("The Text result2 is:" + results2.getText().toString().trim());
        }

        // Showing selected spinner item
//        Toast.makeText(parent.getContext(), "You selected: " + label,
//                Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    protected void onActivityResult(int requestCodes, int resultCode, Intent data) {
        if (data == null)
            return;

        if (requestCodes == requestcode){
            Uri uri = data.getData();
            try {
                String fullpath = pathURI.getPath(this, uri);
                location = fullpath;
                resultsVoice.setText(location.substring(location.lastIndexOf("/") + 1));
            }catch (Exception e){
                File file = new File(uri.getPath());
                final String[] split = file.getPath().split(":");//split the path.
                String files = split[1];
                location = files;
                resultsVoice.setText(location.substring(location.lastIndexOf("/") + 1));
            }
        }


    }
}
