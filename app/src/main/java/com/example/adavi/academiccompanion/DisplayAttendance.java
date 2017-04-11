package com.example.adavi.academiccompanion;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

public class DisplayAttendance extends AppCompatActivity {
    DatabaseHelper myDB;
    String s;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_attendance);
        myDB=new DatabaseHelper(this);
        Cursor c = myDB.getAllData("attendance");
        s=getIntent().getStringExtra("subj_id");
        while(c.moveToNext())
        {
            if(c.getString(1).equals(s) && c.getInt(2)==myDB.getcurrentsem())
            {
                flag=1;
            }
        }
        if(flag==0)
        {
            fetchstartdate();
        }


    }

    public void fetchstartdate()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Start Date for Subject");

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
}
