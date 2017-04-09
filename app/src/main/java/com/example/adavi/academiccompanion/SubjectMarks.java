package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SubjectMarks extends AppCompatActivity {
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_marks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDB= new DatabaseHelper(this);



        Cursor res = myDB.getAllData("marks");
        if (res.getCount() == 0) {
            marksAlert("No Subject marks added", "Go and Add subject marks!");
            return;
        }


        while (res.moveToNext()) {
            //   buffer.append("Teacher's Email : "+res.getString(2)+"\n");
            displayMarks(res.getString(2), res.getInt(3),res.getInt(1));
//            buffer.replace(0,buffer.length(),"");
        }
    }

    String s,type;

    int i;
    public void displayMarks(String examtype,int marksob,int sub_id) {

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


        tv.setText(marksob);
        tv.setTextSize(12);


        ll.setBackgroundColor(Color.rgb(224, 242, 241));
        ll.addView(rowButton);
        ll.addView(tv);

        subjectLL.addView(ll);
    }
    public void viewMarksDetails(View v)
    {
        Intent intent = new Intent(this, DisplayExamDetails.class);
        s=Integer.toString(i);
        intent.putExtra("sub_id",s);
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
        Intent intent = new Intent(this, AddNewSubjectMarks.class);
        startActivity(intent);
    }

}
