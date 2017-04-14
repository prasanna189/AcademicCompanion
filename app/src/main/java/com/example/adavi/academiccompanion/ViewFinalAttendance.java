package com.example.adavi.academiccompanion;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewFinalAttendance extends AppCompatActivity {
    DatabaseHelper myDB;
    int flag=0;
    LinearLayout subjectLL1;
    LinearLayout subjectLL2;
    LinearLayout subjectLL3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_final_attendance);
        myDB=new DatabaseHelper(this);

        subjectLL1 = (LinearLayout) findViewById(R.id.View_att_1);
        subjectLL2 = (LinearLayout) findViewById(R.id.View_att_2);
        subjectLL3 = (LinearLayout) findViewById(R.id.View_att_3);
       // finalresult = (LinearLayout) findViewById(R.id.View_Final_Result);

        displayAttendanceHelper();

    }

    public void displayAttendanceHelper()
    {
        Cursor res = myDB.getAllData("subject_details");
        TextView t1 = new TextView(this);
        t1.setText("SUBJECT");
        t1.setTextSize(20);

        TextView t2 = new TextView(this);
        t2.setText("PRESENT");
        t2.setTextSize(20);

        TextView t3 = new TextView(this);
        t3.setText("TOTAL CLASSES");
        t3.setTextSize(20);

        subjectLL1.addView(t1);
        subjectLL2.addView(t2);
        subjectLL3.addView(t3);

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
                    display(myDB.getSubjectName(res.getInt(1)),present,present+absent);


            }

        }


    }

    public void display(String sname,int pre,int total)
    {

        TextView t1 = new TextView(this);
        t1.setText(sname);
        t1.setTextSize(20);

        TextView t2 = new TextView(this);
        t2.setText(Integer.toString(pre));
        t2.setTextSize(20);


        TextView t3 = new TextView(this);
        t3.setText(Integer.toString(total));
        t3.setTextSize(20);



        t1.setPadding(10,10,50,20);

        t2.setPadding(10,10,50,20);

        t3.setPadding(10,10,50,20);

        subjectLL1.addView(t1);
        subjectLL2.addView(t2);
        subjectLL3.addView(t3);


    }

}
