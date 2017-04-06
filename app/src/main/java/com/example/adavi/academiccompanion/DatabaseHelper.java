package com.example.adavi.academiccompanion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pk on 4/6/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "AcademicCompanion.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user( name text not null, email text not null, phone text)");
        db.execSQL("create table semester(sem_id integer primary key)");
        db.execSQL("create table subject(subject_id integer primary key,subject_name text)");
        db.execSQL("create table subject_details(sem_id integer, subject_id integer, prof_name text , prof_email text, min_attendance integer,status text, credits integer, grade text, lab integer, description text, foreign key (sem_id) references semester(sem_id), foreign key (subject_id) references  subject(subject_id) )");
        db.execSQL("create table marks (sem_id integer, subject_id integer, exam_type text, marks integer, max_marks integer, foreign key (sem_id) references semester(sem_id), foreign key (subject_id) references subject(subject_id))");
        db.execSQL("create table attendance (sem_id integer, subject_id integer, date text, status text, isExtraClass integer,foreign key (sem_id) references semester(sem_id), foreign key (subject_id) references subject(subject_id))");
        db.execSQL("create table timetable (sem_id integer, subject_id integer, day text, startTime text, endTime text, foreign key (sem_id) references semester(sem_id), foreign key (subject_id) references subject(subject_id) )");
        db.execSQL("create table event ( event_id integer primary key, event_name text,date text, startTime text, endTime text, subject_id integer, description text, remainderTime text , foreign key (subject_id) references subject(subject_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name,String surname,String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,marks);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String id,String name,String surname,String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,marks);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }

}
