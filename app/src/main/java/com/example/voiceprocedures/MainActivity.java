package com.example.voiceprocedures;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.voiceprocedures.Fragments.ChapterFragment;
import com.example.voiceprocedures.Fragments.ChaptersDBFragment;
import com.example.voiceprocedures.Fragments.SectionDBFragment;
import com.example.voiceprocedures.Fragments.SubChapterDBFragment;
import com.example.voiceprocedures.Fragments.SubChapterFragment;
import com.example.voiceprocedures.Fragments.TranscriptDBFragment;
import com.example.voiceprocedures.Fragments.UserFragment;
import com.example.voiceprocedures.Fragments.VoiceRecordingDBFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseHelper db;
    SharedPreferences prf;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        //Main Sharing Functions
        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        View headerView = navigationView.getHeaderView(0);

        name = (TextView) headerView.findViewById(R.id.Loginusername);
        TextView dept = (TextView) headerView.findViewById(R.id.department);
        TextView appoint = (TextView) headerView.findViewById(R.id.appointment);
        TextView addition = (TextView) headerView.findViewById(R.id.additional);

        //Just removes | annoyance!
        name.setText(prf.getString("StudentName", null));
        if (prf.getString("department", null) == null || prf.getString("appointment", null) == null){
            addition.setVisibility(View.GONE);
        }

        dept.setText(prf.getString("department", null));
        appoint.setText(prf.getString("appointment", null));

        if (prf.getString("StudentName", null).toString().equals("adminDH")){
            System.out.println("LOL");
            Menu nav_menu = navigationView.getMenu();
            nav_menu.findItem(R.id.admintools).setVisible(true);
        }

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ChapterFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_chap);
        }

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_chap) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ChapterFragment()).commit();

        } else if (id == R.id.nav_subchap) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new SubChapterFragment()).commit();
        } else if (id == R.id.create_user){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new UserFragment()).commit();
        }else if (id == R.id.logout){
            SharedPreferences.Editor editor = prf.edit();
            editor.clear();
            editor.commit();

            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        }else if (id == R.id.create_chapters){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ChaptersDBFragment()).commit();
        }else if (id == R.id.create_subchapters){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new SubChapterDBFragment()).commit();
        }else if (id == R.id.create_sections){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new SectionDBFragment()).commit();
        }else if (id == R.id.create_trans){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new TranscriptDBFragment()).commit();
        }else if (id == R.id.create_voice){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new VoiceRecordingDBFragment()).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
