package com.example.voiceprocedures.CRUD_STUDENT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.MainActivity;
import com.example.voiceprocedures.R;

public class CreateUser extends AppCompatActivity {

    DatabaseHelper db;

    EditText studentName, appointment, department, password, conpass;
    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        this.setTitle("Create User");

        studentName = (EditText) findViewById(R.id.stuname);
        studentName.setText(null);

        appointment = (EditText) findViewById(R.id.appoint);
        appointment.setText(null);

        department = (EditText) findViewById(R.id.dept);
        department.setText(null);

        password = (EditText) findViewById(R.id.pass);
        password.setText(null);

        conpass = (EditText) findViewById(R.id.conpass);
        conpass.setText(null);

        create = (Button) findViewById(R.id.create);
        db = new DatabaseHelper(this);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stuname = studentName.getText().toString().trim();
                String appoint = appointment.getText().toString().trim();
                String dept = department.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String conpasss = conpass.getText().toString().trim();

                if (stuname.length() > 0 ||appoint.length() > 0 ||dept.length() > 0 ||pass.length()  >= 1 ||conpasss.length()  >= 1 ){
                    if(pass.equals(conpasss)){
                        long val = db.addUser(stuname,appoint, dept, pass);
                        if(val > 0){
                            Toast.makeText(CreateUser.this, "Successfully Created User!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(CreateUser.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                            //Send user to login screen.
                        }else{
                            Toast.makeText(CreateUser.this, "Register Error!", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(CreateUser.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(CreateUser.this, "One of the important Fields has not been filled in!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
