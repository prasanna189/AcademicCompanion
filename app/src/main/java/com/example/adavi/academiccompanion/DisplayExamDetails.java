package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class DisplayExamDetails extends AppCompatActivity {
    DatabaseHelper myDB;
    String examtype,s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_exam_details);
        myDB=new DatabaseHelper(this);
         s=getIntent().getStringExtra("sub_id");
        examtype= getIntent().getStringExtra("examtype");
        setTitle(examtype);

        displaytypedetails();
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
        String s=getIntent().getStringExtra("");
        Intent intent = new Intent(this, AddNewSubjectActivity.class);
        intent.putExtra("subject_id",s);
        startActivity(intent);
    }
    public void DeleteMarks()
    {

    }
}
