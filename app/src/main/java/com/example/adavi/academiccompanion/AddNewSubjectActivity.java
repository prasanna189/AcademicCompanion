package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.attr.y;

public class AddNewSubjectActivity extends AppCompatActivity {


    DatabaseHelper myDB;
    EditText editsname, editpname, editpemail,credits,minattendance,description;
    CheckBox lab;
    Button buttonSaveSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_subject);
        myDB = new DatabaseHelper(this);

        editsname = (EditText) findViewById(R.id.sname_edittext);
        editpname = (EditText) findViewById(R.id.pname_edittext);
        editpemail = (EditText) findViewById(R.id.pemail_edittext);
        credits = (EditText) findViewById(R.id.credits_edittext);
        minattendance = (EditText) findViewById(R.id.MinimumAttendance_edittext);
        description = (EditText) findViewById(R.id.Description_edittext);
        buttonSaveSubject = (Button) findViewById(R.id.savesubject_button);
        lab=(CheckBox) findViewById(R.id.lab);


        displaySubjects();
    }

    void displaySubjects()
    {
        String s= getIntent().getStringExtra("subject_id");
        if(s!=null)
        {
            Cursor res=myDB.getAllData("subject_details");
            while(res.moveToNext()){
                if(res.getString(1).equals(s))
                {
                    editsname.setText(myDB.getSubjectName(res.getInt(1)));
                    editpname.setText(res.getString(2));
                    editpemail.setText(res.getString(3));
                    int cre=res.getInt(6);
                    String str1=Integer.toString(cre);
                    credits.setText(str1);
                    cre=res.getInt(4);
                    str1=Integer.toString(cre);
                    minattendance.setText(str1);
                    description.setText(res.getString(9));
                    buttonSaveSubject.setText("Update");
                    if(res.getInt(8)==1)
                    {
                        lab.setChecked(true);
                    }

                }
            }
        }
    }

    public void saveSubject(View view) {

        String s= getIntent().getStringExtra("subject_id");
        if (s != null) {
            int flag=0;
            Cursor res=myDB.getAllData("subject_details");
            String sname=editsname.getText().toString();
            String pname=editpname.getText().toString();
            String pemail=editpemail.getText().toString();
            String cre=credits.getText().toString();
            int credits=Integer.parseInt(cre);
            String min_att=minattendance.getText().toString();
            int minattendance=Integer.parseInt(min_att);
            String desc=description.getText().toString();
            if(lab.isChecked())
            {
                flag=1;
            }
            boolean j=false;
           boolean i= myDB.updateDataSubject(sname,Integer.parseInt(s));
            while(res.moveToNext())
            {
//                Toast.makeText(AddNewSubjectActivity.this, Integer.toString(res.getInt(1)), Toast.LENGTH_LONG).show();
                if(res.getString(1).equals(s))
                {
                     j=myDB.updateDataSubject_details(res.getInt(0),res.getInt(1),pname,pemail,minattendance,res.getString(5),credits,res.getString(7),flag,desc);
                }
            }

            if(i && j)
            {
                Toast.makeText(AddNewSubjectActivity.this, "Subject Details Updated", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, DisplaySubjectsActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(AddNewSubjectActivity.this, "Subject Updation Failed", Toast.LENGTH_LONG).show();
            }

        }
        else
        {
            int sem=myDB.getcurrentsem();

            int flag=0;
            String cre=credits.getText().toString();
            int c=Integer.parseInt(cre);

            String minat=minattendance.getText().toString();
            int minatt=Integer.parseInt(minat);
            if(lab.isChecked())
            {
                flag=1;
            }
            int sub_id = myDB.insertDataSubject(editsname.getText().toString());

            boolean isInserted = myDB.insertDataSubjectDetails(sem,sub_id,editpname.getText().toString(),editpemail.getText().toString(),minatt,"Running",c,"0",flag,description.getText().toString());

            if (isInserted == true) {
                Toast.makeText(AddNewSubjectActivity.this, "Subject Saved", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, DisplaySubjectsActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(AddNewSubjectActivity.this, "Subject not Saved", Toast.LENGTH_LONG).show();
            }

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent intent = new Intent(this, DisplaySubjectsActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    public void reset(View view) {
        Intent intent = new Intent(this, AddNewSubjectActivity.class);
        startActivity(intent);
    }


}
