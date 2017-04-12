package com.example.adavi.academiccompanion;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        LinearLayout ll = new LinearLayout(this);

        TextView subject = new TextView(this);
        TextView exam= new TextView(this);
        TextView marks = new TextView(this);
        TextView max_marks=new TextView(this);

        TextView subjectvalue = new TextView(this);
        TextView examvalue= new TextView(this);
        TextView marksvalue = new TextView(this);
        TextView max_marksvalue=new TextView(this);

        LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        ll.setLayoutParams(ll_params);
        ll.setOrientation(LinearLayout.VERTICAL);
        subject.setText("Subject Name");
        subject.setTextSize(20);
        exam.setText("Exam Type");
        exam.setTextSize(20);
        marks.setText("Marks Obtained");
        marks.setTextSize(20);
        max_marks.setText("MAX MARKS");
        max_marks.setTextSize(20);

        Cursor res=myDB.getAllData("marks");
        while(res.moveToNext())
        {
            if(res.getString(1).equals(s) && res.getString(2).equals(examtype))
            {
                subjectvalue.setText(subname);
                subjectvalue.setTextSize(20);
                examvalue.setText(res.getString(2));
                examvalue.setTextSize(20);
                marksvalue.setText(res.getString(3));
                marksvalue.setTextSize(20);
                max_marksvalue.setText(res.getString(4));
                max_marksvalue.setTextSize(20);
            }
        }

        ll.addView(subject);
        ll.addView(subjectvalue);
        ll.addView(exam);
        ll.addView(examvalue);
        ll.addView(marks);
        ll.addView(marksvalue);
        ll.addView(max_marks);
        ll.addView(max_marksvalue);
        examLL.addView(ll);


    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, ViewSubjectMarks.class);
        intent.putExtra("sub_id",s);
        startActivity(intent);
        super.onBackPressed();
    }
}
