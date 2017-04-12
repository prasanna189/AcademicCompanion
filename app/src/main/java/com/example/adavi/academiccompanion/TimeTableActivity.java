package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TimeTableActivity extends AppCompatActivity {


    DatabaseHelper myDB = null;
    int sem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        myDB= new DatabaseHelper(this);


        Cursor res = myDB.getAllData("timetable");
        sem = myDB.getcurrentsem();
        while(res.moveToNext())
        {
            if(res.getInt(1)==sem)
            {
                addToTimetable(res.getInt(0),res.getInt(2),res.getString(3),res.getString(5),res.getString(6));
            }
        }


    }

    void addToTimetable(int id, int sub_id, String day, String stime, String etime)
    {
        String sub_name=subjectName(sub_id);
        LinearLayout dayll=null;
        if(day.equals("Monday"))
        {
            dayll=(LinearLayout) findViewById(R.id.monday_ll);
        }
        else if(day.equals("Tuesday"))
        {
            dayll=(LinearLayout) findViewById(R.id.tuesday_ll);
        }
        else if(day.equals("Wednesday"))
        {
            dayll=(LinearLayout) findViewById(R.id.wednesday_ll);
        }
        else if(day.equals("Thursday"))
        {
            dayll=(LinearLayout) findViewById(R.id.thursday_ll);
        }
        else if(day.equals("Friday"))
        {
            dayll=(LinearLayout) findViewById(R.id.friday_ll);
        }
        else if(day.equals("Saturday"))
        {
            dayll=(LinearLayout) findViewById(R.id.saturday_ll);
        }
        else if(day.equals("Sunday"))
        {
            dayll=(LinearLayout) findViewById(R.id.sunday_ll);
        }


        TextView tv_sname = new TextView(this);
        TextView tv_stime = new TextView(this);
        TextView tv_etime = new TextView(this);
        final LinearLayout ll = new LinearLayout(this);


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



        tv_sname.setLayoutParams(tv_params);
        tv_stime.setLayoutParams(tv_params);
        tv_etime.setLayoutParams(tv_params);

        ll.setLayoutParams(ll_params);


        ll.setId(id);
        ll.setClickable(true);

        ll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {

                int id=ll.getId();
                Intent intent = new Intent(TimeTableActivity.this,SetTimeTable.class);
                intent.putExtra("timetable_id",id);
                startActivity(intent);


            }
        });
// Add id to buttons and also on click listner to these buttons
        tv_sname.setText(sub_name);
        tv_stime.setText(stime);
        tv_etime.setText(etime);

        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(tv_sname);
        ll.addView(tv_stime);
        ll.addView(tv_etime);


        ll.setBackgroundColor(Color.rgb(224, 242, 241));

        dayll.addView(ll);



    }

    String subjectName(int sub_id)
    {
        Cursor res = myDB.getCurrentSemSubjects(sem);
        String sub_name="";
        while(res.moveToNext())
        {
            if(res.getInt(0)==sub_id)
            {
                sub_name=res.getString(1);
            }
        }
        return sub_name;
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_time_table, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.edit_time_table:
                editTimeTable();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void  editTimeTable()
    {
        Intent intent = new Intent(this,EditTimeTable.class);
        startActivity(intent);
    }
}