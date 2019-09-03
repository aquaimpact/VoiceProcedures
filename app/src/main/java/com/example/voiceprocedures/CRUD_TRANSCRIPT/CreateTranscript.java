package com.example.voiceprocedures.CRUD_TRANSCRIPT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.PathUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voiceprocedures.CRUD_SECTIONS.CreateSection;
import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.MainActivity;
import com.example.voiceprocedures.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.security.spec.ECField;

import static android.provider.CalendarContract.CalendarCache.URI;
import com.example.voiceprocedures.pathURI;
import com.opencsv.CSVReader;

import org.w3c.dom.Text;

public class CreateTranscript extends AppCompatActivity {

    private TextView importdata, transtext, importimg;
    private ImageView imgview;
    private Button createtrans;
    private EditText transName;
    DatabaseHelper db;

    private String location;
//    private EditText transtext;
    public static final int requestcode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transcript);

        db = new DatabaseHelper(this);

        importdata = (TextView) findViewById(R.id.importdata);
        transtext = (TextView) findViewById(R.id.transtext2);

        importimg = (TextView) findViewById(R.id.importimg);
        imgview = (ImageView) findViewById(R.id.transpic);

        createtrans = (Button) findViewById(R.id.createtrans);
        transName = (EditText) findViewById(R.id.transname);
        transName.setText(null);

        location = null;



        importdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
                fileintent.addCategory(Intent.CATEGORY_OPENABLE);
                fileintent.setType("text/*");
                try {
                    startActivityForResult(fileintent, requestcode);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(CreateTranscript.this, "No app found for importing the file.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        importimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 5);
            }
        });

        createtrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String locations = location;
                String transtxt = transtext.getText().toString().trim();
                String transNames = transName.getText().toString().trim();

                File files = new File(locations);
                String filename= locations.substring(locations.lastIndexOf("/")+1);
                File files2 = new File("/data/data/com.example.voiceprocedures/images/" + filename);
                try {
                    copyFile(files, files2);
                }catch (Exception e){
                    e.printStackTrace();
                }

                locations = files2.getAbsolutePath();
                System.out.println(locations);
//                System.out.println(transtxt);
//                System.out.println(transNames);

                System.out.println(transtxt);
                String confirm = "Nothing Selected";

                //Confirmation for the equals does not work.
                if (!confirm.trim().equals(transtxt) || transNames.length() > 0){

                    long val = db.addTrans(transNames, transtxt, locations);

//                    System.out.println("LOL");
                    if(val > 0){
                        Toast.makeText(CreateTranscript.this, "Successfully Created Transcript!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(CreateTranscript.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }else{
                        Toast.makeText(CreateTranscript.this, "Transcript Creation Error!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(CreateTranscript.this, "One of the important FIELDS has not been filled in!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;

        switch (requestCode) {
            case requestcode:
                Uri filepath = data.getData();
                try {
                    String fullpath = pathURI.getPath(this, filepath);
                    try {
                        FileReader filess = new FileReader(fullpath);
                        BufferedReader buffer = new BufferedReader(filess);
                        ContentValues contentValues = new ContentValues();

                        String[] str = buffer.readLine().split(",", 2);
                        String ID = str[0];
                        String Texts = str[1].replace("\"", "");

                        System.out.println(Texts);
                        transtext.setText(Texts);
                        Toast.makeText(CreateTranscript.this, "Succesfully Imported!", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }catch (Exception ee) {

                    File file = new File(filepath.getPath());
                    final String[] split = file.getPath().split(":");//split the path.
                    String files = split[1];
                    try {
                        FileReader filess = new FileReader(files);
                        BufferedReader buffer = new BufferedReader(filess);
                        ContentValues contentValues = new ContentValues();

                        String[] str = buffer.readLine().split(",", 2);
                        String ID = str[0];
                        String Texts = str[1].replace("\"", "");

                        System.out.println(Texts);
                        transtext.setText(Texts);
                        Toast.makeText(CreateTranscript.this, "Succesfully Imported!", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            case 5:
                Uri uri = data.getData();
                imgview.setImageURI(uri);
                try {
                    String fullpath = pathURI.getPath(this, uri);
                    location = fullpath;
                }catch (Exception e){
                    File file = new File(uri.getPath());
                    final String[] split = file.getPath().split(":");//split the path.
                    String files = split[1];
                    location = files;
                }
        }


    }

    public static void copyFile(File src, File dst) throws IOException
    {
        FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try
        {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        }
        finally
        {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }

//                Toast.makeText(CreateTranscript.this, filepath, Toast.LENGTH_SHORT).show();
}

