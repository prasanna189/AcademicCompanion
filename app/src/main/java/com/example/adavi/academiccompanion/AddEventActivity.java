package com.example.adavi.academiccompanion;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddEventActivity extends AppCompatActivity {
    DatabaseHelper myDB = null;
    TextView eventType;
    EditText eventName, eventDate, eventStime, eventEtime, eventSubject, eventDescription, eventRemainder;
    Button saveEvent;

//    s=getIntent().getStringExtra("button_event_id");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        myDB= new DatabaseHelper(this);

        eventType=(TextView) findViewById(R.id.event_type_text_view);
        eventName= (EditText)findViewById(R.id.event_name_edit_text);
        eventDate=(EditText)findViewById(R.id.event_date_edit_text);
        eventStime=(EditText)findViewById(R.id.event_stime_edit_text);
        eventEtime=(EditText)findViewById(R.id.event_etime_edit_text);
        eventSubject=(EditText)findViewById(R.id.event_subject_edit_text);
        eventDescription=(EditText)findViewById(R.id.event_description_edit_text);
        eventRemainder=(EditText)findViewById(R.id.event_remainder_edit_text);
        saveEvent=(Button)findViewById(R.id.save_event_button);

        eventDate.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View view, boolean hasfocus){
                if(hasfocus){
                    DateDialog dialog=new DateDialog(view);
                    FragmentTransaction ft =getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");

                }
            }

        });


        displayEventDetails();
    }


    public void displayEventDetails()
    {

          String s=getIntent().getStringExtra("button_event_id");
        if(s!=null)
        {
            Cursor res=myDB.getAllData("event");
            while(res.moveToNext())
            {
                if(s.equals(res.getString(0)))
                {
                    eventName.setText(res.getString(1));
                    eventDate.setText(res.getString(2));
                    eventStime.setText(res.getString(3));
                    eventEtime.setText(res.getString(4));
                    eventSubject.setText(res.getString(5));
                    eventDescription.setText(res.getString(6));
                    eventRemainder.setText(res.getString(7));
                    saveEvent.setText("Update");
                }
            }
        }


    }


    void saveEvent(View view)
    {
        String s=getIntent().getStringExtra("button_event_id");
        int subject_id=0;
        String sub_name;
        Cursor c = myDB.getAllData("subject");
        while(c.moveToNext())
        {
            sub_name=c.getString(1);
            if(sub_name.equals(eventSubject.getText().toString()))
            {
                subject_id = c.getInt(0);
            }
        }
        if(s==null)
        {

            if(subject_id==0)
            {
                Toast.makeText(AddEventActivity.this, "Invalid Subject Name", Toast.LENGTH_LONG).show();
            }
            else
            {
                boolean isInserted ;
                isInserted = myDB.insertDataEvent(eventName.getText().toString(),eventDate.getText().toString(),eventStime.getText().toString(),eventEtime.getText().toString(),
                        subject_id,eventDescription.getText().toString(),eventRemainder.getText().toString());

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
            if(subject_id==0)
            {
                Toast.makeText(AddEventActivity.this, "Invalid Subject Name", Toast.LENGTH_LONG).show();
            }
            else
            {
                isUpdated = myDB.updateDataEvent(Integer.parseInt(s),eventName.getText().toString(),eventDate.getText().toString(),eventStime.getText().toString(),eventEtime.getText().toString(),
                        subject_id,eventDescription.getText().toString(),eventRemainder.getText().toString());

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
