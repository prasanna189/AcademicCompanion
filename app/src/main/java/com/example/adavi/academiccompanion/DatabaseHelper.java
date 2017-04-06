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

    public boolean insertDataUser(String name,String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("phone",phone);
        long result = db.insert("user",null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertDataSemester(int sem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sem_id",sem);
        long result = db.insert("semester",null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }


    public boolean insertDataSubject(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select ifnull(max(subject_id), 0) from subject",null);
        int subject_id = Integer.parseInt( res.getString(0) );
        subject_id = subject_id + 1;

        ContentValues contentValues = new ContentValues();
        contentValues.put("subject_name",name);
        contentValues.put("subject_id",subject_id);
        long result = db.insert("semester",null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertDataMarks(int semid,int subid,String exam_type,int marks,int max_marks) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sem_id",semid);
        contentValues.put("subject_id",subid);
        contentValues.put("exam_type",exam_type);
        contentValues.put("marks",marks);
        contentValues.put("max_marks",max_marks);

        long result = db.insert("marks",null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(String TABLE_NAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateDataMarks(int semid,int subid,String exam_type,int marks,int max_marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sem_id",semid);
        contentValues.put("subject_id",subid);
        contentValues.put("exam_type",exam_type);
        contentValues.put("marks",marks);
        contentValues.put("max_marks",max_marks);
        db.update("marks", contentValues, "semid="+semid +"and"+ "subid="+subid+"exam_type="+exam_type ,null);

        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }

}
