package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SubjectMarks extends AppCompatActivity {
    DatabaseHelper myDB;
    String str;
    int totalmarks=0;
    int totmaxmarks=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_marks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDB= new DatabaseHelper(this);
         str=getIntent().getStringExtra("sub_id");

        setTitle("Subject Marks");


        int flag=0;
        Cursor res = myDB.getAllData("marks");

        while (res.moveToNext()) {

            if(res.getString(1).equals(str))
            {
                displayMarks(res.getString(2), res.getInt(3), res.getInt(4),res.getInt(1));
                flag=1;
                totalmarks=totalmarks+res.getInt(3);
                totmaxmarks=totmaxmarks+res.getInt(4);
            }
        }
        if(flag==0)
        {
            marksAlert("No Subject Marks", "Go and Add Marks!");
            return;
        }
        else
        {
            displaytotal();
        }

   }

    String s,type;

    int i;
    public void displayMarks(String examtype,int marksob,int totalmark,int sub_id) {

        //layout to which children are added
        LinearLayout subjectLL = (LinearLayout) findViewById(R.id.subjectmarks_linearlayout);


        //child layouts
        Button rowButton = new Button(this);
        TextView tv = new TextView(this);
        LinearLayout ll = new LinearLayout(this);


        //layout params for each view

        LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        ll_params.setMargins(20, 20, 20, 20);


        LinearLayout.LayoutParams rb_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        );

//        rb_params.setMargins(8, 8, 8, 8);

        LinearLayout.LayoutParams tv_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                4.0f
        );

        //tv_params.setMargins(24, 8, 8, 8);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(tv_params);

        ll.setLayoutParams(ll_params);

        rowButton.setLayoutParams(rb_params);

        rowButton.setId(sub_id);

        rowButton.setText(examtype);
        rowButton.setTextSize(20);
        rowButton.setBackgroundColor(Color.parseColor("#CFD8DC"));
        rowButton.setTextColor(Color.parseColor("#263238"));

        rowButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {

                Button pressed;
                pressed=((Button)v);
                i=pressed.getId();
                type=pressed.getText().toString();
                viewMarksDetails( v);


            }
        });


        tv.setText(Integer.toString(marksob)+"/"+Integer.toString(totalmark));
        tv.setTextSize(20);
        tv.setBackgroundColor(Color.parseColor("#80CBC4"));

        ll.setBackgroundColor(Color.rgb(224, 242, 241));
        ll.addView(rowButton);
        ll.addView(tv);



        subjectLL.addView(ll);
    }
    public void displaytotal()
    {
        LinearLayout subjectLLtotal = (LinearLayout) findViewById(R.id.subjectmarks_linearlayout);
        LinearLayout abc =new LinearLayout(this);

        LinearLayout.LayoutParams tv_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                4.0f
        );

        LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        ll_params.setMargins(20, 20, 20, 20);


        abc.setPadding(0,20,0,20);

        abc.setLayoutParams(ll_params);

        abc.setOrientation(LinearLayout.HORIZONTAL);

        TextView total = new TextView(this);
        TextView totalvalue=new TextView(this);

        total.setText("Total Marks                ");
        total.setPadding(160,0,0,0);
        total.setTextSize(20);
        total.setGravity(Gravity.CENTER);
        total.setTypeface(null,Typeface.BOLD);

        totalvalue.setText(Integer.toString(totalmarks)+"/"+Integer.toString(totmaxmarks));
        totalvalue.setGravity(Gravity.CENTER);
        totalvalue.setTextSize(20);
        totalvalue.setTypeface(null, Typeface.BOLD);

        abc.addView(total);
        abc.addView(totalvalue);
        abc.setGravity(Gravity.CENTER);

        abc.setBackgroundColor(Color.parseColor("#f28f2b"));

        subjectLLtotal.addView(abc);
    }

    public void viewMarksDetails(View v)
    {
        Intent intent = new Intent(this, DisplayExamDetails.class);
        s=Integer.toString(i);
        intent.putExtra("subid",s);
        intent.putExtra("examtype",type);
        startActivity(intent);
    }

    public void marksAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void addSubjectMarks(View view){
        int flag=0;
        Cursor res=myDB.getAllData("subject_details");
        while(res.moveToNext())
        {
            if(res.getString(1).equals(str) && (res.getString(7).equals("0")))
            {
                Intent intent = new Intent(this, AddNewSubjectMarks.class);
                intent.putExtra("subject_id",str);
                startActivity(intent);
                flag=1;
            }

        }
        if(flag==0)
        {
            Toast.makeText(SubjectMarks.this, "Grade is Finalised, Cannot Add New Exam", Toast.LENGTH_SHORT).show();
        }

    }
    public void onBackPressed() {

        Intent intent = new Intent(this, DisplaySubjectDetails.class);
        intent.putExtra("sub_id",str);
        startActivity(intent);
        super.onBackPressed();
    }

}
