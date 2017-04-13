package com.example.adavi.academiccompanion;

import android.database.Cursor;
import android.icu.text.DateFormat;
////import android.icu.text.SimpleDateFormat;
//import android.icu.text.SimpleDateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.ParseException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.icu.util.Calendar.getInstance;


public class ViewAttendance extends AppCompatActivity {

    DatabaseHelper myDB;
    String s;
    Spinner AttendanceList;
    ArrayAdapter<String> adapter;
    String filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);
        myDB=new DatabaseHelper(this);
        s=getIntent().getStringExtra("sub_id");

        AttendanceList=(Spinner) findViewById(R.id.attendance_spinner);

        String [] subject_array={"All","Present","Absent","ExtraClass"};

        adapter=new ArrayAdapter<String>(this,
                R.layout.attendance_filter,R.id.Attendance_list, subject_array);

        AttendanceList.setAdapter(adapter);

        AttendanceList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int
                    position, long id) {

                ViewGroup vg=(ViewGroup)view;

                TextView tv=(TextView)vg.findViewById(R.id.Attendance_list);
                filter=tv.getText().toString();

            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {


                filter=null;
            }

        });

        display(filter);

    }

    public void display(String attfilter)
    {
        String startdate="2017-03-20";
        Cursor res=myDB.getAllData("semester");
//        while(res.moveToNext())
//        {
//            if(res.getInt(1)==myDB.getcurrentsem())
//            {
//                 startdate=res.getString(2);
//            }
//        }

        Calendar c = Calendar.getInstance();


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dayformat = new SimpleDateFormat("EEEE");
        String formattedDate = df.format(c.getTime());


       // java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(ViewAttendance.this);


        LinearLayout ll=(LinearLayout)findViewById(R.id.att);
        //android.text.format.DateFormat.format("yyyy-MM-dd", new java.util.Date());
            List<Date> dates = getDates(startdate, formattedDate);
        for(Date date:dates)
        {
            String day=dayformat.format(date);
            Cursor tt=myDB.getAllData("timetable");
            //Toast.makeText(ViewAttendance.this, tt.getString(3), Toast.LENGTH_SHORT).show();

            while(tt.moveToNext())
            {

                if(myDB.getcurrentsem()==tt.getInt(1) && tt.getString(2).equals(s) && tt.getString(3).equals(day))
                {
                   // boolean i= myDB.insertDataAttendance(tt.getInt(1),tt.getInt(2),df.format(date),"",-1);

                    TextView tv =new TextView(this);
                    tv.setText(df.format(date));
                    tv.setTextSize(30);
//                    CheckBox ch1=new CheckBox(this);
//                    CheckBox ch2=new CheckBox(this);
//                    CheckBox ch3=new CheckBox(this);
//
//                    ch1.setId(myDB.AttendanceId(tt.getInt(1),tt.getInt(2),df.format(date)));
//                    ch2.setId(myDB.AttendanceId(tt.getInt(1),tt.getInt(2),df.format(date)));
//                    ch3.setId(myDB.Attendance(tt.getInt(1),tt.getInt(2),df.format(date)));

                    ll.addView(tv);

                }
            }


            //tv.setText(df.format(date));

        }

    }
    private static List<Date> getDates(String dateString1, String dateString2)
    {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1.parse(dateString1);
            date2 = df1.parse(dateString2);
        }
        catch (Exception e) {
            return  null;
        }

        Calendar cal1 = getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while(!cal1.after(cal2))
        {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }
}
