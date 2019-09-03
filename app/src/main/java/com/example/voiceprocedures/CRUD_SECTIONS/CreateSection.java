package com.example.voiceprocedures.CRUD_SECTIONS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voiceprocedures.CRUD_SUBCHAPTER.SubChapterCreate;
import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.MainActivity;
import com.example.voiceprocedures.R;

import java.util.List;

public class CreateSection extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseHelper db;

    EditText sectName;
    TextView results;
    Spinner subchaptlinkedto;
    Button createsect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_create);

        this.setTitle("Create Section");

        sectName = (EditText) findViewById(R.id.sectname);
        createsect = (Button) findViewById(R.id.createsect);

        sectName.setText(null);

        subchaptlinkedto = (Spinner) findViewById(R.id.subchapterlinked);

        results = (TextView) findViewById(R.id.wowSect);
        results.setText(null);

        db = new DatabaseHelper(this);

        List<String> items = db.allsubchapterdatas();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);


        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        subchaptlinkedto.setOnItemSelectedListener(this);

        subchaptlinkedto.setAdapter(dataAdapter);

        createsect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subchaptname = sectName.getText().toString().trim();
                String result = results.getText().toString().trim();

                if (subchaptname.length() > 0 || result.length() > 0){
                    long val = db.addsect(result,subchaptname);
                    if(val > 0){
                        Toast.makeText(CreateSection.this, "Successfully Created Section!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(CreateSection.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }else{
                        Toast.makeText(CreateSection.this, "Section Creation Error!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(CreateSection.this, "One of the important FIELDS has not been filled in!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner item
        String label = parent.getItemAtPosition(position).toString();

        Cursor cursor = db.subchaptDetails(label);

        cursor.moveToFirst();

        results.setText(cursor.getString(2));

        // Showing selected spinner item
//        Toast.makeText(parent.getContext(), "You selected: " + label,
//                Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
