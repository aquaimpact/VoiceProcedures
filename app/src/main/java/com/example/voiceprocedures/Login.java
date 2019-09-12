package com.example.voiceprocedures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voiceprocedures.Main_Components.DescriptionTranscript;
import com.example.voiceprocedures.Main_Components.FinishScreen;
import com.example.voiceprocedures.Main_Components.PracticePage;

public class Login extends AppCompatActivity {

    private EditText username,password;
    private ImageButton login;

    SharedPreferences preferences;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        login = (ImageButton) findViewById(R.id.login);

        preferences = getSharedPreferences("user_details",MODE_PRIVATE);

        db = new DatabaseHelper(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernames = username.getText().toString().trim();
                String passords = password.getText().toString().trim();
                Boolean res = db.checkUser(usernames, passords);


                if (usernames.equals("adminDH") && passords.equals("1234")){

                    //CREATES SESSION!!
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("StudentName", usernames);
                    editor.commit();

                    Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    Intent homeintent = new Intent(Login.this, MainActivity.class);
                    startActivity(homeintent);
                    username.setText(null);
                    password.setText(null);
                }else {
                    if (res == true)
                    {
                        Cursor cursor = db.oneUserData(usernames, passords);
//                        System.out.println(cursor.getCount());
                        if (cursor.getCount()> 0)
                        {
                            System.out.println("LOL");
                            SharedPreferences.Editor editor = preferences.edit();

                            cursor.moveToFirst();

                            editor.putString("ID", cursor.getString(cursor.getColumnIndex("ID")));

                            editor.putString("StudentName", cursor.getString(1));

                            editor.putString("appointment", cursor.getString(2));

                            editor.putString("department", cursor.getString(3));

                            editor.putString("password", cursor.getString(4));

                            editor.commit();

                            //Main Function
                            Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            Intent homeintent = new Intent(Login.this, MainActivity.class);
                            startActivity(homeintent);
                            finish();

                        }

                    }
                    else{
                        Toast.makeText(Login.this, "Incorrect Student Name or Password", Toast.LENGTH_SHORT).show();
                    }
                }
//


            }
        });
    }
}
