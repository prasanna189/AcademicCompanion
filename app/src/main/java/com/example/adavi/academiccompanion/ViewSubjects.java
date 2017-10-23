package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewSubjects extends AppCompatActivity {

    DatabaseHelper myDB;
    int semid;
    String s, sub_name;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_subjects);
        myDB = new DatabaseHelper(this);
        setTitle("Subjects");

        semid = myDB.getcurrentsem();
        displaySubjectsHelper();
    }

    public void displaySubjectsHelper() {
        int flag = 0;
        Cursor res = myDB.getAllData("subject_details");
        if (res.getCount() == 0) {
            subjectAlert("No Subjects", "Please Add Subjects");
            return;
        } else {

            while (res.moveToNext()) {
                if (res.getInt(0) == (semid)) {
                    //Toast.makeText(DisplaySubjectsActivity.this,"faf0", Toast.LENGTH_LONG).show();
                    flag = 1;
                    displaySubjects(myDB.getSubjectName(res.getInt(1)), res.getString(5), res.getInt(1));
                }
            }
            if (flag == 0) {
                subjectAlert("No Subjects", "Go and Add a subject!");
                return;
            }
        }

    }

    public void displaySubjects(String sname, String status, int sub_id) {

        //layout to which children are added
        LinearLayout subjectLL = (LinearLayout) findViewById(R.id.View_Subjects);


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

        // tv_params.setMargins(24, 8, 8, 8);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(tv_params);
        ll.setLayoutParams(ll_params);

        rowButton.setLayoutParams(rb_params);

        rowButton.setId(sub_id);

        rowButton.setText(sname);
        rowButton.setTextSize(20);
        rowButton.setBackgroundColor(Color.parseColor("#CFD8DC"));
        rowButton.setTextColor(Color.parseColor("#263238"));

        rowButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Button pressed;
                pressed = ((Button) v);
                i = pressed.getId();
                // sub_name=pressed.getText().toString();
                viewSubjectMarks(v);


            }
        });

        if (status.equals("Completed")) {
            tv.setText(status);
            tv.setTextSize(12);
            tv.setBackgroundColor(Color.parseColor("#EF9A9A"));
//            tv.setTextAlignment();
        } else {
            tv.setText(status);
            tv.setTextSize(12);
            tv.setBackgroundColor(Color.parseColor("#80CBC4"));
        }


        tv.setText(status);
        tv.setTextSize(12);


        ll.setBackgroundColor(Color.rgb(224, 242, 241));
        ll.addView(rowButton);
        ll.addView(tv);

        subjectLL.addView(ll);
    }

    public void viewSubjectMarks(View v) {
        Intent intent = new Intent(this, ViewSubjectMarks.class);
        s = Integer.toString(i);
        intent.putExtra("sub_id", s);
        startActivity(intent);
    }


    public void subjectAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void onBackPressed() {

        Intent intent = new Intent(this, View_Stats.class);
//        intent.putExtra("sub_id",str);
        startActivity(intent);
        super.onBackPressed();
    }

}
