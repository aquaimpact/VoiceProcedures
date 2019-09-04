package com.example.voiceprocedures.CRUD_VOICERECORDING;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
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

import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.R;

import java.util.ArrayList;
import java.util.List;

public class CreateVoiceClips extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView importvoice, results1, results2;
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

        results1.setText(null);
        results2.setText(null);

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

}
