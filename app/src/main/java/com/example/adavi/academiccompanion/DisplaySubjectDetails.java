package com.example.adavi.academiccompanion;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.id.edit;
import static android.R.id.input;

public class DisplaySubjectDetails extends AppCompatActivity {

    DatabaseHelper myDB;
    String sub_name;
    String gradevalue=null;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_subject_details);
        myDB=new DatabaseHelper(this);
        s=getIntent().getStringExtra("sub_id");
        sub_name= myDB.getSubjectName(Integer.parseInt(s));
        setTitle(sub_name);

        displaysubjectdetails();
    }
int i;
    String str=null;
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
        sname.setTextSize(20);

        pname.setText("Professor's Name");
        pname.setTextSize(20);

        pemail.setText("Professor's Email");
        pemail.setTextSize(20);

        min_att.setText("Minimum Attendance");
        min_att.setTextSize(20);

        status.setText("Status");
        status.setTextSize(20);

        credits.setText("Credits");
        credits.setTextSize(20);

        grade.setText("Grade");
        grade.setTextSize(20);

        lab.setText("Lab");
        lab.setTextSize(20);

        desc.setText("Description");
        desc.setTextSize(20);

        Cursor res=myDB.getAllData("subject_details");
        String s=getIntent().getStringExtra("sub_id");
        while(res.moveToNext())
        {
            if(res.getString(1).equals(s))
            {
                snamevalue.setText(sub_name);
                snamevalue.setTextSize(20);

                pnamevalue.setText(res.getString(2));
                pnamevalue.setTextSize(20);

                pemailvalue.setText(res.getString(3));
                pemailvalue.setTextSize(20);

                min_attvalue.setText(res.getString(4));
                min_attvalue.setTextSize(20);

                statusvalue.setText(res.getString(5));
                statusvalue.setTextSize(20);

                creditsvalue.setText(res.getString(6));
                creditsvalue.setTextSize(20);

                if(res.getString(7).equals("0"))
                {
                    gradevalue.setText("Yet To Confirm");
                    gradevalue.setTextSize(20);
                }
                else {
                    gradevalue.setText(res.getString(7));
                    gradevalue.setTextSize(20);
                }

                if(res.getInt(8)==1)
                {
                    labvalue.setText("Yes");
                    labvalue.setTextSize(20);
                }
                else
                {
                    labvalue.setText("No");
                    labvalue.setTextSize(20);
                }

                descvalue.setText(res.getString(9));
                descvalue.setTextSize(20);
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
        marks.setId(Integer.parseInt(s));

        attendance.setText("Attendance");
        marks.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {

                Button pressed;
                pressed=((Button)v);
                i=pressed.getId();

                Intent intent = new Intent(DisplaySubjectDetails.this, SubjectMarks.class);
                str=Integer.toString(i);
                intent.putExtra("sub_id",str);
                startActivity(intent);

            }
        });


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
            case R.id.final_grade:
                SetGrade();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void SetGrade()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Final Grade");

// Set up the input
        int count=0;
        Cursor res=myDB.getAllData("marks");
        while(res.moveToNext())
        {
            if(myDB.getcurrentsem()==res.getInt(0) && res.getString(1).equals(s))
            {
                count++;
            }
        }
        if(count>0)
        {
            final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

// Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    gradevalue = input.getText().toString();

                    Cursor c = myDB.getAllData("subject_details");
                    boolean j=false;
                    while(c.moveToNext())
                    {
                        //Toast.makeText(DisplaySubjectDetails.this,s, Toast.LENGTH_SHORT).show();
                        if(s.equals(c.getString(1)) && c.getInt(0)==myDB.getcurrentsem())
                        {
                            //Toast.makeText(DisplaySubjectDetails.this, "Successfully ", Toast.LENGTH_SHORT).show();

                            j=myDB.updateDataSubject_details(c.getInt(0),c.getInt(1),c.getString(2),c.getString(3),c.getInt(4),"Completed",c.getInt(6),gradevalue,c.getInt(8),c.getString(9));
                        }
                    }
                    if(j)
                    {
                        Toast.makeText(DisplaySubjectDetails.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DisplaySubjectDetails.this, DisplaySubjectDetails.class);
                        intent.putExtra("sub_id",s);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(DisplaySubjectDetails.this, "Not Updated", Toast.LENGTH_SHORT).show();
                    }
//                SQLiteDatabase db = this.getWritableDatabase();
//
//                String sql="update subject_details set grade='"+gradevalue+"' where subject_id="+Integer.parseInt(s)+";";
//                db.execSQL(sql);


                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }
        else
        {
            subjectAlert("No Marks Entered ","Please Go And Add Marks");

        }

    }
    public void subjectAlert(String title,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
       builder.setMessage(message);
        builder.show();
    }
    public void EditSubjectDetails()
    {
        int flag=0;
        Cursor res=myDB.getAllData("subject_details");
        while(res.moveToNext())
        {
            if(res.getString(1).equals(s) && (res.getString(7).equals("0")))
            {// String s=getIntent().getStringExtra("sub_id");
                Intent intent = new Intent(this, AddNewSubjectActivity.class);
                intent.putExtra("subject_id",s);
                startActivity(intent);
                flag=1;
            }

        }
        if(flag==0)
        {
            Toast.makeText(DisplaySubjectDetails.this, "Grade is Finalised, Cannot Update Subject Details", Toast.LENGTH_SHORT).show();
        }



    }
    public void DeleteSubject()
    {
        int flag=0;
        Cursor res=myDB.getAllData("subject_details");
        while(res.moveToNext())
        {
            if(res.getString(1).equals(s) && (res.getString(7).equals("0")))
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
                        boolean subnamedelete = myDB.deleteDataSubject(s);
                        boolean subdetailsdelete=false;
                        boolean submarksdelete=false;
                        while(res.moveToNext())
                        {
                            if(res.getString(1).equals(s))
                            {
                                subdetailsdelete= myDB.deleteDataSubjectDetails(Integer.parseInt(s),res.getInt(0));
                                submarksdelete=myDB.deleteMarksOnSubDelete(Integer.parseInt(s),myDB.getcurrentsem());
                            }
                        }

                        if(subnamedelete && subdetailsdelete && submarksdelete)
                        {
                            Toast.makeText(DisplaySubjectDetails.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DisplaySubjectDetails.this, DisplaySubjectsActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(DisplaySubjectDetails.this, "Subject Deletion Failed", Toast.LENGTH_SHORT).show();
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


                flag=1;
            }

        }
        if(flag==0)
        {
            Toast.makeText(DisplaySubjectDetails.this, "Grade is Finalised, Cannot Delete Subject Details", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, DisplaySubjectsActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
