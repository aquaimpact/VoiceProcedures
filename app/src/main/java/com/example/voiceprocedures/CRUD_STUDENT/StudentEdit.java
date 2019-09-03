package com.example.voiceprocedures.CRUD_STUDENT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.MainActivity;
import com.example.voiceprocedures.R;

public class StudentEdit extends AppCompatActivity {

    DatabaseHelper db;
    SharedPreferences prf;
    TextView stuID, stuName, stuAppoint, stuDept, stuPass, stuConpass;
    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_edit);

        this.setTitle("Edit Student Information");

        db = new DatabaseHelper(this);
        prf = getSharedPreferences("userCreationDetails", MODE_PRIVATE);
        stuName = (TextView) findViewById(R.id.stunameq);
        stuAppoint = (TextView) findViewById(R.id.appointq);
        stuDept = (TextView) findViewById(R.id.deptq);
        stuPass = (TextView) findViewById(R.id.passq);
        stuConpass = (TextView) findViewById(R.id.conpassq);
        edit = (Button) findViewById(R.id.editSTU);

        final String UserName = prf.getString("StudentName", null);

        Cursor cursor = db.studentDetails(UserName);
        if (cursor.getCount() == 0) {
            System.out.println("NULL");
        }
        cursor.moveToFirst();
        stuName.setText(cursor.getString(cursor.getColumnIndex("studentName")));
        stuAppoint.setText(cursor.getString(cursor.getColumnIndex("appointment")));
        stuDept.setText(cursor.getString(cursor.getColumnIndex("department")));
        stuPass.setText(cursor.getString(cursor.getColumnIndex("password")));

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stuname = stuName.getText().toString().trim();
                String appoint = stuAppoint.getText().toString().trim();
                String dept = stuDept.getText().toString().trim();
                String pass = stuPass.getText().toString().trim();
                String conpasss = stuConpass.getText().toString().trim();

                if (stuname.length() > 0 || appoint.length() > 0 || dept.length() > 0 || pass.length() != 0 || conpasss.length() != 0 || pass != null || pass != "" || conpasss != null || conpasss != "") {
                    if (pass.equals(conpasss)) {
                        db.editUser(UserName, stuname, appoint, dept, pass);

                        Toast.makeText(StudentEdit.this, "Successfully Edited User!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(StudentEdit.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                        //Send user to login screen.
                    } else {
                        Toast.makeText(StudentEdit.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(StudentEdit.this, "One of the important Fields has not been filled in!", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
