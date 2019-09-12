package com.example.voiceprocedures.CRUD_TRANSCRIPT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voiceprocedures.CRUD_CHAPTER.ChapterEdit;
import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.MainActivity;
import com.example.voiceprocedures.R;
import com.example.voiceprocedures.pathURI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

import static com.example.voiceprocedures.CRUD_TRANSCRIPT.CreateTranscript.copyFile;

public class TranscriptEdit extends AppCompatActivity {

    private TextView importdata, importimg;
    private ImageView imgview;
    private Button createtrans;
    private EditText transName, transtext;
    DatabaseHelper db;

    SharedPreferences prf;

    private String location;
    //    private EditText transtext;
    public static final int requestcode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transcript_edit);

        db = new DatabaseHelper(this);

        importdata = (TextView) findViewById(R.id.importdataE);
        transtext = (EditText) findViewById(R.id.transtext2E);

        importimg = (TextView) findViewById(R.id.importimgE);
        imgview = (ImageView) findViewById(R.id.transpicE);

        createtrans = (Button) findViewById(R.id.createtransE);
        transName = (EditText) findViewById(R.id.transnameE);

        prf = getSharedPreferences("transCreationDetails", MODE_PRIVATE);


        final String chaptName = prf.getString("transName", null);

        Cursor cursor = db.transDetails(chaptName);
        if (cursor.getCount() == 0) {
            System.out.println("NULL");
        }
        cursor.moveToFirst();
        transName.setText(cursor.getString(cursor.getColumnIndex("transcriptName")));
        transtext.setText(cursor.getString(cursor.getColumnIndex("transcript")));
        location = cursor.getString(cursor.getColumnIndex("image"));

        try {
            File imgfile = new File(cursor.getString(cursor.getColumnIndex("image")));
            if (imgfile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgfile.getAbsolutePath());

                imgview.setImageBitmap(myBitmap);
            }
        }
        catch (NullPointerException e){
            System.out.println(e);
//            imgview.setBackgroundResource(R.drawable.noimg);
        }

        importdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
                fileintent.addCategory(Intent.CATEGORY_OPENABLE);
                fileintent.setType("text/*");
                try {
                    startActivityForResult(fileintent, requestcode);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(TranscriptEdit.this, "No app found for importing the file.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        importimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1000);
            }
        });

        createtrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String locations = location;
                String transtxt = transtext.getText().toString().trim();
                String transNames = transName.getText().toString().trim();

                if (!locations.startsWith("/data/data/com.example.voiceprocedures/imgs/")) {
                    File files = new File(locations);
                    String filename = locations.substring(locations.lastIndexOf("/") + 1);
                    File files2 = new File("/data/data/com.example.voiceprocedures/imgs/" + filename);
                    try {
                        copyFile(files, files2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    locations = files2.getAbsolutePath();
                    System.out.println(locations);
                }
                System.out.println(transtxt);
                String confirm = "Nothing Selected";

                //Confirmation for the equals does not work.
                if (transNames.length() > 0 || transtxt.length() > 0) {

                    db.editTrans(chaptName, transNames, transtxt, locations);

                    Toast.makeText(TranscriptEdit.this, "Successfully Edited Transcript!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(TranscriptEdit.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(TranscriptEdit.this, "One of the important Fields has not been filled in!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(TranscriptEdit.this, "Succesfully Imported!", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(TranscriptEdit.this, "Succesfully Imported!", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            case 1000:
                Uri uri = data.getData();
                imgview.setImageURI(uri);
                try {
                    String fullpath2 = pathURI.getPath(this, uri);
                    location = fullpath2;
                }catch (Exception e){
                    File file2 = new File(uri.getPath());
                    final String[] split = file2.getPath().split(":");//split the path.
                    String files2 = split[1];
                    location = files2;
                }

//                Toast.makeText(TranscriptEdit.this, location, Toast.LENGTH_SHORT).show();
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
}
