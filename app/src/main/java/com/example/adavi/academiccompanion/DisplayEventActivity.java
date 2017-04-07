package com.example.adavi.academiccompanion;

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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static com.example.adavi.academiccompanion.R.id.toolbar;

public class DisplayEventActivity extends AppCompatActivity {

    final TextView[] myTextViews = new TextView[128];
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDB = new DatabaseHelper(this);
        displayEventHelper();


    }

    public void displayEventHelper() {
        Cursor res = myDB.getAllData("event");
//        Cursor res = myDB.getRecentEvents();
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


    public void openAddEvent(View view){
        Intent intent = new Intent(this, AddEventActivity.class);
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

}
