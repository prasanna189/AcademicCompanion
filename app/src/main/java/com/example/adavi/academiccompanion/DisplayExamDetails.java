package com.example.adavi.academiccompanion;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
public class DisplayExamDetails extends AppCompatActivity {
    DatabaseHelper myDB;
    String m_Text = "";
    String examtype,s,subname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_exam_details);
        myDB=new DatabaseHelper(this);

         s=getIntent().getStringExtra("subid");
        subname=myDB.getSubjectName(Integer.parseInt(s));
        examtype= getIntent().getStringExtra("examtype");
        setTitle(examtype);

        displaytypedetails();
    }
    public void displaytypedetails(){


        LinearLayout examLL = (LinearLayout) findViewById(R.id.exam_details);

        LinearLayout ll = new LinearLayout(this);

        TextView subject = new TextView(this);
        TextView exam= new TextView(this);
        TextView marks = new TextView(this);
        TextView max_marks=new TextView(this);

        TextView subjectvalue = new TextView(this);
        TextView examvalue= new TextView(this);
        TextView marksvalue = new TextView(this);
        TextView max_marksvalue=new TextView(this);

        LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        ll.setLayoutParams(ll_params);
        ll.setOrientation(LinearLayout.VERTICAL);
        subject.setText("Subject Name");
        subject.setTextSize(20);
        exam.setText("Exam Type");
        exam.setTextSize(20);
        marks.setText("Marks Obtained");
        marks.setTextSize(20);
        max_marks.setText("MAX MARKS");
        max_marks.setTextSize(20);

        Cursor res=myDB.getAllData("marks");
        while(res.moveToNext())
        {
            if(res.getString(1).equals(s) && res.getString(2).equals(examtype))
            {
                subjectvalue.setText(subname);
                subjectvalue.setTextSize(20);
                examvalue.setText(res.getString(2));
                examvalue.setTextSize(20);
                marksvalue.setText(res.getString(3));
                marksvalue.setTextSize(20);
                max_marksvalue.setText(res.getString(4));
                max_marksvalue.setTextSize(20);
            }
        }

        ll.addView(subject);
        ll.addView(subjectvalue);
        ll.addView(exam);
        ll.addView(examvalue);
        ll.addView(marks);
        ll.addView(marksvalue);
        ll.addView(max_marks);
        ll.addView(max_marksvalue);
        examLL.addView(ll);


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.marks_details_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.edit_marks:
                EditMarksDetails();
                return true;
            case R.id.delete_marks:
                DeleteMarks();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void EditMarksDetails()
    {
        int flag=0;
        Cursor res=myDB.getAllData("subject_details");
        while(res.moveToNext())
        {
            if(res.getString(1).equals(s) && (res.getString(7).equals("0")))
            {
                Intent intent = new Intent(this, AddNewSubjectMarks.class);
                intent.putExtra("subj_id",s);
                intent.putExtra("exam_type",examtype);
                startActivity(intent);
                flag=1;
            }

        }
        if(flag==0)
        {
            Toast.makeText(DisplayExamDetails.this, "Grade is Finalised, Cannot Update Subject Marks", Toast.LENGTH_SHORT).show();
        }
    }
    public void DeleteMarks()
    {

        int flag=0;
        Cursor res=myDB.getAllData("subject_details");
        while(res.moveToNext())
        {
            if(res.getString(1).equals(s) && (res.getString(7).equals("0")))
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(
                    DisplayExamDetails.this);
                alert.setTitle("Confirmation!!");
                alert.setMessage("Are you sure to delete Exam");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do your work here
                        Cursor res=myDB.getAllData("marks");

                        boolean j=false;
                        while(res.moveToNext())
                        {
                            if(res.getString(1).equals(s) && res.getString(2).equals(examtype))
                            {
                                //Toast.makeText(DisplayExamDetails.this,s, Toast.LENGTH_LONG).show();
                                j= myDB.deleteDataMarks(Integer.parseInt(s),res.getInt(0),res.getString(2));
                            }
                        }
                        if(j)
                        {
                            Toast.makeText(DisplayExamDetails.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DisplayExamDetails.this, SubjectMarks.class);
                            intent.putExtra("sub_id",s);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(DisplayExamDetails.this, "Subject Deletion Failed", Toast.LENGTH_SHORT).show();
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

        }
        if(flag==0)
        {
            Toast.makeText(DisplayExamDetails.this, "Grade is Finalised, Cannot Update Subject Marks", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, SubjectMarks.class);
        intent.putExtra("sub_id",s);
        startActivity(intent);
        super.onBackPressed();
    }
}
