package com.example.adavi.academiccompanion;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewFinalAttendance extends AppCompatActivity {
    DatabaseHelper myDB;
    int flag=0;
    LinearLayout subjectLL1;
    LinearLayout subjectLL2;
    LinearLayout subjectLL3;
    LinearLayout subjectLL4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_final_attendance);
        myDB=new DatabaseHelper(this);
        setTitle("Attendance");
        subjectLL1 = (LinearLayout) findViewById(R.id.View_att_1);
        subjectLL2 = (LinearLayout) findViewById(R.id.View_att_2);
        subjectLL3 = (LinearLayout) findViewById(R.id.View_att_3);
        subjectLL4 = (LinearLayout) findViewById(R.id.View_att_4);
       // finalresult = (LinearLayout) findViewById(R.id.View_Final_Result);

        displayAttendanceHelper();

    }

    public void displayAttendanceHelper()
    {
        Cursor res = myDB.getAllData("subject_details");
        TextView t1 = new TextView(this);
        t1.setText("SUBJECT");
        t1.setTextSize(16);
        t1.setTypeface(null, Typeface.BOLD);

        TextView t2 = new TextView(this);
        t2.setText("PRESENT ");
        t2.setTextSize(16);
        t2.setTypeface(null, Typeface.BOLD);


        TextView t3 = new TextView(this);
        t3.setText("TOTAL CLASSES");
        t3.setTextSize(16);
        t3.setTypeface(null, Typeface.BOLD);

        TextView t4 = new TextView(this);
        t4.setText("Per%");
        t4.setTextSize(16);
        t4.setTypeface(null, Typeface.BOLD);


        t1.setPadding(10,10,0,10);
        t2.setPadding(0,10,0,10);
        t3.setPadding(0,10,0,10);
        t4.setPadding(0,10,0,10);

        subjectLL1.addView(t1);
        subjectLL2.addView(t2);
        subjectLL3.addView(t3);
        subjectLL4.addView(t4);

        while(res.moveToNext())
        {
            if(res.getInt(0)==myDB.getcurrentsem())
            {
                int present=0;
                int absent=0;
                Cursor c=myDB.getAllData("attendance");

                    while(c.moveToNext())
                    {
                        if(res.getInt(1)==c.getInt(2))
                        {

                            if (c.getString(4).equals("Present")) {
                                present++;
                            } else if (c.getString(4).equals("Absent")) {
                                absent++;
                            }
                        }
                    }

                    display(res.getInt(1),myDB.getSubjectName(res.getInt(1)),present,present+absent,(present*100/(present+absent)));


            }

        }


    }

    public void display(int subid,String sname,int pre,int total,float a)
    {
        int minat=0;

        TextView t1 = new TextView(this);
        t1.setText(sname);
        t1.setTextSize(15);

        TextView t2 = new TextView(this);
        t2.setText(Integer.toString(pre));
        t2.setTextSize(15);


        TextView t3 = new TextView(this);
        t3.setText(Integer.toString(total));
        t3.setTextSize(15);

        TextView t4 = new TextView(this);
        t4.setText(""+a);
        t4.setTextSize(15);
        Cursor c = myDB.getAllData("subject_details");
        while(c.moveToNext())
        {
            if(c.getInt(1)==subid)
            {
                minat=c.getInt(4);
            }
        }

        t1.setPadding(5,10,0,20);
        t1.setGravity(Gravity.CENTER_VERTICAL);
        t2.setPadding(5,10,0,20);
        t2.setGravity(Gravity.CENTER_VERTICAL);

        t3.setPadding(5,10,0,20);
        t3.setGravity(Gravity.CENTER_VERTICAL);
       // Toast.makeText(ViewFinalAttendance.this,""+subid, Toast.LENGTH_SHORT).show();
        if(a<(float)minat)
        {
            t1.setTextColor(Color.RED);
            t2.setTextColor(Color.RED);
            t3.setTextColor(Color.RED);
            t4.setTextColor(Color.RED);
        }
        t4.setPadding(5,10,0,20);
        t4.setGravity(Gravity.CENTER_VERTICAL);
        subjectLL1.addView(t1);
        subjectLL2.addView(t2);
        subjectLL3.addView(t3);
        subjectLL4.addView(t4);


    }

}
