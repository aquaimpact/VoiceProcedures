package com.example.voiceprocedures.CRUD_VOICERECORDING;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voiceprocedures.CRUD_TRANSCRIPT.TranscriptEdit;
import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.MainActivity;
import com.example.voiceprocedures.R;
import com.example.voiceprocedures.pathURI;

import java.io.File;
import java.util.List;

import static com.example.voiceprocedures.HelperFunctions.copyFile;

public class VoiceClipsEdit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseHelper db;
    SharedPreferences prf;
    private TextView importvoice, results1, results2, resultsVoice;
    private Spinner stulinkedto, translinkedto;
    Button createvoice;

    private String location;
    public static final int requestcode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_clips_edit);

        db = new DatabaseHelper(this);

        this.setTitle("Edit Voice Clip Information");

        prf = getSharedPreferences("voiceCreationDetails",MODE_PRIVATE);
        importvoice = findViewById(R.id.importvoiceE);
        results1 = findViewById(R.id.wowvoice1E);
        results2 = findViewById(R.id.wowvoice2E);
        stulinkedto = findViewById(R.id.stulinkedVE);
        translinkedto = findViewById(R.id.translinkedVE);
        createvoice = findViewById(R.id.createvoiceE);
        resultsVoice = findViewById(R.id.resultsVoiceE);

        List<String> items = db.allstudatas();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stulinkedto.setOnItemSelectedListener(this);

        List<String> item2 = db.alltransdatas();
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, item2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        translinkedto.setOnItemSelectedListener(this);

        final String voiceName = prf.getString("voiceName", null);

        final Cursor cursors = db.voiceDetails(voiceName);
        cursors.moveToFirst();

        resultsVoice.setText(cursors.getString(cursors.getColumnIndex("recordingPath")));

        location = cursors.getString(cursors.getColumnIndex("recordingPath"));

        int i= items.indexOf(cursors.getString(cursors.getColumnIndex("studentName")));
        int ii = item2.indexOf(cursors.getString(cursors.getColumnIndex("transcriptName")));

        stulinkedto.setAdapter(dataAdapter);
        stulinkedto.setSelection(i);

        translinkedto.setAdapter(dataAdapter2);
        translinkedto.setSelection(ii);

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

                System.out.println("##############");
                String locations = location;

                String sturesult = results1.getText().toString().trim();

                System.out.println("##############");
                String transresult = results2.getText().toString().trim();

                Cursor cursor = db.counterVoice(transresult);
                cursor.moveToFirst();
                String count = cursor.getString(cursor.getColumnIndex("COUNT(transcriptID)"));
                String counter;

                String filename = cursors.getString(cursors.getColumnIndex("recordingName"));
                if (!locations.startsWith("/data/data/com.example.voiceprocedures/VoiceRecordings/")) {

                    if (count.equals("0")) {
                        counter = "";
                    }else{
                        counter = "_" + count;
                    }

                    File files = new File(locations);
                    filename = locations.substring(locations.lastIndexOf("/") + 1) + counter;
                    File files2 = new File("/data/data/com.example.voiceprocedures/VoiceRecordings/" + filename);
                    try {
                        copyFile(files, files2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    locations = files2.getAbsolutePath();
                }

                //Confirmation for the equals does not work.
                if (results1.length() > 0 || results2.length() > 0){

                    String oldfilename = cursors.getString(cursors.getColumnIndex("recordingName"));

                    db.editVoice(oldfilename, filename, sturesult, transresult, locations);

                    Toast.makeText(VoiceClipsEdit.this, "Successfully Edited Voice Clip!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(VoiceClipsEdit.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
                else{
                    Toast.makeText(VoiceClipsEdit.this, "One of the important FIELDS has not been filled in!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String label = parent.getItemAtPosition(position).toString();

        Spinner spin = (Spinner)parent;
        Spinner spin2 = (Spinner)parent;
        // On selecting a spinner item
        if(spin.getId() == R.id.stulinkedVE){

            String stulabel = parent.getItemAtPosition(position).toString();
            Cursor stu = db.studentDetails(stulabel);
            stu.moveToFirst();
            System.out.println(stu.getString(stu.getColumnIndex("ID")));
            results1.setText(stu.getString(stu.getColumnIndex("ID")));
//            System.out.println("The Text result1 is:" + results1.getText().toString().trim());
        }
        else if (spin2.getId() == R.id.translinkedVE){
            String translabel = parent.getItemAtPosition(position).toString();
            Cursor trans = db.transDetails2(translabel);
            trans.moveToFirst();
            results2.setText(trans.getString(trans.getColumnIndex("transcriptID")));
//            System.out.println("The Text result2 is:" + results2.getText().toString().trim());
        }
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
