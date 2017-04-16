package com.example.adavi.academiccompanion;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Date;

import static com.example.adavi.academiccompanion.R.id.center_vertical;

public class DisplayEventActivity extends AppCompatActivity {

    final TextView[] myTextViews = new TextView[128];
    DatabaseHelper myDB;
    int button_id;
    ListView listView;
    ArrayAdapter<String> adapter;
    String activity_type;
    AlertDialog dialog;
    String formattedDate;
    String formattedTime;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("Events");
        setSupportActionBar(toolbar);
        myDB = new DatabaseHelper(this);
        activity_type=null;
        listView=new ListView(this);
        Calendar c = Calendar.getInstance();
//        System.out.println("Current time => "+c.getTime());
//        Toast.makeText(this, toString(c.getTime()), Toast.LENGTH_SHORT).show();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");

        formattedDate = df.format(c.getTime());
        formattedTime = tf.format(c.getTime());
        // formattedDate have current date/time
//        Toast.makeText(this, formattedTime, Toast.LENGTH_SHORT).show();

        // Add data to the ListView

        String[] items={"Return Book","Assignment","Homework","Exam","Extra Class","Other Activity"};

        adapter=new ArrayAdapter<String>(this,
                R.layout.list_item, R.id.txtitem,items);

        listView.setAdapter(adapter);

        // Perform action when an item is clicked

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long id) {

                ViewGroup vg=(ViewGroup)view;

                TextView txt=(TextView)vg.findViewById(R.id.txtitem);
                activity_type=txt.getText().toString();
//                Toast.makeText(DisplayEventActivity.this,txt.getText().toString(),Toast.LENGTH_LONG).show();
                dialog.dismiss();
//                dialog.cancel();
                openAddEvent();
            }

        });

        displayEventHelper();


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void displayEventHelper() {
        Cursor res = myDB.getRecentEvents();
        Cursor res1 = myDB.getEventsAsc();
        if (res1.getCount() == 0) {
            eventAlert("No Events", "Go and Add a Event!");
            return;
        }

        LinearLayout eventLL = (LinearLayout) findViewById(R.id.event_display_ll);
        TextView tv = new TextView(this);
        tv.setText("Upcoming Events");
        LinearLayout.LayoutParams tv_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT

        );
        tv_params.setMargins(16,16,16,16);
        tv.setLayoutParams(tv_params);
        tv.setPadding(8,8,8,8);
        tv.setTextSize(15);
        tv.setBackgroundColor(Color.LTGRAY);
        tv.setTextColor(Color.BLACK);
        eventLL.addView(tv);

        while(res1.moveToNext())
        {
            if(res1.getString(2).compareTo(formattedDate)>0)
            {
                displayEvent(res1.getInt(0), res1.getString(1), res1.getString(2),1,res1.getString(8));
            }
            else if(res1.getString(2).compareTo(formattedDate)==0)
            {
                if((res1.getString(3)!=null && res1.getString(3).compareTo(formattedTime)>=0) || res1.getString(3).equals(""))
                {
                    displayEvent(res1.getInt(0), res1.getString(1), res1.getString(2),1,res1.getString(8));
                }
            }

        }


        TextView tv1 = new TextView(this);
        tv1.setText("Completed Events");
        tv_params.setMargins(16,32,16,16);
        tv1.setLayoutParams(tv_params);
        tv1.setPadding(8,8,8,8);
        tv1.setTextSize(15);
        tv1.setBackgroundColor(Color.LTGRAY);
        tv1.setTextColor(Color.BLACK);
        eventLL.addView(tv1);
//        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
//            buffer.append("Subject: "+res.getString(0)+"\n");
//            buffer.append("Teacher: "+res.getString(1)+"\n");
//            buffer.append("Teacher's Email : "+res.getString(2)+"\n");
//            if(res.getString(2).compareTo(formattedDate)>0)
//            {
//                displayEvent(res.getInt(0), res.getString(1), res.getString(2),1);
//            }
//            else if(res.getString(2).compareTo(formattedDate)==0)
//            {
//                if(res.getString(4).compareTo(formattedTime)>0)
//                {
//                    displayEvent(res.getInt(0), res.getString(1), res.getString(2),1);
//                }
//                else
//                {
//                    displayEvent(res.getInt(0), res.getString(1), res.getString(2),0);
//                }
//            }
//            else
//            {
//                displayEvent(res.getInt(0), res.getString(1), res.getString(2),0);
//            }

            if(res.getString(2).compareTo(formattedDate)<0)
            {
                displayEvent(res.getInt(0), res.getString(1), res.getString(2),0,res.getString(8));
            }
            else if(res.getString(2).compareTo(formattedDate)==0)
            {
                if(res.getString(3)!=null && res.getString(3).compareTo(formattedTime)<0 && !res.getString(3).equals(""))
                {
                    displayEvent(res.getInt(0), res.getString(1), res.getString(2),0,res.getString(8));
                }
            }

//            buffer.replace(0,buffer.length(),"");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void displayEvent(int eventid, String ename, String date, int status,String etype) {

        //layout to which children are added
        LinearLayout eventLL = (LinearLayout) findViewById(R.id.event_display_ll);
//
//
//        //child layouts
//        Button rowButton = new Button(this);
//        TextView tv = new TextView(this);
//        LinearLayout ll = new LinearLayout(this);
//        ll.setBackgroundResource(R.drawable.button_sample1);
//
//        //layout params for each view
//
//        LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//        );
//
//        ll_params.setMargins(20, 20, 20, 20);
//
//
//        LinearLayout.LayoutParams rb_params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                1.0f
//        );
//
////        rb_params.setMargins(8, 8, 8, 8);
//
//        LinearLayout.LayoutParams tv_params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                4.0f
//        );
//
////        tv_params.setMargins(8,8,8,8);/
//        tv.setGravity(Gravity.CENTER);
//        tv.setLayoutParams(tv_params);
//        ll.setLayoutParams(ll_params);
//        rowButton.setLayoutParams(rb_params);
//
//
//        rowButton.setTextSize(20);
//        rowButton.setBackgroundColor(Color.parseColor("#CFD8DC"));
//        rowButton.setTextColor(Color.parseColor("#263238"));
//        ll.setBackgroundColor(Color.rgb(224, 242, 241));
////        LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(
////                LinearLayout.LayoutParams.MATCH_PARENT,
////                200
////        );
////
////        ll_params.setMargins(0, 0, 0, 0);
////
////
////        LinearLayout.LayoutParams rb_params = new LinearLayout.LayoutParams(
////                LinearLayout.LayoutParams.MATCH_PARENT,
////                LinearLayout.LayoutParams.MATCH_PARENT,
////                1.0f
////        );
////
//////        rb_params.setMargins(8, 24, 8, 8);
////
////        LinearLayout.LayoutParams tv_params = new LinearLayout.LayoutParams(
////                LinearLayout.LayoutParams.MATCH_PARENT,
////                LinearLayout.LayoutParams.MATCH_PARENT,
////                3.0f
////        );
//
////        tv_params.setMargins(24, 24, 24, 24);
////        rb_params.setMargins(24,24,24,24);
////        tv.setLayoutParams(tv_params);
////        ll.setLayoutParams(ll_params);
////        rowButton.setLayoutParams(rb_params);
////        rowButton.setGravity(center_vertical);
////        rowButton.setPadding(0,32,0,32);
////        rowButton.setTextColor(Color.BLACK);
//        rowButton.setAllCaps(true);
//
////        tv.setPadding(0,32,0,32);
////        tv.setTypeface(null, Typeface.BOLD);
//        ll.setId(eventid);
//        rowButton.setText(ename);
////        rowButton.setTextSize(20);
////        rowButton.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
////        rowButton.setBackgroundColor(Color.rgb(224, 242, 241));
////        rowButton.setBackgroundResource(R.drawable.button_sample1);
//        ll.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v)
//            {
//
//                LinearLayout pressed;
//                pressed=((LinearLayout)v);
//                button_id=pressed.getId();
//                viewEventDetails(v);
//
//
//            }
//        });
//        rowButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v)
//            {
//
//                Button pressed;
//                pressed=((Button) v);
//                button_id=pressed.getId();
//                viewEventDetails(v);
//
//
//            }
//        });
//// Add id to buttons and also on click listner to these buttons
//        tv.setText(date);
//        tv.setTextSize(16);
//
//
////        ll.setBackgroundColor(Color.rgb(224, 242, 241));
//        ll.addView(rowButton);
//        ll.addView(tv);
//        if(status==0)
//        {
//            ll.setBackgroundColor(Color.LTGRAY);
//            rowButton.setBackgroundColor(Color.LTGRAY);
//        }
//        eventLL.addView(ll);

        SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2=new SimpleDateFormat("MMM dd");
        Date dt1= null;
        try {
            dt1 = format1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        Toast.makeText(DisplayEventActivity.this,format2.format(dt1) , Toast.LENGTH_LONG).show();

        LinearLayout ll = new LinearLayout(this);
        LinearLayout ll1 = new LinearLayout(this);
        TextView tv1 = new TextView(this);
        TextView tv2 = new TextView(this);
        TextView tv3 = new TextView(this);

        LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );


        LinearLayout.LayoutParams tv1_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT

        );

        LinearLayout.LayoutParams tv2_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        );
        ll.setLayoutParams(ll_params);
        ll1.setLayoutParams(ll_params);
        tv1.setLayoutParams(tv1_params);
        tv2.setLayoutParams(tv2_params);
        tv3.setLayoutParams(tv2_params);

        ll.setPadding(8,8,8,0);
        ll_params.setMargins(16,16,16,0);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll1.setOrientation(LinearLayout.HORIZONTAL);

//        ll.setBackgroundColor(Color.WHITE);
//        ll.setBackgroundColor(Color.parseColor("#B2DFDB"));
        ll.setBackgroundResource(R.drawable.button_sample2);
//        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
        tv3.setGravity(Gravity.RIGHT);
        tv1.setPadding(32,16,16,16);
        tv2.setPadding(16,16,16,16);
        tv3.setPadding(16,16,16,16);

        tv1.setTextColor(Color.BLACK);
        tv1.setTextSize(20);
        tv1.setAllCaps(true);
        tv2.setTextSize(14);
        tv3.setTextSize(14);
        ll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {

                LinearLayout pressed;
                pressed=((LinearLayout)v);
                button_id=pressed.getId();
                viewEventDetails(v);


            }
        });

        tv1.setText(ename);
        tv2.setText(etype);
        tv3.setText(format2.format(dt1));
        ll.setId(eventid);

        ll1.addView(tv2);
        ll1.addView(tv3);
        ll.addView(tv1);
        ll.addView(ll1);

        if(status==0)
        {
            ll.setBackgroundResource(R.drawable.button_sample3);
        }
//        eventLL.setPadding(8,8,8,8);
        eventLL.addView(ll);

    }

    public void eventAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


    public void openAddEvent(){


        Intent intent = new Intent(this, AddEventActivity.class);
        intent.putExtra("activity_type",activity_type);
        startActivity(intent);
    }

    public void showDialogListView(View view){

        AlertDialog.Builder builder=new
                AlertDialog.Builder(DisplayEventActivity.this);

        builder.setCancelable(true);

//        builder.setPositiveButton("OK",null);

        builder.setView(listView);

        dialog=builder.create();
        dialog.setTitle("Select Your Activity");
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                                       @Override
                                       public void onCancel(DialogInterface dialog) {
                                           Intent intent = new Intent(DisplayEventActivity.this, DisplayEventActivity.class);
                                           startActivity(intent);
                                       }
                                   }


        );
        dialog.show();



    }

    public void viewEventDetails(View v)
    {
        Intent intent = new Intent(this, DisplayEventDetailsActivity.class);
        String s = Integer.toString(button_id);
        intent.putExtra("button_event_id",s);
        startActivity(intent);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}