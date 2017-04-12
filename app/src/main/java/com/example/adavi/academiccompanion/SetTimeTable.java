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
                subject_name="";
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
        String t_id=getIntent().getStringExtra("timetable_id");
        if(t_id==null)
        {
            String id=getIntent().getStringExtra("subject_id");
            int subject_id=Integer.parseInt(id);
            Cursor c = myDB.getAllData("subject");
            String sub_name="";
            while (c.moveToNext()) {

                if (c.getInt(0)==subject_id) {
                    sub_name = c.getString(1);
                }
            }
            editSubject.setSelection(adapter.getPosition(sub_name));

        }
        else
        {
            int tid=Integer.parseInt(t_id);
            Cursor res= myDB.getAllData("timetable");
            Toast.makeText(SetTimeTable.this, ""+tid, Toast.LENGTH_LONG).show();
            while(res.moveToNext())
            {
                if(tid==res.getInt(0))
                {
//                    String sub_name=getSubjectName(res.getInt(2));
                    Cursor c = myDB.getAllData("subject");
                    String sub_name="";
                    while (c.moveToNext()) {

                        if (c.getInt(0)==res.getInt(2)) {
                            sub_name = c.getString(1);
                        }
                    }
                    editSubject.setSelection(adapter.getPosition(sub_name));
                    editStime.setText(res.getString(4));
                    editEtime.setText(res.getString(5));
                    Toast.makeText(SetTimeTable.this, ""+tid, Toast.LENGTH_LONG).show();
                }
            }

        }
    }

//    String getSubjectName(int id)
//    {
//        Cursor res = myDB.getAllData("subject");
//        String sub="";
//        while(res.moveToNext())
//        {
//            if(res.getInt(0)==id)
//                sub=res.getString(1);
//        }
//        return sub;
//    }

    void saveTimeTable(View view)
    {
//        String id=getIntent().getStringExtra("subject_id");
        String day=getIntent().getStringExtra("day");
        String t_id=getIntent().getStringExtra("timetable_id");

        int subject_id=-1;
        String sub_name;
        Cursor c = myDB.getAllData("subject");
        while (c.moveToNext()) {
            sub_name = c.getString(1);
            if (sub_name.equals(subject_name)) {
                subject_id = c.getInt(0);
            }
        }
        if(t_id!=null)
        {
            int timetable_id = Integer.parseInt(t_id);
            Cursor res = myDB.getAllData("timetable");
            while(res.moveToNext())
            {
                if(timetable_id==res.getInt(0));
                {
                    boolean isUpdated = myDB.updatetDataTimeTable(res.getInt(0),res.getInt(1),subject_id,res.getString(3),editStime.getText().toString(),editEtime.getText().toString());
                    if(isUpdated)
                    {
                        Toast.makeText(SetTimeTable.this,"TimeTable Updated",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(this,TimeTableActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(SetTimeTable.this, "TimeTable Updation Failed", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
        else
        {

            boolean isInserted = myDB.insertDataTimeTable(sem,subject_id,day,editStime.getText().toString(),editEtime.getText().toString());
            if(isInserted)
            {
                Toast.makeText(SetTimeTable.this, "TimeTable Updated", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, TimeTableActivity.class);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(SetTimeTable.this, "TimeTable Updation Failed", Toast.LENGTH_LONG).show();
            }
        }
    }



    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.cancel_edit_timetable)
        {

            String t_id=getIntent().getStringExtra("timetable_id");
            if(t_id==null)
            {
                Intent intent = new Intent(this,EditTimeTable.class);
                startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(this,TimeTableActivity.class);
                startActivity(intent);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.set_timetable_cancel_menu, menu);
        return true;
    }

    public void onBackPressed()
    {
        String t_id=getIntent().getStringExtra("timetable_id");
        if(t_id==null)
        {
            Intent intent = new Intent(this,EditTimeTable.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this,TimeTableActivity.class);
            startActivity(intent);
        }
    }
}
