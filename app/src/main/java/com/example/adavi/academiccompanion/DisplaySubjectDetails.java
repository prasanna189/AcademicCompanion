package com.example.adavi.academiccompanion;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.id.edit;

public class DisplaySubjectDetails extends AppCompatActivity {

    DatabaseHelper myDB;
    String sub_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_subject_details);
        myDB=new DatabaseHelper(this);
        String s=getIntent().getStringExtra("sub_id");
        sub_name= myDB.getSubjectName(Integer.parseInt(s));
        setTitle(sub_name);

        displaysubjectdetails();
    }

    public void displaysubjectdetails()
    {
        LinearLayout subjectLL = (LinearLayout) findViewById(R.id.subject_details);

        LinearLayout ll = new LinearLayout(this);
        LinearLayout bl = new LinearLayout(this);

        Button marks = new Button(this);
        Button attendance = new Button(this);

        TextView sname = new TextView(this);
        TextView  pname= new TextView(this);
        TextView pemail = new TextView(this);
        TextView min_att = new TextView(this);
        TextView status = new TextView(this);
        TextView credits = new TextView(this);
        TextView grade = new TextView(this);
        TextView lab = new TextView(this);
        TextView desc = new TextView(this);

        TextView snamevalue = new TextView(this);
        TextView  pnamevalue= new TextView(this);
        TextView pemailvalue = new TextView(this);
        TextView min_attvalue = new TextView(this);
        TextView statusvalue = new TextView(this);
        TextView creditsvalue = new TextView(this);
        TextView gradevalue = new TextView(this);
        TextView labvalue = new TextView(this);
        TextView descvalue = new TextView(this);

        LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        ll.setLayoutParams(ll_params);
        ll.setOrientation(LinearLayout.VERTICAL);


        sname.setText("Subject Name");
        sname.setTextSize(12);

        pname.setText("Professor's Name");
        pname.setTextSize(12);

        pemail.setText("Professor's Email");
        pemail.setTextSize(12);

        min_att.setText("Minimum Attendance");
        min_att.setTextSize(12);

        status.setText("Status");
        status.setTextSize(12);

        credits.setText("Credits");
        credits.setTextSize(12);

        grade.setText("Grade");
        grade.setTextSize(12);

        lab.setText("Lab");
        lab.setTextSize(12);

        desc.setText("Description");
        desc.setTextSize(12);

        Cursor res=myDB.getAllData("subject_details");
        String s=getIntent().getStringExtra("sub_id");
        while(res.moveToNext())
        {
            if(res.getString(1).equals(s))
            {
                snamevalue.setText(sub_name);
                snamevalue.setTextSize(12);

                pnamevalue.setText(res.getString(2));
                pnamevalue.setTextSize(12);

                pemailvalue.setText(res.getString(3));
                pemailvalue.setTextSize(12);

                min_attvalue.setText(res.getString(4));
                min_attvalue.setTextSize(12);

                statusvalue.setText(res.getString(5));
                statusvalue.setTextSize(12);

                creditsvalue.setText(res.getString(6));
                creditsvalue.setTextSize(12);

                if(res.getString(7).equals("0"))
                {
                    gradevalue.setText("Yet To Confirm");
                    gradevalue.setTextSize(12);
                }
                else {
                    gradevalue.setText(res.getString(7));
                    gradevalue.setTextSize(12);
                }

                if(res.getInt(8)==1)
                {
                    labvalue.setText("Yes");
                    labvalue.setTextSize(12);
                }
                else
                {
                    labvalue.setText("No");
                    labvalue.setTextSize(12);
                }

                descvalue.setText(res.getString(9));
                descvalue.setTextSize(12);
            }
        }


        ll.addView(sname);
        ll.addView(snamevalue);
        ll.addView(pname);
        ll.addView(pnamevalue);
        ll.addView(pemail);
        ll.addView(pemailvalue);
        ll.addView(min_att);
        ll.addView(min_attvalue);
        ll.addView(status);
        ll.addView(statusvalue);
        ll.addView(credits);
        ll.addView(creditsvalue);
        ll.addView(grade);
        ll.addView(gradevalue);
        ll.addView(lab);
        ll.addView(labvalue);
        ll.addView(desc);
        ll.addView(descvalue);

        marks.setText("Marks");
        attendance.setText("Attendance");

        bl.addView(marks);
        bl.addView(attendance);

        bl.setOrientation(LinearLayout.HORIZONTAL);

        ll.addView(bl);

        subjectLL.addView(ll);
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
        String s=getIntent().getStringExtra("sub_id");
        Intent intent = new Intent(this, AddNewSubjectActivity.class);
        intent.putExtra("subject_id",s);
        startActivity(intent);

    }
    public void DeleteSubject()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(
                DisplaySubjectDetails.this);
        alert.setTitle("Confirmation!!");
        alert.setMessage("Are you sure to delete subject");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do your work here
                Cursor res=myDB.getAllData("subject_details");
                String s=getIntent().getStringExtra("sub_id");
                boolean i = myDB.deleteDataSubject(s);
                boolean j=false;
                while(res.moveToNext())
                {
                    if(res.getString(1).equals(s))
                    {
                         j= myDB.deleteDataSubjectDetails(Integer.parseInt(s),res.getInt(0));
                    }
                }
                if(i && j)
                {
                    Toast.makeText(DisplaySubjectDetails.this, "Successfully Deleted", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(DisplaySubjectDetails.this, DisplaySubjectsActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(DisplaySubjectDetails.this, "Subject Deletion Failed", Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();

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

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, DisplaySubjectsActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
