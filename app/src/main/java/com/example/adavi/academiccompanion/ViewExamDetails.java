package com.example.adavi.academiccompanion;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ViewExamDetails extends AppCompatActivity {

    DatabaseHelper myDB;
    String m_Text = "";
    String examtype,s,subname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exam_details);
        myDB=new DatabaseHelper(this);

        s=getIntent().getStringExtra("subid");
        subname=myDB.getSubjectName(Integer.parseInt(s));
        examtype= getIntent().getStringExtra("examtype");
        setTitle(examtype);

        displaytypedetails();
    }
    public void displaytypedetails(){


        LinearLayout examLL = (LinearLayout) findViewById(R.id.view_exam_details);


        LinearLayout ll1 = (LinearLayout)findViewById(R.id.e1);
        LinearLayout ll2 = (LinearLayout)findViewById(R.id.e2);
        LinearLayout ll3 = (LinearLayout)findViewById(R.id.e3);
        LinearLayout ll4 = (LinearLayout)findViewById(R.id.e4);

        TextView subject = new TextView(this);
        TextView exam= new TextView(this);
        TextView marks = new TextView(this);
        TextView max_marks=new TextView(this);

        TextView subjectvalue = new TextView(this);
        TextView examvalue= new TextView(this);
        TextView marksvalue = new TextView(this);
        TextView max_marksvalue=new TextView(this);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
        );

        subject.setText("Subject : ");
        subject.setTextColor(Color.BLACK);
        subject.setTextSize(20);
        subject.setPadding(20,15,0,15);
        subject.setTypeface(null, Typeface.BOLD);
        subject.setLayoutParams(param);
        subject.setGravity(Gravity.CENTER_VERTICAL);
//        subject.setTextColor(Color.parseColor("#FF4081"));



        exam.setText("Exam Type : ");
        exam.setTextColor(Color.BLACK);
        exam.setTextSize(20);
        exam.setPadding(20,15,0,15);
        exam.setTypeface(null, Typeface.BOLD);
        exam.setLayoutParams(param);
        exam.setGravity(Gravity.CENTER_VERTICAL);

//        exam.setTextColor(Color.parseColor("#FF4081"));



        marks.setText("Marks  : ");
        marks.setTextColor(Color.BLACK);
        marks.setTextSize(20);
        marks.setPadding(20,15,0,15);
        marks.setTypeface(null, Typeface.BOLD);
        marks.setLayoutParams(param);
        marks.setGravity(Gravity.CENTER_VERTICAL);

//        marks.setTextColor(Color.parseColor("#FF4081"));



        max_marks.setText("Max Marks : ");
        max_marks.setTextColor(Color.BLACK);
        max_marks.setTextSize(20);
        max_marks.setTypeface(null, Typeface.BOLD);
        max_marks.setPadding(20,15,0,15);
        max_marks.setLayoutParams(param);
        max_marks.setGravity(Gravity.CENTER_VERTICAL);




        Cursor res=myDB.getAllData("marks");
        while(res.moveToNext())
        {
            if(res.getString(1).equals(s) && res.getString(2).equals(examtype))
            {
                subjectvalue.setText(subname);
                subjectvalue.setTextColor(Color.BLACK);
                subjectvalue.setTextSize(20);
                subjectvalue.setPadding(20,5,0,5);
                subjectvalue.setLayoutParams(param);
                subjectvalue.setGravity(Gravity.CENTER_VERTICAL);



                examvalue.setText(res.getString(2));
                examvalue.setTextColor(Color.BLACK);
                examvalue.setTextSize(20);
                examvalue.setPadding(20,5,0,5);
                examvalue.setLayoutParams(param);
                examvalue.setGravity(Gravity.CENTER_VERTICAL);



                marksvalue.setText(res.getString(3));
                marksvalue.setTextColor(Color.BLACK);
                marksvalue.setTextSize(20);
                marksvalue.setPadding(20,5,0,5);
                marksvalue.setLayoutParams(param);
                marksvalue.setGravity(Gravity.CENTER_VERTICAL);



                max_marksvalue.setText(res.getString(4));
                max_marksvalue.setTextColor(Color.BLACK);
                max_marksvalue.setTextSize(20);
                max_marksvalue.setPadding(20,5,0,5);
                max_marksvalue.setLayoutParams(param);
                max_marksvalue.setGravity(Gravity.CENTER_VERTICAL);

            }
        }

        ll1.addView(subject);
        ll1.addView(subjectvalue);

        ll2.addView(exam);
        ll2.addView(examvalue);

        ll3.addView(marks);
        ll3.addView(marksvalue);

        ll4.addView(max_marks);
        ll4.addView(max_marksvalue);



    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, ViewSubjectMarks.class);
        intent.putExtra("sub_id",s);
        startActivity(intent);
        super.onBackPressed();
    }
}
