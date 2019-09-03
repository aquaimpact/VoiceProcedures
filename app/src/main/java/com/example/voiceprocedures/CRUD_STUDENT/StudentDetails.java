package com.example.voiceprocedures.CRUD_STUDENT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.R;

public class StudentDetails extends AppCompatActivity {

    DatabaseHelper db;
    SharedPreferences prf;
    TextView stuID, stuName, stuAppoint, stuDept, stuPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        this.setTitle("Detailed Student Information");

        db = new DatabaseHelper(this);
        prf = getSharedPreferences("userCreationDetails",MODE_PRIVATE);
        stuID = (TextView) findViewById(R.id.detailStuID);
        stuName = (TextView) findViewById(R.id.detailStuName);
        stuAppoint = (TextView) findViewById(R.id.detailStuAppt);
        stuDept = (TextView) findViewById(R.id.detailStuDept);
        stuPass = (TextView) findViewById(R.id.detailStuPass);

        String UserName = prf.getString("StudentName", null);
        String UserID = prf.getString("StudentID", null);

        System.out.println(UserName);
        System.out.println(UserID);

        Cursor cursor = db.studentDetails(UserName);
        if (cursor.getCount()  == 0){
            System.out.println("NULL");
        }
        cursor.moveToFirst();
        stuID.setText(cursor.getString(cursor.getColumnIndex("ID")));
        stuName.setText(cursor.getString(cursor.getColumnIndex("studentName")));
        stuAppoint.setText(cursor.getString(cursor.getColumnIndex("appointment")));
        stuDept.setText(cursor.getString(cursor.getColumnIndex("department")));
        stuPass.setText(cursor.getString(cursor.getColumnIndex("password")));

    }
}
