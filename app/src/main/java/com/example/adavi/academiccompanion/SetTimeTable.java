package com.example.adavi.academiccompanion;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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
            setTitle("Set Timings");
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
            setTitle("Edit Timings");
            final int tid=Integer.parseInt(t_id);
            Cursor res= myDB.getAllData("timetable");
//            Toast.makeText(SetTimeTable.this, ""+tid, Toast.LENGTH_LONG).show();
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
//                    Toast.makeText(SetTimeTable.this, ""+tid, Toast.LENGTH_LONG).show();
                }
            }
            LinearLayout ll = (LinearLayout) findViewById(R.id.buttons_ll);
            Button delete_button = new Button(this);

            LinearLayout.LayoutParams Button_params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
            );


            delete_button.setLayoutParams(Button_params);
            delete_button.setText("DELETE");
            ll.addView(delete_button);

            delete_button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {


                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            SetTimeTable.this);
                    alert.setTitle("Confirmation!!");
                    alert.setMessage("Are you sure to Delete Time slot?");
                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do your work here
                            dialog.dismiss();

                            boolean isDeleted = myDB.deleteDataTimetable(tid);
                            if(isDeleted)
                            {
                                Toast.makeText(SetTimeTable.this,"Delete Successful",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SetTimeTable.this,TimeTableActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(SetTimeTable.this,"Delete Unsuccessful",Toast.LENGTH_LONG).show();
                            }


                        }
                    });
                    alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });

                    alert.show();

                }
            });
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
        String stime = editStime.getText().toString();
        String etime = editEtime.getText().toString();

        if(stime.equals(""))
        {
            Toast.makeText(SetTimeTable.this, "Enter Start Time", Toast.LENGTH_LONG).show();
        }
        else if(stime.compareTo(etime)>0 && !etime.equals(""))
        {
            Toast.makeText(SetTimeTable.this, "Start Time should be less than End Time", Toast.LENGTH_LONG).show();
        }
        else if(!checkTimings(stime,etime,day))
        {
            Toast.makeText(SetTimeTable.this, "Timings already exists", Toast.LENGTH_LONG).show();
        }
        else
        {

            if(t_id!=null)
            {
                int timetable_id = Integer.parseInt(t_id);
                Cursor res = myDB.getAllData("timetable");
                while(res.moveToNext())
                {
                    if(timetable_id==res.getInt(0))
                    {
                        Toast.makeText(SetTimeTable.this, res.getInt(0)+"", Toast.LENGTH_LONG).show();
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

    }

    boolean checkTimings(String stime,String etime,String day)
    {
        boolean flag=true;
        Cursor c_tt=myDB.getAllData("timetable");
        while(c_tt.moveToNext())
        {
            if(c_tt.getInt(1)==sem && c_tt.getString(3).equals(day))
            {
                if(c_tt.getString(5)==null)
                {
                    if(c_tt.getString(4).equals(stime))
                    {
                        flag =false;
                    }
                    if(stime.compareTo(c_tt.getString(4))<0 && etime.compareTo(c_tt.getString(4))>0 && !etime.equals(""))
                    {
                        flag=false;
                    }
                }
                else
                {
                    if(c_tt.getString(4).compareTo(stime)<0 && c_tt.getString(5).compareTo(stime)>0)
                    {
                        flag=false;
                    }
                    if(c_tt.getString(4).compareTo(stime)<0 && c_tt.getString(5).compareTo(stime)>0 && !etime.equals(""))
                    {
                        flag=false;
                    }
                    if(stime.compareTo(c_tt.getString(4))<0 && etime.compareTo(c_tt.getString(4))>0 && !etime.equals(""))
                    {
                        flag=false;
                    }
                    if(stime.compareTo(c_tt.getString(5))<0 && etime.compareTo(c_tt.getString(5))>0 && !etime.equals(""))
                    {
                        flag=false;
                    }
                }
            }
        }
        return flag;
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
