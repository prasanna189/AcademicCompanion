package com.example.adavi.academiccompanion;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AddEventActivity extends AppCompatActivity {
    DatabaseHelper myDB = null;
//    TextView eventType;
    Spinner eventType;
    EditText eventName, eventDate, eventStime, eventEtime, eventDescription, eventRemainder;
    Button saveEvent;
    Spinner eventSubject;
    String subject_name;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter_event;

    String event_type;

//    s=getIntent().getStringExtra("button_event_id");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        subject_name=null;
        myDB= new DatabaseHelper(this);

//        eventType=(TextView) findViewById(R.id.event_type_text_view);
        eventType=(Spinner) findViewById(R.id.event_type_spinner);
        eventName= (EditText)findViewById(R.id.event_name_edit_text);
        eventDate=(EditText)findViewById(R.id.event_date_edit_text);
        eventStime=(EditText)findViewById(R.id.event_stime_edit_text);
        eventEtime=(EditText)findViewById(R.id.event_etime_edit_text);
//        eventSubject=(EditText)findViewById(R.id.event_subject_edit_text);
        eventDescription=(EditText)findViewById(R.id.event_description_edit_text);
        eventRemainder=(EditText)findViewById(R.id.event_remainder_edit_text);
        saveEvent=(Button)findViewById(R.id.save_event_button);




        event_type= getIntent().getStringExtra("activity_type");
//        eventType.setText(event_type);

        eventDate.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View view, boolean hasfocus){
                if(hasfocus){
                    DateDialog dialog=new DateDialog(view);
                    FragmentTransaction ft =getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");

                }
            }

        });

        eventStime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {
                    TimeDialog dialog = TimeDialog.newInstance(view);
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    dialog.show(ft, "TimeDialog");

                }
            }

        });

        eventEtime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {
                    TimeDialog dialog = TimeDialog.newInstance(view);
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    dialog.show(ft, "TimeDialog");

                }
            }

        });

        eventRemainder.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {
                    TimeDialog dialog = TimeDialog.newInstance(view);
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    dialog.show(ft, "TimeDialog");

                }
            }

        });

        eventSubject=(Spinner)findViewById(R.id.event_subject_spinner);

        Cursor sub_res=myDB.getAllData("subject");
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

        eventSubject.setAdapter(adapter);

        eventSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
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

//          Event Type Spinner

        String[] event_types={"Return Book","Assignment","Homework","Exam","Extra Class","Other Activity"};
        adapter_event=new ArrayAdapter<String>(this,
                R.layout.spinner_layout,R.id.textview, event_types);

        eventType.setAdapter(adapter_event);

        eventType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int
                    position, long id) {

                ViewGroup vg=(ViewGroup)view;

                TextView tv=(TextView)vg.findViewById(R.id.textview);
                event_type=tv.getText().toString();
//                Toast.makeText(AddEventActivity.this, tv.getText().toString(),
//                        Toast.LENGTH_LONG).show();

            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {


//                event_type=null;
            }

        });
        eventType.setSelection(adapter_event.getPosition(event_type));



        displayEventDetails();
    }


    public void displayEventDetails()
    {

          String s=getIntent().getStringExtra("button_event_id");
        if(s!=null)
        {
            Cursor res=myDB.getAllData("event");
            Cursor res1=myDB.getAllData("subject");
            String sub_name=null;
            while(res.moveToNext())
            {
                if(s.equals(res.getString(0)))
                {
                    eventType.setSelection(adapter_event.getPosition(res.getString(8)));
                    eventName.setText(res.getString(1));
                    eventDate.setText(res.getString(2));
                    eventStime.setText(res.getString(3));
                    eventEtime.setText(res.getString(4));
//                    eventSubject.setText(res.getString(5));
                    eventDescription.setText(res.getString(6));
                    eventRemainder.setText(res.getString(7));
                    saveEvent.setText("Update");
                    while(res1.moveToNext())
                    {
                        if(res1.getInt(0)==res.getInt(5))
                        {
                            sub_name=res1.getString(1);
                        }
                    }


                    eventSubject.setSelection(adapter.getPosition(sub_name));
                }
            }
        }


    }


    void saveEvent(View view)
    {
        String s=getIntent().getStringExtra("button_event_id");
        int subject_id=-1;
        String sub_name;
        Cursor c = myDB.getAllData("subject");
        String activity_sub_name=subject_name;
        while(c.moveToNext())
        {
            sub_name=c.getString(1);
            if(sub_name.equals(subject_name))
            {
                subject_id = c.getInt(0);
            }
        }
        if(s==null)
        {

            if(subject_id==-1 && !activity_sub_name.equals(""))
            {
                Toast.makeText(AddEventActivity.this, "Invalid Subject Name", Toast.LENGTH_LONG).show();
            }
            else
            {
                boolean isInserted ;
                isInserted = myDB.insertDataEvent(eventName.getText().toString(),eventDate.getText().toString(),eventStime.getText().toString(),eventEtime.getText().toString(),
                        subject_id,eventDescription.getText().toString(),eventRemainder.getText().toString(),event_type);

//            myDB.insertDataEvent(eventName.getText().toString(),eventDate.getText().toString(),eventStime.getText().toString(),eventEtime.getText().toString(),
//                    subject_id,eventDescription.getText().toString(),eventRemainder.getText().toString());
                if (isInserted == true) {
                    Toast.makeText(AddEventActivity.this, "Event Saved", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, DisplayEventActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(AddEventActivity.this, "Event not Saved", Toast.LENGTH_LONG).show();
                }

            }
        }
        else
        {
            boolean isUpdated;
            if(subject_id==-1 && !activity_sub_name.equals(""))
            {
                Toast.makeText(AddEventActivity.this, "Invalid Subject Name", Toast.LENGTH_LONG).show();
            }
            else
            {
                isUpdated = myDB.updateDataEvent(Integer.parseInt(s),eventName.getText().toString(),eventDate.getText().toString(),eventStime.getText().toString(),eventEtime.getText().toString(),
                        subject_id,eventDescription.getText().toString(),eventRemainder.getText().toString(),event_type);

                if (isUpdated ) {
                    Toast.makeText(AddEventActivity.this, "Event Updated", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, DisplayEventActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(AddEventActivity.this, "Event not Updated", Toast.LENGTH_LONG).show();
                }
            }


        }


//        if (isInserted == true) {
//            Toast.makeText(AddNewSubjectActivity.this, "Subject Saved", Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(this, DisplaySubjectsActivity.class);
//            startActivity(intent);
//        } else {
//            Toast.makeText(AddNewSubjectActivity.this, "Subject not Saved", Toast.LENGTH_LONG).show();
//

    }


    public void reset(View view) {
        Intent intent = new Intent(this, AddEventActivity.class);
        startActivity(intent);
    }


}
