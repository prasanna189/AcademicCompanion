package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DisplaySubjectDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_subject_details);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.subject_details_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.edit_subject:
                EditSubjectDetails();
                return true;
            case R.id.delete_subject:
                DeleteSubject();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    String s;


    public void EditSubjectDetails()
    {
        LinearLayout subjectLL = (LinearLayout) findViewById(R.id.subject_details);
        String s=getIntent().getStringExtra("abc");
        TextView tv = new TextView(this);
        tv.setText(s);
        tv.setTextSize(20);
        subjectLL.addView(tv);

    }
    public void DeleteSubject()
    {

    }
}
