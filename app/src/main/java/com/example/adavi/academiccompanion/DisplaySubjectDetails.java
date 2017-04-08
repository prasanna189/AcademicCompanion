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
import android.widget.TextView;
import android.widget.Toast;

import static android.R.id.edit;

public class DisplaySubjectDetails extends AppCompatActivity {

    DatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_subject_details);
        myDB=new DatabaseHelper(this);
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
                int i = myDB.deleteDataSubject(s);
                int j=-1;
                while(res.moveToNext())
                {
                    if(res.getString(1).equals(s))
                    {
                         j= myDB.deleteDataSubjectDetails(Integer.parseInt(s),res.getInt(0));
                    }
                }
                if(i<=0 && j<=0)
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
}
