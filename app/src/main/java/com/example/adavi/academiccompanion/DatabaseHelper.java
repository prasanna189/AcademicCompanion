package com.example.adavi.academiccompanion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.R.attr.id;
import static android.R.attr.order;
import static android.R.attr.type;
import static com.example.adavi.academiccompanion.R.id.sem_id;
import static com.example.adavi.academiccompanion.ScheduleActivity.formattedDate;

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

        db.execSQL("create table if not exists user_details( name text not null, email text not null, phone text, current_sem integer not null);");
        db.execSQL("create table if not exists  semester(sem_id integer primary key, start_date text, end_date text);");
        db.execSQL("create table if not exists subject(subject_id integer primary key,subject_name text);");
        db.execSQL("create table if not exists subject_details(sem_id integer, subject_id integer, prof_name text , prof_email text, min_attendance integer,status text, credits integer, grade text, lab integer, description text, foreign key (sem_id) references semester(sem_id), foreign key (subject_id) references  subject(subject_id) );");
        db.execSQL("create table if not exists  marks (sem_id integer, subject_id integer, exam_type text, marks integer, max_marks integer, foreign key (sem_id) references semester(sem_id), foreign key (subject_id) references subject(subject_id));");
        db.execSQL("create table if not exists  attendance (attendance_id integer primary key, sem_id integer, subject_id integer, date text, status text, isExtraClass integer,foreign key (sem_id) references semester(sem_id), foreign key (subject_id) references subject(subject_id));");
        db.execSQL("create table if not exists  timetable (timetable_id integer primary key, sem_id integer, subject_id integer, day text, startTime text, endTime text, foreign key (sem_id) references semester(sem_id), foreign key (subject_id) references subject(subject_id));");
        db.execSQL("create table if not exists  event ( event_id integer primary key, event_name text,date text, startTime text, endTime text, subject_id integer, description text, remainderTime text ,eventType text, remainderDate text, foreign key (subject_id) references subject(subject_id));");
        db.execSQL("create table if not exists  ac_images ( image_name text, image_data blob);");
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
        db.execSQL("DROP TABLE IF EXISTS ac_images");
        onCreate(db);
    }


    public String getDBName()
    {
        return DATABASE_NAME;
    }

    public boolean insertDataUserDetails(String name,String email, String phone, String semester) {
        SQLiteDatabase db = this.getWritableDatabase();
        int current_sem = Integer.parseInt(semester);
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("phone",phone);
        contentValues.put("current_sem",current_sem);
        long result = db.insert("user_details",null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertDataSemester(String semester, String start_date, String end_date) {

        int sem = Integer.parseInt(semester);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sem_id",sem);
        long result = db.insert("semester",null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }


    public int insertDataSubject(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int maxid=-1;
        Cursor res = db.rawQuery("select * from subject",null);
        while (res.moveToNext()) {
            if(res.getInt(0)>maxid)
            {
                maxid=res.getInt(0);
            }
        }
        maxid=maxid+1;
//        int subject_id = Integer.parseInt( res.getString(0) );
//        subject_id = subject_id + 1;
        ContentValues contentValues = new ContentValues();
        contentValues.put("subject_id",maxid);
        contentValues.put("subject_name",name);
        long result = db.insert("subject",null ,contentValues);
        if(result == -1)
            return -1;
        else
            return maxid;
//        String ROW1 = "INSERT INTO " + "subject" + " ("
//                + "subject_id"+ ", " + "subject_name" + ") Values ( "+subject_id+","+name+");";
//        db.execSQL(ROW1);
//        return  subject_id;
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
        Cursor res = db.rawQuery("select ifnull(max(attendance_id), 0) from attendance",null);
        int attendance_id = Integer.parseInt( res.getString(0) );
        attendance_id = attendance_id + 1;

        contentValues.put("attendance_id",attendance_id);
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
        int id=0;
        Cursor res = db.rawQuery("select * from timetable", null);
        while(res.moveToNext())
        {
            if(res.getInt(0)>id)
            {
                id=res.getInt(0);
            }
        }
        id=id+1;
        ContentValues contentValues = new ContentValues();
        contentValues.put("timetable_id",id);
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

    public boolean insertDataEvent(String event_name,String date,String startTime,String endTime,int subject_id,String description,String remainderTime, String eventType, String remainderDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        int event_id=0;
        Cursor res = db.rawQuery("SELECT ifnull(max(event_id),0) FROM event", null);
//        Cursor res = db.query("event","event_id",null,null,null,null,null)
        while(res.moveToNext())
        {
            if(res.getInt(0)> event_id)
            {
                event_id=res.getInt(0);
            }
        }
//        event_id=res.getInt(0);
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
        contentValues.put("eventType",eventType);
        contentValues.put("remainderDate",remainderDate);
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

    public void setCurrentSem(String semester)
    {
        int sem = Integer.parseInt(semester);
        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("sem_id",sem);
//        long result=db.update("user",contentValues,"",null);

        db.execSQL("update user_details set current_sem = "+semester);

        System.out.println("pk");

    }



    public Cursor getAllData(String TABLE_NAME) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME, null);
        return res;

    }

    public String getSubjectName(int sub_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from subject",null);
        while(res.moveToNext())
        {
            if(res.getInt(0)==sub_id)
            {
                return res.getString(1);
            }
        }
        return null;
    }

    public String getUserPhone()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from user_details",null);
        boolean i=res.moveToNext();
        if(i)
        {
            return res.getString(2);
        }
        else
            return null;


    }
    public String getUserName()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from user_details",null);
        boolean i=res.moveToNext();
        if(i)
        {
            return res.getString(0);
        }
        else
            return null;


    }

    public String getUserEmail()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from user_details",null);
        boolean i=res.moveToNext();
        if(i)
        {
            return res.getString(1);
        }
        else
            return null;


    }

    public int getcurrentsem()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from user_details",null);
        boolean i=res.moveToNext();
        if(i)
        {
            return res.getInt(3);
        }
        else
            return -1;


    }

    public int gettotalmarks(int sem_id,int sub_id)
    {
        int totalmarks=0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from marks where sem_id = "+sem_id+" and subject_id = "+sub_id,null);
        while(res.moveToNext()){

            totalmarks=totalmarks+res.getInt(3);
        }

        return totalmarks;

    }


    public Cursor getRecentEvents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from event order by date and startTime and endTime DESC",null);
        return res;
    }

    public Cursor getTodayEvents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        String formattedTime = tf.format(c.getTime());
        Cursor res = db.rawQuery("select * from event where date= '"+formattedDate+"' order by startTime ASC",null);
        return res;
    }

    public Cursor getCurrentSemSubjects(int sem)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select s.subject_id,s.subject_name from subject s,subject_details sd where s.subject_id=sd.subject_id and sd.sem_id="+sem+"",null);

        return res;
    }

    public boolean updateDataSemester(int sem, String start_date, String end_date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sem_id",sem);
        contentValues.put("start_date",start_date);
        contentValues.put("end_date",end_date);
        long result=db.update("semester",contentValues,"sem_id="+sem,null);
        if(result == -1)
            return false;
        else
            return true;
    }


    public boolean updateDataUserDetails(String name,String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("phone",phone);
        contentValues.put("current_sem",getcurrentsem());
        long result=db.update("user_details",contentValues,null,null);
        if(result == -1)
            return false;
        else
            return true;
    }


    public boolean updateDataSubject(String name,int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("subject_name",name);

        long result = db.update("subject",contentValues,"subject_id="+Integer.toString(id),null);
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
        long result=db.update("subject_details",contentValues,"subject_id="+Integer.toString(subject_id)+" and sem_id="+Integer.toString(sem_id),null);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean updateDataEvent(int event_id,String event_name,String date,String startTime,String endTime,int subject_id,String description,String remainderTime,String eventType, String remainderDate) {
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
        contentValues.put("eventType",eventType);
        contentValues.put("remainderDate",remainderDate);
        long result=db.update("event", contentValues, "event_id = "+event_id+"",null);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean updatetDataTimeTable(int id, int sem_id,int subject_id,String day,String startTime,String endTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sem_id",sem_id);
        contentValues.put("subject_id",subject_id);
        contentValues.put("day",day);
        contentValues.put("startTime",startTime);
        contentValues.put("endTime",endTime);
        long result=db.update("timetable", contentValues, "timetable_id = "+id+" ",null);
        if(result == -1)
            return false;
        else
            return true;
    }


    public boolean updateDataMarks(int semid,int subid,String exam_type,int marks,int max_marks,String new_examtype) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sem_id",semid);
        contentValues.put("subject_id",subid);
        contentValues.put("exam_type",new_examtype);
        contentValues.put("marks",marks);
        contentValues.put("max_marks",max_marks);
        long result=db.update("marks", contentValues, "sem_id="+semid+" and subject_id="+subid+" and exam_type='"+exam_type+"'" ,null);

        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean updatetDataAttendance(int attendance_id,int sem_id,int subject_id,String date,String status,int is_extra_class) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("attendance_id",attendance_id);
        contentValues.put("sem_id",sem_id);
        contentValues.put("subject_id",subject_id);
        contentValues.put("date",date);
        contentValues.put("status",status);
        contentValues.put("isExtraClass",is_extra_class);
       long result= db.update("attendance", contentValues, "attendance_id="+attendance_id+"" ,null);

        if(result == -1)
            return false;
        else
            return true;
    }

//
//    public Integer deleteData (String id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
//    }

    public boolean deleteDataSemester (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result= db.delete("semester", "sem_id = "+id+"",null);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean deleteDataSubject (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result= db.delete("subject", "subject_id = "+id+"",null);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean deleteDataSubjectDetails (int sub_id,int sem_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result= db.delete("subject_details", "subject_id = "+sub_id+" and sem_id = "+sem_id+"",null);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean deleteDataMarks (int sub_id,int sem_id,String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result= db.delete("marks", "subject_id = "+sub_id+" and sem_id = "+sem_id+" and exam_type = '"+type+"'",null);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean deleteMarksOnSubDelete (int sub_id,int sem_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result= db.delete("marks", "subject_id = "+sub_id+" and sem_id = "+sem_id,null);
        if(result == -1)
            return false;
        else
            return true;
    }




    public boolean deleteDataAttendance (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result= db.delete("attendance", "attendance_id = "+id+"",null);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean deleteDataTimetable (int sub_id,int sem_id,String day,String stime,String etime) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result= db.delete("subject", "subject_id = "+sub_id+" and sem_id = "+sem_id+" and day = "+day+" and startTime = "+stime+"and endTime = "+etime+"",null);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean deleteDataEvent (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result= db.delete("event", "event_id = "+id+"",null);
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean insertDataImages( String name, byte[] image){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new  ContentValues();
        cv.put("image_name",    name);
        cv.put("image_data",   image);
        long result = database.insert( "ac_images", null, cv );

        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public void updategrade(String grad,int subid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("update subject_details set grade='"+grad+"' where subject_id="+subid+";",null);

    }
    public boolean updateImage(String name, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new  ContentValues();
        cv.put("image_name",    name);
        cv.put("image_data",   image);
        long result = db.update("ac_images",cv,null,null);
        if(result == -1)
            return false;
        else
            return true;
    }
    public byte[] getImage(String image_name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        byte[] image = null;
        Cursor res = db.rawQuery("select * from ac_images",null);
        while(res.moveToNext())
        {
            if(res.getString(0).equals(image_name))
            {
                image = res.getBlob(1);
            }
        }
        return image;
    }


    ////////////////  CODE FOR ANDROID DATABASE MANAGER

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }


    }


}
