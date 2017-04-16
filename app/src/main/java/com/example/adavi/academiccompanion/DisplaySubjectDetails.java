package com.example.adavi.academiccompanion;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.color.black;
import static android.R.color.white;
import static android.R.id.edit;
import static android.R.id.input;

public class DisplaySubjectDetails extends AppCompatActivity {

    DatabaseHelper myDB;
    String sub_name;
    String gradevalue=null;
    String s;
    TextView txt;
    Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_subject_details);
        myDB=new DatabaseHelper(this);
        s=getIntent().getStringExtra("sub_id");
        sub_name= myDB.getSubjectName(Integer.parseInt(s));
        setTitle(sub_name);

        String[] g={"","AA","AB","BB","BC","CC","CD","DD","W","FF"};

        final ArrayAdapter<String> adp = new ArrayAdapter<String>(DisplaySubjectDetails.this,
                R.layout.grade_spinner,R.id.Grade_list, g);

        sp=new Spinner(this);

        sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        sp.setAdapter(adp);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int
                    position, long id) {

                ViewGroup vg=(ViewGroup)view;

                TextView tv=(TextView)vg.findViewById(R.id.Grade_list);
                gradevalue=tv.getText().toString();
//                Toast.makeText(AddEventActivity.this, tv.getText().toString(),
//                        Toast.LENGTH_LONG).show();

            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {


                gradevalue=null;
            }

        });


        displaysubjectdetails();
    }
int i;
    String str=null;
    public void displaysubjectdetails()
    {

        LinearLayout subjectLL = (LinearLayout) findViewById(R.id.subject_details);

        LinearLayout ll1 = (LinearLayout)findViewById(R.id.s1);
        LinearLayout ll2 = (LinearLayout)findViewById(R.id.s2);
        LinearLayout ll3 = (LinearLayout)findViewById(R.id.s3);
        LinearLayout ll4 = (LinearLayout)findViewById(R.id.s4);
        LinearLayout ll5 = (LinearLayout)findViewById(R.id.s5);
        LinearLayout ll6 = (LinearLayout)findViewById(R.id.s6);
        LinearLayout ll7 = (LinearLayout)findViewById(R.id.s7);
        LinearLayout ll8 = (LinearLayout)findViewById(R.id.s8);
        LinearLayout ll9 = (LinearLayout)findViewById(R.id.s9);

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

//        LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//        );
//        ll.setLayoutParams(ll_params);
//        ll.setOrientation(LinearLayout.VERTICAL);


        sname.setText("Subject Name : ");
        sname.setTextColor(Color.BLACK);
        sname.setTypeface(null, Typeface.BOLD_ITALIC);
        sname.setTextSize(20);
        sname.setPadding(20,15,0,15);

//        sname.setTextColor(Color.parseColor("#FF4081"));


        pname.setText("Professor Name : ");
        pname.setTextColor(Color.BLACK);
        pname.setTypeface(null, Typeface.BOLD_ITALIC);
        pname.setTextSize(20);
        pname.setPadding(20,15,0,15);
//        pname.setTextColor(Color.parseColor("#FF4081"));

        pemail.setText("Professor Email : ");
        pemail.setTextColor(Color.BLACK);
        pemail.setTypeface(null, Typeface.BOLD_ITALIC);
        pemail.setTextSize(20);
        pemail.setPadding(20,15,0,15);
//        pemail.setTextColor(Color.parseColor("#FF4081"));

        min_att.setText("Minimum Attendance : ");
        min_att.setTextColor(Color.BLACK);
        min_att.setTextSize(20);
        min_att.setTypeface(null, Typeface.BOLD_ITALIC);
        min_att.setPadding(20,15,0,15);
//        min_att.setTextColor(Color.parseColor("#FF4081"));

        status.setText("Status : ");
        status.setTextColor(Color.BLACK);
        status.setTextSize(20);
        status.setTypeface(null, Typeface.BOLD_ITALIC);
        status.setPadding(20,15,0,15);
//        status.setTextColor(Color.parseColor("#FF4081"));

        credits.setText("Credits : ");
        credits.setTextColor(Color.BLACK);
        credits.setTextSize(20);
        credits.setTypeface(null, Typeface.BOLD_ITALIC);
        credits.setPadding(20,15,0,15);
//        credits.setTextColor(Color.parseColor("#FF4081"));

        grade.setText("Grade : ");
        grade.setTextColor(Color.BLACK);
        grade.setTextSize(20);
        grade.setTypeface(null, Typeface.BOLD_ITALIC);
        grade.setPadding(20,15,0,15);
//        grade.setTextColor(Color.parseColor("#FF4081"));

        lab.setText("Lab : ");
        lab.setTextColor(Color.BLACK);
        lab.setTextSize(20);
        lab.setTypeface(null, Typeface.BOLD_ITALIC);
        lab.setPadding(20,15,0,15);
//        lab.setTextColor(Color.parseColor("#FF4081"));

        desc.setText("Description : ");
        desc.setTextColor(Color.BLACK);
        desc.setTextSize(20);
        desc.setTypeface(null, Typeface.BOLD_ITALIC);
        desc.setPadding(20,15,0,15);


        Cursor res=myDB.getAllData("subject_details");
        String s=getIntent().getStringExtra("sub_id");
        while(res.moveToNext())
        {
            if(res.getString(1).equals(s))
            {
                snamevalue.setText(sub_name);
                snamevalue.setTextSize(20);
                snamevalue.setTextColor(Color.BLACK);
                snamevalue.setPadding(20,5,0,5);
//                snamevalue.setTypeface(null,Typeface.BOLD);


                pnamevalue.setText(res.getString(2));
                pnamevalue.setTextColor(Color.BLACK);
                pnamevalue.setTextSize(20);
                pnamevalue.setPadding(20,5,0,5);
//                pnamevalue.setTypeface(null,Typeface.BOLD);

                if(res.getString(3).length()!=0)
                {
                    pemailvalue.setText(res.getString(3));
                    pemailvalue.setTextColor(Color.BLACK);
                    pemailvalue.setTextSize(20);
                    pemailvalue.setPadding(20,5,0,5);
                }
                else
                {
                    pemailvalue.setText("none");
                    pemailvalue.setTextColor(Color.BLACK);
                    pemailvalue.setTextSize(20);
                    pemailvalue.setPadding(20,5,0,5);
                }

//                pemailvalue.setTypeface(null,Typeface.BOLD);

                min_attvalue.setText(res.getString(4));
                min_attvalue.setTextColor(Color.BLACK);
                min_attvalue.setTextSize(20);
                min_attvalue.setPadding(20,5,0,5);
//                min_attvalue.setTypeface(null,Typeface.BOLD);

                statusvalue.setText(res.getString(5));
                statusvalue.setTextSize(20);
                statusvalue.setTextColor(Color.BLACK);
                statusvalue.setPadding(20,5,0,5);
//                statusvalue.setTypeface(null,Typeface.BOLD);

                creditsvalue.setText(res.getString(6));
                creditsvalue.setTextColor(Color.BLACK);
                creditsvalue.setTextSize(20);
                creditsvalue.setPadding(20,5,0,5);
//                creditsvalue.setTypeface(null,Typeface.BOLD);

                if(res.getString(7).equals("0"))
                {
                    gradevalue.setText("Yet To Confirm");
                    gradevalue.setTextSize(20);
                    gradevalue.setPadding(20,5,0,5);
//                    gradevalue.setTypeface(null,Typeface.BOLD);
                }
                else {
                    gradevalue.setText(res.getString(7));
                    gradevalue.setTextSize(20);
                    gradevalue.setPadding(20,5,0,5);
//                    gradevalue.setTypeface(null,Typeface.BOLD);
                }
                gradevalue.setTextColor(Color.BLACK);
                if(res.getInt(8)==1)
                {
                    labvalue.setText("Yes");
                    labvalue.setTextSize(20);
                    labvalue.setPadding(20,5,0,5);
                    //labvalue.setTypeface(null,Typeface.BOLD);
                }
                else
                {
                    labvalue.setText("No");
                    labvalue.setTextSize(20);
                    labvalue.setPadding(20,5,0,5);
                   // labvalue.setTypeface(null,Typeface.NORMAL);
                }
                labvalue.setTextColor(Color.BLACK);
                if(res.getString(9).length()!=0)
                {
                    descvalue.setText(res.getString(9));
                    descvalue.setTextSize(20);
                    descvalue.setPadding(20,5,0,5);
                }
                else
                {
                    descvalue.setText("none");
                    descvalue.setTextSize(20);
                    descvalue.setPadding(20,5,0,5);
                }
                descvalue.setTextColor(Color.BLACK);
                //descvalue.setTypeface(null,Typeface.NORMAL);
            }
        }


        ll1.addView(sname);
        ll1.addView(snamevalue);

        ll2.addView(pname);
        ll2.addView(pnamevalue);


        ll3.addView(pemail);
        ll3.addView(pemailvalue);

        ll4.addView(min_att);
        ll4.addView(min_attvalue);

        ll5.addView(status);
        ll5.addView(statusvalue);


        ll6.addView(credits);
        ll6.addView(creditsvalue);

        ll7.addView(grade);
        ll7.addView(gradevalue);


        ll8.addView(lab);
        ll8.addView(labvalue);


        ll9.addView(desc);
        ll9.addView(descvalue);



        LinearLayout.LayoutParams rb_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        );
        marks.setText("Marks");
        marks.setId(Integer.parseInt(s));
        marks.setLayoutParams(rb_params);

        rb_params.setMargins(75,10,50,10);
       // marks.setPadding(5,10,25,10);

        marks.setWidth(0);

        attendance.setText("Attendance");
        attendance.setLayoutParams(rb_params);
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

        attendance.setId(Integer.parseInt(s));

        attendance.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {

                Button pressed;
                pressed=((Button)v);
                int flag1=0;
                i=pressed.getId();
                Cursor c = myDB.getAllData("subject_details");
                while(c.moveToNext() && flag1==0)
                {
                    if(c.getString(1).equals(Integer.toString(i)) && (c.getString(7).equals("0")))
                    {
                        flag1=1;
                    }

                }
                if(flag1==1)
                {

                    Intent intent = new Intent(DisplaySubjectDetails.this, DisplayAttendance.class);
                    String subjectid=Integer.toString(i);
                    intent.putExtra("subj_id",subjectid);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(DisplaySubjectDetails.this, "Grade is Finalised, Cannot Show attendance", Toast.LENGTH_SHORT).show();

                }

            }
        });

        bl.addView(marks);
        bl.addView(attendance);

        bl.setOrientation(LinearLayout.HORIZONTAL);

        subjectLL.addView(bl);

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
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

            builder.setView(sp);

// Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

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
                        boolean subattdelete=false;
                        while(res.moveToNext())
                        {
                            if(res.getString(1).equals(s))
                            {
                                subdetailsdelete= myDB.deleteDataSubjectDetails(Integer.parseInt(s),res.getInt(0));
                                submarksdelete=myDB.deleteMarksOnSubDelete(Integer.parseInt(s),myDB.getcurrentsem());
                                subattdelete=myDB.deleteDataAttendanceonsubdelete(myDB.getcurrentsem(),Integer.parseInt(s));
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
