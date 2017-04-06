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
        db.execSQL("create table user_details( name text not null, email text not null, phone text)");
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
        db.execSQL("DROP TABLE IF EXISTS user_details");
        db.execSQL("DROP TABLE IF EXISTS semester");
        db.execSQL("DROP TABLE IF EXISTS subject");
        db.execSQL("DROP TABLE IF EXISTS subject_details");
        db.execSQL("DROP TABLE IF EXISTS marks");
        db.execSQL("DROP TABLE IF EXISTS attendance");
        db.execSQL("DROP TABLE IF EXISTS timetable");
        db.execSQL("DROP TABLE IF EXISTS event");

        onCreate(db);
    }

    public boolean insertDataUserDetails(String name,String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("phone",phone);
        long result = db.insert("user_details",null ,contentValues);
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
        long result = db.insert("subject",null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertDataSubjectDetails(int sem_id,int subject_id,String prof_name,String prof_email,int min_attendance,String status, int credits, String grade, int lab,String description ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sem_id",sem_id);
        contentValues.put("subject_id",subject_id);
        contentValues.put("prof_name",prof_name);
        contentValues.put("prof_email",prof_email);
        contentValues.put("min_attendance",min_attendance);
        contentValues.put("status",status);
        contentValues.put("credits",credits);
        contentValues.put("grade",grade);
        contentValues.put("lab",lab);
        contentValues.put("description", description);
        long result = db.insert("subject_details",null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }


    public boolean insertDataAttendance(int sem_id,int subject_id,String date,String status,int is_extra_class) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sem_id",sem_id);
        contentValues.put("subject_id",subject_id);
        contentValues.put("date",date);
        contentValues.put("status",status);
        contentValues.put("isExtraClass",is_extra_class);
        long result = db.insert("attendance",null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertDataTimeTable(int sem_id,int subject_id,String day,String startTime,String endTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sem_id",sem_id);
        contentValues.put("subject_id",subject_id);
        contentValues.put("day",day);
        contentValues.put("startTime",startTime);
        contentValues.put("endTime",endTime);
        long result = db.insert("timetable",null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertDataEvent(String event_name,String date,String startTime,String endTime,int subject_id,String description,String remainderTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select ifnull(max(event_id), 0) from event",null);
        int event_id = Integer.parseInt( res.getString(0) );
        event_id = event_id + 1;
        ContentValues contentValues = new ContentValues();
        contentValues.put("event_id",event_id);
        contentValues.put("event_name",event_name);
        contentValues.put("date",date);
        contentValues.put("startTime",startTime);
        contentValues.put("endTime",endTime);
        contentValues.put("subject_id",subject_id);
        contentValues.put("description",description);
        contentValues.put("remainderTime",remainderTime);
        long result = db.insert("event",null ,contentValues);
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


    public boolean updateDataUserDetails(String name,String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("phone",phone);
        long result=db.update("user_details",contentValues,"name="+name,null)
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean updateDataSemester(int sem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sem_id",sem);
        long result=db.update("semester",contentValues,"sem_id="+sem,null);
        if(result == -1)
            return false;
        else
            return true;
    }


    public boolean updateDataSubject(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("subject_name",name);

        long result = db.update("subject",contentValues,"name="+name,null);
        if(result == -1)
            return false;
        else
            return true;
    }


    public boolean updateDataSubject_details(int sem_id,int subject_id,String prof_name,String prof_email,int min_attendance,String status,int credits,String grade,int lab,String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sem_id",sem_id);
        contentValues.put("subject_id",subject_id);
        contentValues.put("prof_name",prof_name);
        contentValues.put("prof_email",prof_email);
        contentValues.put("min_attendance",min_attendance);
        contentValues.put("status",status);
        contentValues.put("credits",credits);
        contentValues.put("grade",grade);
        contentValues.put("lab",lab);
        contentValues.put("description",description);
        long result=db.update("subject_details",contentValues,"subject_id="+subject_id+"sem_id="+sem_id,null);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean updateDataEvent(int event_id,String event_name,String date,String startTime,String endTime,int subject_id,String description,String remainderTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("event_id",event_id);
        contentValues.put("event_name",event_name);
        contentValues.put("date",date);
        contentValues.put("startTime",startTime);
        contentValues.put("endTime",endTime);
        contentValues.put("subject_id",subject_id);
        contentValues.put("description",description);
        contentValues.put("remainderTime",remainderTime);
        long result=db.update("event", contentValues, "event_id = "+event_id+"",null);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean updatetDataTimeTable(int sem_id,int subject_id,String day,String startTime,String endTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sem_id",sem_id);
        contentValues.put("subject_id",subject_id);
        contentValues.put("day",day);
        contentValues.put("startTime",startTime);
        contentValues.put("endTime",endTime);
        long result=db.update("timetable", contentValues, "subject_id = "+subject_id+" and sem_id = "+sem_id+" ",null);
        if(result == -1)
            return false;
        else
            return true;
    }


    public boolean updateDataMarks(int semid,int subid,String exam_type,int marks,int max_marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sem_id",semid);
        contentValues.put("subject_id",subid);
        contentValues.put("exam_type",exam_type);
        contentValues.put("marks",marks);
        contentValues.put("max_marks",max_marks);
        long result=db.update("marks", contentValues, "sem_id="+semid +"and"+ "subject_id="+subid+"exam_type="+exam_type ,null);

        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean updatetDataAttendance(int sem_id,int subject_id,String date,String status,int is_extra_class) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sem_id",sem_id);
        contentValues.put("subject_id",subject_id);
        contentValues.put("date",date);
        contentValues.put("status",status);
        contentValues.put("isExtraClass",is_extra_class);
       long result= db.update("attendance", contentValues, "sem_id="+sem_id +"and"+ "subject_id="+subject_id+"date="+date ,null);

        if(result == -1)
            return false;
        else
            return true;
    }


    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }

}
