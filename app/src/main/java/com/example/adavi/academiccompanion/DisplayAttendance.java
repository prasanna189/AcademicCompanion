package com.example.adavi.academiccompanion;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayAttendance extends AppCompatActivity {
    DatabaseHelper myDB;
    String s;
    int total=0;
    int absent=0;
    int present=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_attendance);
        myDB=new DatabaseHelper(this);

       // Cursor c = myDB.getAllData("attendance");
        s=getIntent().getStringExtra("subj_id");
        String sname=myDB.getSubjectName(Integer.parseInt(s));
        setTitle(sname+"  Attendance");

        DisplayViewAttendance();

    }
    public void ViewAttendance(View view)
    {
        Intent intent = new Intent(DisplayAttendance.this, ViewAttendance.class);
        intent.putExtra("subj_id",s);
        startActivity(intent);
    }

    public void DisplayViewAttendance()
    {
        TextView tv=(TextView) findViewById(R.id.display_attendance_tv);
        Cursor res=myDB.getAllData("attendance");
        while(res.moveToNext())
        {
            if(res.getInt(1)==myDB.getcurrentsem() && res.getString(2).equals(s))
            {
                if(res.getString(4).equals("Present"))
                {
                    present++;
                }
                else if(res.getString(4).equals("Absent"))
                {
                    absent++;
                }
            }
        }
        total=present+absent;
//
//        TextView p=new TextView(this);
//        TextView t=new TextView(this);
//
//        p.setText(Integer.toString(present));
//        p.setTextSize(20);
//
//        t.setText("/"+total);
//        t.setTextSize(20);

        tv.setText("Classes Attended : "+Integer.toString(present)+"/"+total);

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, DisplaySubjectDetails.class);
        intent.putExtra("sub_id",s);
        startActivity(intent);
        super.onBackPressed();
    }
}
