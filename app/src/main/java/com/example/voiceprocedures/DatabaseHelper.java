package com.example.voiceprocedures;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="reporting.db";
    public static final String TABLE_NAME="studentAccount";
    public static final String COL_1="ID";
    public static final String COL_2="studentName";
    public static final String COL_3="appointment";
    public static final String COL_4="department";
    public static final String COL_5="password";


    public static final String TABLE_NAME2 = "chapters";
    public static final String COL2_1 = "chapterName";
//    public static final String COL_6="username";

    public static final String TABLE_NAME3 = "subchapters";
    public static final String COL3_1 = "subchapterName";

    public static final String TABLE_NAME4 = "sections";
    public static final String COL4_1 = "sectionName";

    public static final String TABLE_NAME5 = "transcripts";
    public static final String COL5_1 = "transcriptName";

    public static final String TABLE_NAME6 = "voiceRecordings";
    public static final String COL6_1 = "studentID";
    public static final String COL6_2 = "transcriptID";
    public static final String COL6_3 = "datetime";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE studentAccount (ID INTEGER PRIMARY KEY AUTOINCREMENT, studentName Text NOT NULL, appointment Text NOT NULL, department Text NOT NULL, password Text NOT NULL)");
        sqLiteDatabase.execSQL("CREATE TABLE chapters (ID INTEGER PRIMARY KEY AUTOINCREMENT,chapterName Text NOT NULL)");
        sqLiteDatabase.execSQL("CREATE TABLE subchapters (ID INTEGER PRIMARY KEY AUTOINCREMENT, subchapterName Text NOT NULL, chapterID INTEGER, CONSTRAINT fk_chapter FOREIGN KEY (chapterID) REFERENCES chapters(ID))");
        sqLiteDatabase.execSQL("CREATE TABLE sections (ID INTEGER PRIMARY KEY AUTOINCREMENT, sectionName Text NOT NULL, subchapterID INTEGER, CONSTRAINT fk_subchapter FOREIGN KEY (subchapterID) REFERENCES subchapters(ID))");
        sqLiteDatabase.execSQL("CREATE TABLE transcripts (transcriptID INTEGER PRIMARY KEY AUTOINCREMENT, transcriptName Text NOT NULL, transcript Text NOT NULL, image Text, sectionID INTEGER, CONSTRAINT fk_section FOREIGN KEY (sectionID) REFERENCES sections(ID))");
        sqLiteDatabase.execSQL("CREATE TABLE voiceRecordings (recordingID INTEGER PRIMARY KEY AUTOINCREMENT, studentID INTEGER, transcriptID INTEGER, datetime DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ", CONSTRAINT fk_student FOREIGN KEY (studentID) REFERENCES studentAccount(ID), CONSTRAINT fk_transcript FOREIGN KEY (transcriptID) REFERENCES transcripts(transcriptID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME2 );
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME3 );
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME4 );
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME5);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME6);
    onCreate(sqLiteDatabase);
}

    //USER ACCOUNT (NEED TO FINX THE PROBLEM WITH DUPLICATED USERS!)
    public long addUser(String studentName, String appointment, String department, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("studentName", studentName);
        contentValues.put("appointment", appointment);
        contentValues.put("department", department);
        contentValues.put("password", password);

        long res = db.insert("studentAccount", null, contentValues);
        db.close();
        return res;
    }

    public boolean checkUser(String studentName, String password){
        String[] columns = { COL_1 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_5 + "=?";
        String[] selectionArgs = {studentName, password};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();

        if (count > 0){
            return true;
        }else{
            return false;
        }

    }

    public void deleteUsers(String Stuname){
        SQLiteDatabase db = this.getWritableDatabase();
        // "' AND ID = '" + StuID +
        String Query = "DELETE FROM studentAccount WHERE studentName = '" + Stuname + "'";

        db.execSQL(Query);
        db.close();
//        String whre = COL_1 + "=" + StuID ;
//        return db.delete(TABLE_NAME, whre, null) > 0;

    }

    public Cursor studentDetails(@NonNull String Stuname){
        SQLiteDatabase db = this.getWritableDatabase();
//      "' AND ID = '" + StuID +
        String Query = "SELECT * FROM studentAccount WHERE studentName = '" + Stuname + "'";
//        String Query = "SELECT * FROM studentAccount WHERE ID = '" + StuID + "'";
        Cursor cursor = db.rawQuery(Query,null);
        return cursor;
    }

    public Cursor oneUserData(String studentNames, String passwords){
        SQLiteDatabase db = this.getWritableDatabase();
//        String[] columns = { COL_1 };
//        String selection = COL_2 + "=?" + " and " + COL_5 + "=?";
//        String[] selectionArgs = {studentNames, passwords};
        String Query = "SELECT * FROM studentAccount WHERE studentName = '" + studentNames + "' AND password = '" + passwords + "'";
        Cursor cursor = db.rawQuery(Query, null);
        return cursor;
    }

    public Cursor allUserData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query  = "SELECT * FROM studentAccount";
        Cursor cursor = db.rawQuery(Query, null);
        return cursor;
    }

    public void editUser(String Stuname, String name, String appoint, String Dept, String Password){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "UPDATE " + TABLE_NAME + " SET " + COL_2 + " = '"
                        + name +"', " + COL_3 + " = '" + appoint + "' , "
                        + COL_4 + " = '" + Dept + "', " + COL_5 + " = '"
                        + Password + "' WHERE studentName = '" + Stuname + "'";
        db.execSQL(Query) ;
    }

//    public Cursor resetrows(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query2 = "UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='" + TABLE_NAME +"'";
////        String Query = "ALTER TABLE " + TABLE_NAME + " AUTO_INCREMENT = 0";
//        return db.rawQuery(query2, null);
////        db.execSQL(query2);
//    }


//    public Cursor getall(){
//        SQLiteDatabase
//    }

    //CHAPTERS
    public long addChapter(String ChapterName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("chapterName", ChapterName);

        long res = db.insert("chapters", null, contentValues);
        db.close();
        return res;
    }

    public Cursor allChapterData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM chapters";
        Cursor cursor = db.rawQuery(Query, null);
        return cursor;
    }

    public Cursor chaptDetails(@NonNull String chaptname){
        SQLiteDatabase db = this.getWritableDatabase();
//      "' AND ID = '" + StuID +
        String Query = "SELECT * FROM chapters WHERE chapterName = '" + chaptname + "'";
//        String Query = "SELECT * FROM studentAccount WHERE ID = '" + StuID + "'";
        Cursor cursor = db.rawQuery(Query,null);
        return cursor;
    }

    public void editChapter(String chaptname, String chaptnames){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "UPDATE " + TABLE_NAME2 + " SET " + COL2_1 + " = '"
                + chaptnames + "' WHERE " + COL2_1  + " = '" + chaptname + "'";
        db.execSQL(Query) ;
    }

    public void deleteChapter(String chaptID, String chaptname){
        SQLiteDatabase db = this.getWritableDatabase();
        // "' AND ID = '" + StuID +
        String Query = "DELETE FROM " + TABLE_NAME2 + " WHERE " + COL2_1 + " = '" + chaptname + "'";

        //FOR SAFE DELETE IN DB
        String Query2 = "DELETE FROM " + TABLE_NAME3 +" WHERE chapterID ='" + chaptID + "'" ;
        db.execSQL(Query);
        db.execSQL(Query2);
        db.close();
//        String whre = COL_1 + "=" + StuID ;
//        return db.delete(TABLE_NAME, whre, null) > 0;

    }

    //SUBCHAPTERS
    public List<String> allchapterdatas(){
        List<String> items = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM chapters";
        Cursor cursor = db.rawQuery(Query, null);

        if (cursor.moveToFirst()) {
            do {
                items.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        return items;
    }

    public Cursor allSubChapterData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM subchapters";
        Cursor cursor = db.rawQuery(Query, null);
        return cursor;
    }

    public long addSubChapter(String chaptID, String subChapterName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("subchapterName", subChapterName);
        contentValues.put("chapterID", Integer.parseInt(chaptID));

        long res = db.insert("subchapters", null, contentValues);
        db.close();
        return res;
    }

    public Cursor subchaptDetails(@NonNull String chaptname){
        SQLiteDatabase db = this.getWritableDatabase();
//      "' AND ID = '" + StuID +
        String Query = "SELECT * FROM chapters INNER JOIN subchapters ON chapters.ID = subchapters.chapterID WHERE subchapters.subchapterName = '" + chaptname + "'";
//      String Query2 = "SELECT * FROM chapters WHERE chapterName = '" + chaptname + "'";
//      String Query = "SELECT * FROM studentAccount WHERE ID = '" + StuID + "'";
        Cursor cursor = db.rawQuery(Query,null);
        return cursor;
    }

    public void editSubChapter(String subchaptname1, String subchaptname, String chaptID){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "UPDATE " + TABLE_NAME3 + " SET " + COL3_1 + " = '" + subchaptname + "', chapterID = " + Integer.parseInt(chaptID) + " WHERE "+ COL3_1  + " = '" + subchaptname1 + "'";
        db.execSQL(Query) ;
    }

    public void deletesubChapter(String subchaptID, String subchaptname){
//    public void deletesubChapter(String subchaptname){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "DELETE FROM " + TABLE_NAME3 + " WHERE " + COL3_1 + " = '" + subchaptname + "'";

        //FOR SAFE DELETE IN DB
        String Query2 = "DELETE FROM " + TABLE_NAME4 +" WHERE subchapterID ='" + subchaptID + "'" ;
        db.execSQL(Query);
        db.execSQL(Query2);
        db.close();

    }

    //SECTIONS
    public Cursor allsectData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM sections";
        Cursor cursor = db.rawQuery(Query, null);
        return cursor;
    }

    public List<String> allsubchapterdatas(){
        List<String> items = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM subchapters";
        Cursor cursor = db.rawQuery(Query, null);

        if (cursor.moveToFirst()) {
            do {
                items.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        return items;
    }

    public long addsect(String subchapterID, String sectionName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sectionName", sectionName);
        contentValues.put("subchapterID", Integer.parseInt(subchapterID));

        long res = db.insert("sections", null, contentValues);
        db.close();
        return res;
    }

    public Cursor sectDetails(@NonNull String sectname){
        SQLiteDatabase db = this.getWritableDatabase();
//      "' AND ID = '" + StuID +
        String Query = "SELECT * FROM ((chapters INNER JOIN subchapters ON chapters.ID = subchapters.chapterID) INNER JOIN sections ON sections.subchapterID = subchapters.ID ) WHERE sections.sectionName ='" + sectname + "'";
//      String Query2 = "SELECT * FROM chapters WHERE chapterName = '" + chaptname + "'";
//      String Query = "SELECT * FROM studentAccount WHERE ID = '" + StuID + "'";
        Cursor cursor = db.rawQuery(Query,null);
        return cursor;
    }

    public void editSection(String sectname1, String sectname, String subchaptID){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "UPDATE " + TABLE_NAME4 + " SET " + COL4_1 + " = '" + sectname + "', subchapterID = " + Integer.parseInt(subchaptID) + " WHERE "+ COL4_1  + " = '" + sectname1 + "'";
        db.execSQL(Query) ;
    }

    public void deletesect(String sectname){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "DELETE FROM " + TABLE_NAME4 + " WHERE " + COL4_1 + " = '" + sectname + "'";

        //FOR SAFE DELETE IN DB
//        String Query2 = "DELETE FROM " + TABLE_NAME4 +" WHERE subchapterID ='" + chaptID + "'" ;
        db.execSQL(Query);
//        db.execSQL(Query2);
        db.close();

    }

    //Transcripts
    public Cursor alltransData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM transcripts";
        Cursor cursor = db.rawQuery(Query, null);
        return cursor;
    }

    public List<String> allsectiondatas(){
        List<String> items = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM sections";
        Cursor cursor = db.rawQuery(Query, null);

        if (cursor.moveToFirst()) {
            do {
                items.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        return items;
    }

    public long addTrans(String transName, String transcript, String imgLocation, String sectionID ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("transcriptName", transName);

        contentValues.put("transcript", transcript);

        contentValues.put("image", imgLocation);

        contentValues.put("sectionID", sectionID);

        long res= db.insert("transcripts", null, contentValues);
        db.close();
        return res;
//        try{
////            FileInputStream fc = new FileInputStream(imgLocation);
////
////            byte[] imgbyte = new byte[fc.available()];
////
////            fc.read(imgbyte);
//
//            ContentValues contentValues = new ContentValues();
//
//            contentValues.put("transcriptName", transName);
//
//            contentValues.put("transcript", transcript);
//
//            contentValues.put("image", imgLocation);
//
//            long res= db.insert("transcripts", null, contentValues);
//            db.close();
//            return res;
//        }catch (IOException e){
//            e.printStackTrace();
//            long res = 0;
//            return res;
//        }
    }

    public Cursor transDetails(@NonNull String transname){
        SQLiteDatabase db = this.getWritableDatabase();
//      "' AND ID = '" + StuID +
        String Query = "SELECT * FROM transcripts WHERE transcriptName ='" + transname + "'";
//      String Query2 = "SELECT * FROM chapters WHERE chapterName = '" + chaptname + "'";
//      String Query = "SELECT * FROM studentAccount WHERE ID = '" + StuID + "'";
        Cursor cursor = db.rawQuery(Query,null);
        return cursor;
    }

    public void deletetrans(String transName){
        SQLiteDatabase db = this.getWritableDatabase();
        // "' AND ID = '" + StuID +
        String Query = "DELETE FROM " + TABLE_NAME5 + " WHERE " + COL5_1 + " = '" + transName + "'";
        db.execSQL(Query);
        db.close();
//        String whre = COL_1 + "=" + StuID ;
//        return db.delete(TABLE_NAME, whre, null) > 0;
    }

    public void editTrans(String oldtransName,String transName, String transTxt, String imglocation){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "UPDATE " + TABLE_NAME5 + " SET " + COL5_1 + " = '" + transName + "', transcript = '" + transTxt + "', image = '" + imglocation +"'" + " WHERE "+ COL5_1  + " = '" + oldtransName + "'";
        db.execSQL(Query) ;
    }

    //MAIN FUNCTIONS
    public void DELETE_DB(){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "DELETE FROM sections";
        String query2 = "UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='" + TABLE_NAME +"'";
        db.execSQL(Query);
        db.execSQL(query2);
    }
}
