package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DisplaySubjectsActivity extends AppCompatActivity {

    final TextView[] myTextViews = new TextView[128]; // create an empty array;
    DatabaseHelper myDB;

    int semid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Subjects List");
        setContentView(R.layout.activity_display_subjects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDB = new DatabaseHelper(this);
        semid=myDB.getcurrentsem();
        displaySubjectsHelper();
    }

    public void displaySubjectsHelper() {
        int flag=0;
        Cursor res = myDB.getAllData("subject_details");
        if (res.getCount() == 0) {
            subjectAlert("No Subjects", "Go and Add a subject!");
            return;
        }
        else
        {
            StringBuffer buffer = new StringBuffer();
            while (res.moveToNext()) {
                buffer.append("Subject: "+myDB.getSubjectName(res.getInt(1))+"\n");
                buffer.append("Status: "+res.getString(5)+"\n");
                //   buffer.append("Teacher's Email : "+res.getString(2)+"\n");

                if(res.getInt(0)==(semid))
                {
                    //Toast.makeText(DisplaySubjectsActivity.this,"faf0", Toast.LENGTH_LONG).show();
                    flag=1;
                    displaySubjects(myDB.getSubjectName(res.getInt(1)), res.getString(5),res.getInt(1));
                }
//            else
//            {
//               // Toast.makeText(DisplaySubjectsActivity.this,res.getString(0), Toast.LENGTH_LONG).show();
//               // Toast.makeText(DisplaySubjectsActivity.this,Integer.toString(semid), Toast.LENGTH_LONG).show();
//               // Toast.makeText(DisplaySubjectsActivity.this, "No subjects added", Toast.LENGTH_LONG).show();
//            }

                buffer.replace(0,buffer.length(),"");
            }
            if(flag==0){
                subjectAlert("No Subjects", "Go and Add a subject!");
                return;
            }
        }

    }
String s,sub_name;

    int i;
    public void displaySubjects(String sname, String status,int sub_id) {

        //layout to which children are added
        LinearLayout subjectLL = (LinearLayout) findViewById(R.id.subjects_linearlayout);


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

//        tv_params.setMargins(8,8,8,8);/
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(tv_params);
        ll.setLayoutParams(ll_params);

        rowButton.setLayoutParams(rb_params);

        rowButton.setId(sub_id);

        rowButton.setText(sname);
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
                sub_name=pressed.getText().toString();
                viewSubjectDetails( v);


            }
        });

        if(status.equals("Completed"))
        {
            tv.setText(status);
            tv.setTextSize(12);
            tv.setBackgroundColor(Color.parseColor("#EF9A9A"));
//            tv.setTextAlignment();
        }
        else
        {
            tv.setText(status);
            tv.setTextSize(12);
            tv.setBackgroundColor(Color.parseColor("#80CBC4"));
        }




        ll.setBackgroundColor(Color.rgb(224, 242, 241));
        ll.addView(rowButton);
        ll.addView(tv);

        subjectLL.addView(ll);
    }
    public void viewSubjectDetails(View v)
    {
        Intent intent = new Intent(this, DisplaySubjectDetails.class);
        s=Integer.toString(i);
        intent.putExtra("sub_id",s);
        //intent.putExtra("xyz",sub_name);
        startActivity(intent);
    }

    public void subjectAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


    public void addSubject(View view){
        Intent intent = new Intent(this, AddNewSubjectActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
