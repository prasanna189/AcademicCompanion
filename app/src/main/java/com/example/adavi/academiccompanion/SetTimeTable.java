package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SetTimeTable extends AppCompatActivity {

    Spinner editSubject;
    String subject_name;
    Button editStime,editEtime;
    int sem;

    DatabaseHelper myDB;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time_table);
        myDB=new DatabaseHelper(this);

        subject_name="";
        editSubject=(Spinner)findViewById(R.id.time_table_subject_spinner);
        editEtime=(Button)findViewById( R.id.time_table_etime_button);
        editStime=(Button)findViewById(R.id.time_table_stime_button);
//          Subject Spinner
        sem=myDB.getcurrentsem();

        Cursor sub_res=myDB.getCurrentSemSubjects(sem);
        String[] subject_array= new String[sub_res.getCount()+1];
        subject_array[0]="";

        int i=1;
        while(sub_res.moveToNext())
        {
            subject_array[i]=sub_res.getString(1);
            i=i+1;
        }

        adapter=new ArrayAdapter<String>(this,
                R.layout.spinner_layout,R.id.textview, subject_array);

        editSubject.setAdapter(adapter);

        editSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int
                    position, long id) {

                ViewGroup vg=(ViewGroup)view;

                TextView tv=(TextView)vg.findViewById(R.id.textview);
                subject_name=tv.getText().toString();
//                Toast.makeText(AddEventActivity.this, tv.getText().toString(),
//                        Toast.LENGTH_LONG).show();

            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {


                subject_name=null;
            }

        });


        editStime.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
                TimeDialog dialog = TimeDialog.newInstance(view);
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                dialog.show(ft, "TimeDialog");
            }
        });

        editEtime.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
                TimeDialog dialog = TimeDialog.newInstance(view);
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                dialog.show(ft, "TimeDialog");
            }
        });

        displayDetails();
    }


    void displayDetails()
    {
//        int id=Integer.parseInt(getIntent().getStringExtra("Subject_id"));
//        String day=getIntent().getStringExtra("Day");

//        if(stime!=null && etime!=null)
//        {
//            editStime.setText(stime);
//            editEtime.setText(etime);
//        }
    }

    void saveTimeTable()
    {
        int id=Integer.parseInt(getIntent().getStringExtra("Subject_id"));
        String day=getIntent().getStringExtra("Day");
//        String t_id=getIntent().getStringExtra("timetable_id");
        String t_id=null;
        if(t_id!=null)
        {

        }
        else
        {
            boolean isInserted = myDB.insertDataTimeTable(sem,id,day,editStime.getText().toString(),editEtime.getText().toString());
            if(isInserted)
            {
                Toast.makeText(SetTimeTable.this, "TimeTable Updated", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(SetTimeTable.this, "TimeTable Updation Failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    void resetTimeTable()
    {
        Intent intent = new Intent(this, DisplaySubjectDetails.class);
        intent.putExtra("timetable_id",getIntent().getStringExtra("timetable_id"));
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.cancel_edit_timetable)
        {
            String s=getIntent().getStringExtra("button_event_id");

            Intent intent = new Intent(this,DisplayEventActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.set_timetable_cancel_menu, menu);
        return true;
    }
}
