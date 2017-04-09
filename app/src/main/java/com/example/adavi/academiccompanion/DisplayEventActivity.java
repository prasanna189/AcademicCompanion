package com.example.adavi.academiccompanion;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDB = new DatabaseHelper(this);
        activity_type=null;
        listView=new ListView(this);

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
        if (res.getCount() == 0) {
            eventAlert("No Events", "Go and Add a Event!");
            return;
        }

//        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
//            buffer.append("Subject: "+res.getString(0)+"\n");
//            buffer.append("Teacher: "+res.getString(1)+"\n");
//            buffer.append("Teacher's Email : "+res.getString(2)+"\n");
            displayEvent(res.getInt(0), res.getString(1), res.getString(2));
//            buffer.replace(0,buffer.length(),"");
        }
    }

    public void displayEvent(int eventid, String ename, String date) {

        //layout to which children are added
        LinearLayout eventLL = (LinearLayout) findViewById(R.id.event_display_ll);


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
            public void onClick(View v)
            {

                Button pressed;
                pressed=((Button)v);
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
