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

public class StudentDelete extends AppCompatActivity {

    DatabaseHelper db;
    SharedPreferences prf;
    TextView stuID, stuName, stuAppoint, stuDept, stuPass;
    Button confirmDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_delete);

        this.setTitle("Delete Student Information");

        db = new DatabaseHelper(this);
        prf = getSharedPreferences("userCreationDetails",MODE_PRIVATE);
        stuID = (TextView) findViewById(R.id.detailStuIDs);
        stuName = (TextView) findViewById(R.id.detailStuNames);
        stuAppoint = (TextView) findViewById(R.id.detailStuAppts);
        stuDept = (TextView) findViewById(R.id.detailStuDepts);
        stuPass = (TextView) findViewById(R.id.detailStuPasss);
        confirmDelete = (Button) findViewById(R.id.confirmDelete);

        final String UserName = prf.getString("StudentName", null);
//        final String UserID = prf.getString("StudentID", null);

        Cursor cursor = db.studentDetails(UserName);
        if (cursor.getCount()  == 0){
            System.out.println("NULL");
        }

//        System.out.println(UserName);
//        System.out.println(UserID);

        cursor.moveToFirst();
        stuID.setText(cursor.getString(cursor.getColumnIndex("ID")));
        stuName.setText(cursor.getString(cursor.getColumnIndex("studentName")));
        stuAppoint.setText(cursor.getString(cursor.getColumnIndex("appointment")));
        stuDept.setText(cursor.getString(cursor.getColumnIndex("department")));
        stuPass.setText(cursor.getString(cursor.getColumnIndex("password")));




        confirmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteUsers(UserName);
//                db.resetrows();
                Toast.makeText(StudentDelete.this, "Successfully Deleted! Returning Back..." , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(StudentDelete.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
