package com.example.adavi.academiccompanion;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class DisplayExamDetails extends AppCompatActivity {
    DatabaseHelper myDB;
    String examtype,s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_exam_details);
        myDB=new DatabaseHelper(this);
         s=getIntent().getStringExtra("subid");
        examtype= getIntent().getStringExtra("examtype");
        setTitle(examtype);

        //displaytypedetails();
    }
    public void displaytypedetails(){

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
        Intent intent = new Intent(this, AddNewSubjectMarks.class);
        intent.putExtra("subj_id",s);
        intent.putExtra("exam_type",examtype);
        startActivity(intent);
    }
    public void DeleteMarks()
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
                    Toast.makeText(DisplayExamDetails.this, "Successfully Deleted", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(DisplayExamDetails.this, SubjectMarks.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(DisplayExamDetails.this, "Subject Deletion Failed", Toast.LENGTH_LONG).show();
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

//    @Override
//    public void onBackPressed() {
//
//        Intent intent = new Intent(this, SubjectMarks.class);
//        startActivity(intent);
//        super.onBackPressed();
//    }
}
