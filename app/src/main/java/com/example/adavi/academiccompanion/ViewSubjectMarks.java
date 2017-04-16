package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ViewSubjectMarks extends AppCompatActivity {

    DatabaseHelper myDB;
    String str;
    int totalmarks=0;
    int totmaxmarks=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_subject_marks);

        myDB= new DatabaseHelper(this);

        setTitle("Marks");

        str=getIntent().getStringExtra("sub_id");


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
            marksAlert("No Subject Marks", "No Data to display");
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
        LinearLayout subjectLL = (LinearLayout) findViewById(R.id.viewsubjectmarks_linearlayout);


        //child layouts
        Button rowButton = new Button(this);
        TextView tv = new TextView(this);
        LinearLayout ll = new LinearLayout(this);


        //layout params for each view

        LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        ll_params.setMargins(24, 24, 24, 24);


        LinearLayout.LayoutParams rb_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        );

//        rb_params.setMargins(8, 8, 8, 8);

        LinearLayout.LayoutParams tv_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                4.0f
        );

        tv_params.setMargins(24, 8, 8, 8);

        tv.setLayoutParams(tv_params);
        ll.setLayoutParams(ll_params);
        rowButton.setLayoutParams(rb_params);

        rowButton.setId(sub_id);

        rowButton.setText(examtype);
        rowButton.setTextSize(20);
        rowButton.setBackgroundColor(Color.rgb(224, 242, 241));
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
        tv.setTextSize(12);


        ll.setBackgroundColor(Color.rgb(224, 242, 241));
        ll.addView(rowButton);
        ll.addView(tv);



        subjectLL.addView(ll);
    }
    public void displaytotal()
    {
        LinearLayout subjectLLtotal = (LinearLayout) findViewById(R.id.viewsubjectmarks_linearlayout);
        LinearLayout abc =new LinearLayout(this);
        abc.setPadding(5,50,0,0);
        abc.setOrientation(LinearLayout.HORIZONTAL);
        TextView total = new TextView(this);
        TextView totalvalue=new TextView(this);
        total.setText("Total Marks");
        total.setPadding(50,10,315,10);
        total.setTextSize(20);
        totalvalue.setText(Integer.toString(totalmarks)+"/"+Integer.toString(totmaxmarks));
        abc.addView(total);
        abc.addView(totalvalue);
        abc.setBackgroundColor(Color.parseColor("#f98da5"));
        subjectLLtotal.addView(abc);
    }
    public void viewMarksDetails(View v)
    {
        Intent intent = new Intent(this, ViewExamDetails.class);
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

    public void onBackPressed() {

        Intent intent = new Intent(this, ViewSubjects.class);
//        intent.putExtra("sub_id",str);
        startActivity(intent);
        super.onBackPressed();
    }
}
