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

        sem=myDB.getcurrentsem();

        Cursor res= myDB.getCurrentSemSubjects(sem);
        while(res.moveToNext())
        {
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


            mondayButton.setLayoutParams(button_params);
            tuesdayButton.setLayoutParams(button_params);
            wednesdayButton.setLayoutParams(button_params);
            thursdayButton.setLayoutParams(button_params);
            fridayButton.setLayoutParams(button_params);
            saturdayButton.setLayoutParams(button_params);
            sundayButton.setLayoutParams(button_params);
            sub_tv.setLayoutParams(button_params);


            sub_tv.setText(res.getString(1));
            subjectll.addView(sub_tv);
            mondayButton.setId(res.getInt(0));
            tuesdayButton.setId(res.getInt(0));
            wednesdayButton.setId(res.getInt(0));
            thursdayButton.setId(res.getInt(0));
            fridayButton.setId(res.getInt(0));
            saturdayButton.setId(res.getInt(0));
            sundayButton.setId(res.getInt(0));
            Toast.makeText(EditTimeTable.this, ""+res.getInt(0)+"" , Toast.LENGTH_LONG).show();


            mondayll.addView(mondayButton);
            tuesdayll.addView(tuesdayButton);
            wednesdayll.addView(wednesdayButton);
            thursdayll.addView(thursdayButton);
            fridayll.addView(fridayButton);
            saturdayll.addView(saturdayButton);
            sundayll.addView(sundayButton);

            mondayButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                    Button pressed;
                    pressed=((Button)v);
                    int id_mon=pressed.getId();
                    Toast.makeText(EditTimeTable.this, ""+id_mon+"" , Toast.LENGTH_LONG).show();

                    redirectSetTimeTable(id_mon,"Monday");
                }
            });

            tuesdayButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Button pressed;
                    pressed=((Button)v);
                    int id_tue=pressed.getId();
                    redirectSetTimeTable(id_tue,"Tuesday");
                }
            });

            wednesdayButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Button pressed;
                    pressed=((Button)v);
                    int id_wed=pressed.getId();
                    redirectSetTimeTable(id_wed,"Wednesday");
                }
            });

            thursdayButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Button pressed;
                    pressed=((Button)v);
                    int id_thu=pressed.getId();
                    redirectSetTimeTable(id_thu,"Thursday");
                }
            });

            fridayButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Button pressed;
                    pressed=((Button)v);
                    int id_fri=pressed.getId();
                    redirectSetTimeTable(id_fri,"Friday");
                }
            });

            saturdayButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Button pressed;
                    pressed=((Button)v);
                    int id_sat=pressed.getId();
                    redirectSetTimeTable(id_sat,"Saturday");

                }
            });

            sundayButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Button pressed;
                    pressed=((Button)v);
                    int id_sun=pressed.getId();
                    redirectSetTimeTable(id_sun,"Sunday");

                }
            });

        }




    }

    void redirectSetTimeTable(int sub_id,String day)
    {
        Intent intent = new Intent(this,SetTimeTable.class);
        intent.putExtra("subject_id",""+sub_id+"");
        intent.putExtra("day",day);
        startActivity(intent);
    }

    public void onBackPressed()
    {
        Intent intent = new Intent(this,TimeTableActivity.class);
        startActivity(intent);

    }


}
