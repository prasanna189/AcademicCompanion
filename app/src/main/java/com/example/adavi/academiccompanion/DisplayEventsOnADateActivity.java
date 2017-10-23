package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class DisplayEventsOnADateActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    String formattedDate;
    String formattedTime;
    int button_id;
    AlertDialog dialog;
    ListView listView;
    ArrayAdapter<String> adapter;
    String activity_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_events_on_adate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDB = new DatabaseHelper(this);

        LinearLayout eventLL = (LinearLayout) findViewById(R.id.event_display_on_a_date_ll);
        TextView tv = new TextView(this);
        tv.setText("Events on : " + getIntent().getStringExtra("date"));
        tv.setTextSize(16);
        tv.setGravity(Gravity.CENTER);
        eventLL.addView(tv);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayEventsOnADateActivity.this, AddEventFromScheduleActivity.class);
                intent.putExtra("activity_type", activity_type);
                intent.putExtra("date", getIntent().getStringExtra("date"));
                startActivity(intent);

            }
        });


//        String[] items={"Return Book","Assignment","Homework","Exam","Extra Class","Other Activity"};
//
//        adapter=new ArrayAdapter<String>(this,
//                R.layout.list_item_schedule, R.id.schedule_list_item,items);
//
//        listView.setAdapter(adapter);
//
//        // Perform action when an item is clicked
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//
//            public void onItemClick(AdapterView<?> parent, View view, int
//                    position, long id) {
//
//                ViewGroup vg=(ViewGroup)view;
//
//                TextView txt=(TextView)vg.findViewById(R.id.schedule_list_item);
//                activity_type=txt.getText().toString();
//                Toast.makeText(DisplayEventsOnADateActivity.this,txt.getText().toString(),Toast.LENGTH_LONG).show();
//                dialog.dismiss();
////                dialog.cancel();
//                openAddEvent();
//            }
//
//        });
//


        //calling display event helper to show today's events
        displayEventHelper();
    }


    public void openAddEvent() {
        Intent intent = new Intent(DisplayEventsOnADateActivity.this, AddEventActivity.class);
        intent.putExtra("activity_type", activity_type);
        intent.putExtra("date", getIntent().getStringExtra("date"));
        startActivity(intent);
    }

    public void displayEventHelper() {
        Cursor res = myDB.getEvents(getIntent().getStringExtra("date"));

        if (res.getCount() == 0) {
            eventAlert("No Events Today", "Go and Add a Event!");
            return;
        }
        while (res.moveToNext()) {
            displayEvent(res.getInt(0), res.getString(1), res.getString(2), res.getString(3));
        }
    }


    public void displayEvent(int eventid, String ename, String date, String StartTime) {

        //layout to which children are added
        LinearLayout eventLL = (LinearLayout) findViewById(R.id.event_display_on_a_date_ll);

        //child layouts
        Button rowButton = new Button(this);
        TextView tv = new TextView(this);
        LinearLayout ll = new LinearLayout(this);


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

        tv_params.setMargins(24, 8, 8, 8);

        tv.setLayoutParams(tv_params);
        ll.setLayoutParams(ll_params);
        rowButton.setLayoutParams(rb_params);


        rowButton.setId(eventid);
        rowButton.setText(ename);
        rowButton.setTextSize(20);
        rowButton.setBackgroundColor(Color.rgb(224, 242, 241));

        rowButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Button pressed;
                pressed = ((Button) v);
                button_id = pressed.getId();
                viewEventDetails(v);


            }
        });
// Add id to buttons and also on click listner to these buttons
        tv.setText(StartTime);
        tv.setTextSize(12);


        ll.setBackgroundColor(Color.rgb(224, 242, 241));
        ll.addView(rowButton);
        ll.addView(tv);
//        ll.setBackgroundColor(Color.LTGRAY);
        rowButton.setBackgroundColor(Color.LTGRAY);

        eventLL.addView(ll);
    }

    public void eventAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


//    public void showDialogListView(View view){
//
//        AlertDialog.Builder builder=new
//                AlertDialog.Builder(getApplicationContext());
//
//        builder.setCancelable(true);
//
////        builder.setPositiveButton("OK",null);
//
//        builder.setView(listView);
//
//        dialog=builder.create();
//        dialog.setTitle("Select Your Activity");
//        dialog.setCanceledOnTouchOutside(true);
//        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//
//                                       @Override
//                                       public void onCancel(DialogInterface dialog) {
//                                           Intent intent = new Intent(DisplayEventsOnADateActivity.this, DisplayEventsOnADateActivity.class);
//                                           intent.putExtra("date",getIntent().getStringExtra("date"));
//                                           startActivity(intent);
//                                       }
//                                   }
//
//
//        );
//        dialog.show();
//
//    }

    public void viewEventDetails(View v) {
        Intent intent = new Intent(this, DisplayEventOnScheduleActivity.class);
        String s = Integer.toString(button_id);
        intent.putExtra("button_event_id", s);
        intent.putExtra("date", getIntent().getStringExtra("date"));
        startActivity(intent);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent intent = new Intent(this, ScheduleActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}
