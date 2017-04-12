package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EditTimeTable extends AppCompatActivity {

    LinearLayout subjectll,mondayll,tuesdayll,wednesdayll,thursdayll,fridayll,saturdayll,sundayll;
    DatabaseHelper myDB;
    int sem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_time_table);

        myDB= new DatabaseHelper(this);
        TextView sub_tv = new TextView(this);
//
        Button sundayButton = new Button(this);
        sundayButton.setPadding(0,8,0,0);
        sundayButton.setBackgroundResource(R.drawable.ic_action_name);

        Button mondayButton = new Button(this);
        mondayButton.setPadding(0,8,0,0);
        mondayButton.setBackgroundResource(R.drawable.ic_action_name);

        Button tuesdayButton = new Button(this);
        tuesdayButton.setPadding(0,8,0,0);
        tuesdayButton.setBackgroundResource(R.drawable.ic_action_name);

        Button wednesdayButton = new Button(this);
        wednesdayButton.setPadding(0,8,0,0);
        wednesdayButton.setBackgroundResource(R.drawable.ic_action_name);

        Button thursdayButton = new Button(this);
        thursdayButton.setPadding(0,8,0,0);
        thursdayButton.setBackgroundResource(R.drawable.ic_action_name);

        Button fridayButton = new Button(this);
        fridayButton.setPadding(0,8,0,0);
        fridayButton.setBackgroundResource(R.drawable.ic_action_name);

        Button saturdayButton = new Button(this);
        saturdayButton.setPadding(0,8,0,0);
        saturdayButton.setBackgroundResource(R.drawable.ic_action_name);

        subjectll=(LinearLayout)findViewById(R.id.edit_subs_layout);
        mondayll = (LinearLayout)findViewById(R.id.edit_monday_layout);
        tuesdayll=(LinearLayout)findViewById(R.id.edit_tuesday_layout);
        wednesdayll=(LinearLayout)findViewById(R.id.edit_wednesday_layout);
        thursdayll=(LinearLayout)findViewById(R.id.edit_thursday_layout);
        fridayll=(LinearLayout)findViewById(R.id.edit_friday_layout);
        saturdayll=(LinearLayout)findViewById(R.id.edit_saturday_layout);
        sundayll=(LinearLayout)findViewById(R.id.edit_sunday_layout);

        LinearLayout.LayoutParams button_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        sem=myDB.getcurrentsem();

        Cursor res= myDB.getCurrentSemSubjects(sem);

        mondayButton.setLayoutParams(button_params);
        tuesdayButton.setLayoutParams(button_params);
        wednesdayButton.setLayoutParams(button_params);
        thursdayButton.setLayoutParams(button_params);
        fridayButton.setLayoutParams(button_params);
        saturdayButton.setLayoutParams(button_params);
        sundayButton.setLayoutParams(button_params);
        sub_tv.setLayoutParams(button_params);

        while(res.moveToNext())
        {
            sub_tv.setText(res.getString(1));
            subjectll.addView(sub_tv);
            mondayButton.setId(res.getInt(0));
            tuesdayButton.setId(res.getInt(0));
            wednesdayButton.setId(res.getInt(0));
            thursdayButton.setId(res.getInt(0));
            fridayButton.setId(res.getInt(0));
            saturdayButton.setId(res.getInt(0));
            sundayButton.setId(res.getInt(0));

            mondayll.addView(mondayButton);
            tuesdayll.addView(tuesdayButton);
            wednesdayll.addView(wednesdayButton);
            thursdayll.addView(thursdayButton);
            fridayll.addView(fridayButton);
            saturdayll.addView(saturdayButton);
            sundayll.addView(sundayButton);

        }
        final int id_mon=mondayButton.getId();
        final int id_tue=tuesdayButton.getId();
        final int id_wed=wednesdayButton.getId();
        final int id_thu=thursdayButton.getId();
        final int id_fri=fridayButton.getId();
        final int id_sat=saturdayButton.getId();
        final int id_sun=sundayButton.getId();

        mondayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
//
//                String stime;
//                String etime;
//                TimeDialog dialog = TimeDialog.newInstance(v);
//                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.setBreadCrumbTitle("Start Time");
//                dialog.show(ft, "TimeDialog");
//                stime=mondayButton.getText().toString();
//                mondayButton.setBackgroundResource(R.drawable.ic_action_name);
//
//                ft.setBreadCrumbTitle("End Time");
//                dialog.show(ft, "TimeDialog");
//                etime=mondayButton.getText().toString();
//                mondayButton.setBackgroundResource(R.drawable.ic_action_name);

//                Intent intent = new Intent(EditTimeTable.this,SetTimeTable.class);
////                intent.putExtra("Subject_id",sub_id);
////                intent.putExtra("Day",day);
//                startActivity(intent);
                redirectSetTimeTable(id_mon,"Monday");

//                myDB.insertDataTimeTable(sem,id,"monday",stime,etime);
                ///write a code for start time and then end time  dialog to set time for that subject on a day
            }
        });

        tuesdayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

//                String stime;
//                String etime;
//                TimeDialog dialog = TimeDialog.newInstance(v);
//                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.setBreadCrumbTitle("Start Time");
//                dialog.show(ft, "TimeDialog");
//                stime=tuesdayButton.getText().toString();
//                tuesdayButton.setBackgroundResource(R.drawable.ic_action_name);
//
//                ft.setBreadCrumbTitle("End Time");
//                dialog.show(ft, "TimeDialog");
//                etime=tuesdayButton.getText().toString();
//                tuesdayButton.setBackgroundResource(R.drawable.ic_action_name);

                redirectSetTimeTable(id_tue,"Tuesday");
//                myDB.insertDataTimeTable(sem,id,"tuesday",stime,etime);
                ///write a code for start time and then end time  dialog to set time for that subject on a day
            }
        });

        wednesdayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

//                String stime;
//                String etime;
//                TimeDialog dialog = TimeDialog.newInstance(v);
//                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.setBreadCrumbTitle("Start Time");
//                dialog.show(ft, "TimeDialog");
//                stime=wednesdayButton.getText().toString();
//                wednesdayButton.setBackgroundResource(R.drawable.ic_action_name);
//
//                ft.setBreadCrumbTitle("End Time");
//                dialog.show(ft, "TimeDialog");
//                etime=wednesdayButton.getText().toString();
//                wednesdayButton.setBackgroundResource(R.drawable.ic_action_name);

                redirectSetTimeTable(id_wed,"Wednesday");
//                myDB.insertDataTimeTable(sem,id,"monday",stime,etime);
                ///write a code for start time and then end time  dialog to set time for that subject on a day
            }
        });

        thursdayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
//                String stime;
//                String etime;
//                TimeDialog dialog = TimeDialog.newInstance(v);
//                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.setBreadCrumbTitle("Start Time");
//                dialog.show(ft, "TimeDialog");
//                stime=thursdayButton.getText().toString();
//                thursdayButton.setBackgroundResource(R.drawable.ic_action_name);
//
//                ft.setBreadCrumbTitle("End Time");
//                dialog.show(ft, "TimeDialog");
//                etime=thursdayButton.getText().toString();
//                thursdayButton.setBackgroundResource(R.drawable.ic_action_name);

                redirectSetTimeTable(id_thu,"Thursday");
//                myDB.insertDataTimeTable(sem,id,"monday",stime,etime);
                ///write a code for start time and then end time  dialog to set time for that subject on a day
            }
        });

        fridayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
//                String stime;
//                String etime;
//                TimeDialog dialog = TimeDialog.newInstance(v);
//                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.setBreadCrumbTitle("Start Time");
//                dialog.show(ft, "TimeDialog");
//                stime=fridayButton.getText().toString();
//                fridayButton.setBackgroundResource(R.drawable.ic_action_name);
//
//                ft.setBreadCrumbTitle("End Time");
//                dialog.show(ft, "TimeDialog");
//                etime=fridayButton.getText().toString();
//                fridayButton.setBackgroundResource(R.drawable.ic_action_name);

                redirectSetTimeTable(id_fri,"Friday");

//                myDB.insertDataTimeTable(sem,id,"monday",stime,etime);
                ///write a code for start time and then end time  dialog to set time for that subject on a day
            }
        });

        saturdayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
//                String stime;
//                String etime;
//                TimeDialog dialog = TimeDialog.newInstance(v);
//                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.setBreadCrumbTitle("Start Time");
//                dialog.show(ft, "TimeDialog");
//                stime=saturdayButton.getText().toString();
//                saturdayButton.setBackgroundResource(R.drawable.ic_action_name);
//
//                ft.setBreadCrumbTitle("End Time");
//                dialog.show(ft, "TimeDialog");
//                etime=saturdayButton.getText().toString();
//                saturdayButton.setBackgroundResource(R.drawable.ic_action_name);
                redirectSetTimeTable(id_sat,"Saturday");

//                myDB.insertDataTimeTable(sem,id,"monday",stime,etime);
                ///write a code for start time and then end time  dialog to set time for that subject on a day
            }
        });

        sundayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
//                String stime;
//                String etime;
//                TimeDialog dialog = TimeDialog.newInstance(v);
//                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.setBreadCrumbTitle("Start Time");
//                dialog.show(ft, "TimeDialog");
//                stime=sundayButton.getText().toString();
//                sundayButton.setBackgroundResource(R.drawable.ic_action_name);
//
//                ft.setBreadCrumbTitle("End Time");
//                dialog.show(ft, "TimeDialog");
//                etime=sundayButton.getText().toString();
//                sundayButton.setBackgroundResource(R.drawable.ic_action_name);

                redirectSetTimeTable(id_sun,"Sunday");

//                myDB.insertDataTimeTable(sem,id,"monday",stime,etime);
                ///write a code for start time and then end time  dialog to set time for that subject on a day
            }
        });


    }

    void redirectSetTimeTable(int sub_id,String day)
    {
        Intent intent = new Intent(this,SetTimeTable.class);
        intent.putExtra("Subject_id",sub_id);
        intent.putExtra("Day",day);
        startActivity(intent);
    }
}
