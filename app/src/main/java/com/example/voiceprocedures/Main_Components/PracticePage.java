package com.example.voiceprocedures.Main_Components;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.R;

import java.io.File;

public class PracticePage extends AppCompatActivity {

    DatabaseHelper db;
    ImageView imgstrans;
    TextView txt1, txtp2, P1s, P2s;
    Button rec1, play1, rec2, play2, back, next;
    LinearLayout recordingbarforp1, recordingbarforp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_page);

        imgstrans = findViewById(R.id.imgstrans);
        txt1 = findViewById(R.id.txt1);
        txtp2 = findViewById(R.id.txtp2);
        rec1 = findViewById(R.id.rec1);
        play1 = findViewById(R.id.play1);
        rec2 = findViewById(R.id.rec2);
        play2 = findViewById(R.id.play2);
        recordingbarforp1 = findViewById(R.id.recordingbarforp1);
        recordingbarforp2 = findViewById(R.id.recordingbarforp2);
        back = findViewById(R.id.back);
        next = findViewById(R.id.next);
        P1s = findViewById(R.id.P1s);
        P2s = findViewById(R.id.P2s);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        final String transID = intent.getStringExtra("ID");
        final String name = intent.getStringExtra("NAME");
        final String usertype = intent.getStringExtra("speak");
        this.setTitle(name);

        if(usertype.equals("a")){
            recordingbarforp2.setVisibility(View.GONE);
            P1s.setTextColor(Color.parseColor("#FF0000"));
            P1s.setTypeface(Typeface.DEFAULT_BOLD);
            txt1.setBackgroundResource(R.drawable.borders4selected);
        }else if(usertype.equals("b")){
            recordingbarforp1.setVisibility(View.GONE);
            P2s.setTextColor(Color.parseColor("#FF0000"));
            P2s.setTypeface(Typeface.DEFAULT_BOLD);
            txtp2.setBackgroundResource(R.drawable.borders4selected);
        }

        Cursor cursor = db.transdetailsid(transID);
        if(cursor == null){
            Log.i("PracPage", "Error in fetching data! cursor == null!");
            Toast.makeText(this, "ERROR IN FETCHING TASKS", Toast.LENGTH_SHORT).show();
        }
        cursor.moveToFirst();

        try {
            File imgfile = new File(cursor.getString(cursor.getColumnIndex("image")));
            if (imgfile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgfile.getAbsolutePath());

                imgstrans.setImageBitmap(myBitmap);
            }
        }catch (NullPointerException e){
            imgstrans.setBackgroundResource(R.drawable.lol1);
        }


        final boolean finish = true;
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (finish){
                    Intent intent = new Intent(PracticePage.this, FinishScreen.class);
                    intent.putExtra("ID", transID);
                    intent.putExtra("NAME", name);
                    intent.putExtra("speak",usertype );
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
