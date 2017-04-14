package com.example.adavi.academiccompanion;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.adavi.academiccompanion.R.id.toolbar;
import static com.example.adavi.academiccompanion.R.id.txtitem;

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
                Toast.makeText(DisplayEventActivity.this,txt.getText().toString(),Toast.LENGTH_LONG).show();
                dialog.dismiss();
//                dialog.cancel();
                openAddEvent();
            }

        });

        displayEventHelper();


    }

    public void displayEventHelper() {
        Cursor res = myDB.getRecentEvents();
        Cursor res1 = myDB.getEventsAsc();
        if (res.getCount() == 0) {
            eventAlert("No Events", "Go and Add a Event!");
            return;
        }

        while(res1.moveToNext())
        {
            if(res1.getString(2).compareTo(formattedDate)>0)
            {
                displayEvent(res1.getInt(0), res1.getString(1), res1.getString(2),1);
            }
            else if(res1.getString(2).compareTo(formattedDate)==0)
            {
                if(res1.getString(3)!=null && res1.getString(3).compareTo(formattedTime)>=0)
                {
                    displayEvent(res1.getInt(0), res1.getString(1), res1.getString(2),1);
                }
            }

        }

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
                displayEvent(res.getInt(0), res.getString(1), res.getString(2),0);
            }
            else if(res.getString(2).compareTo(formattedDate)==0)
            {
                if(res.getString(3)!=null && res.getString(3).compareTo(formattedTime)<0)
                {
                    displayEvent(res.getInt(0), res.getString(1), res.getString(2),0);
                }
            }

//            buffer.replace(0,buffer.length(),"");
        }
    }

    public void displayEvent(int eventid, String ename, String date,int status) {

        //layout to which children are added
        LinearLayout eventLL = (LinearLayout) findViewById(R.id.event_display_ll);


        //child layouts
        Button rowButton = new Button(new ContextThemeWrapper(this, R.style.MyButton), null, 0);
        TextView tv = new TextView(this);
        LinearLayout ll = new LinearLayout(this,null,R.style.MyButton);


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


        ll.setId(eventid);
        rowButton.setText(ename);
        rowButton.setTextSize(20);
//        rowButton.setBackgroundColor(Color.rgb(224, 242, 241));

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
// Add id to buttons and also on click listner to these buttons
        tv.setText(date);
        tv.setTextSize(12);


        ll.setBackgroundColor(Color.rgb(224, 242, 241));
        ll.addView(rowButton);
        ll.addView(tv);
        if(status==0)
        {
            ll.setBackgroundColor(Color.LTGRAY);
            rowButton.setBackgroundColor(Color.LTGRAY);
        }
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
