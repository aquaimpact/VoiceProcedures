package com.example.voiceprocedures.CRUD_CHAPTER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.MainActivity;
import com.example.voiceprocedures.R;

import java.util.ArrayList;
import java.util.List;

public class CreateChapter extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseHelper db;

    EditText chaptName;
    Button createChapt;
    Spinner commType;
    private Integer value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chapter);

        this.setTitle("Create Chapter");

        chaptName = (EditText) findViewById(R.id.chaptername);
        createChapt = (Button) findViewById(R.id.createChapter);
        commType = findViewById(R.id.communicationtypes);

        chaptName.setText(null);

        db = new DatabaseHelper(this);

        List<String> items = new ArrayList<String>();
        items.add("External Communications");
        items.add("On-board Communications");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);


        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        commType.setOnItemSelectedListener(this);

        commType.setAdapter(dataAdapter);

        createChapt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chaptname = chaptName.getText().toString().trim();

                if (chaptname.length() > 0){
                    long val = db.addChapter(chaptname, value);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        String label = parent.getItemAtPosition(position).toString();
        if (label.equals("External Communications")){
            value = 0;
        }else if (label.equals("On-board Communications")) {
            value = 1;
//            Toast.makeText(this, label, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
