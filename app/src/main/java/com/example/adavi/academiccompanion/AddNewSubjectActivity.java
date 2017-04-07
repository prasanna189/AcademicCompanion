package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.database.Cursor;
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

    }

    public void saveSubject(View view) {


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
//        Intent intent = new Intent(this, DisplaySubjectsActivity.class);
//            startActivity(intent);

         boolean isInserted = myDB.insertDataSubjectDetails(sem,sub_id,editpname.getText().toString(),editpemail.getText().toString(),minatt,"Running",c,"0",flag,description.getText().toString());

        if (isInserted == true) {
            Toast.makeText(AddNewSubjectActivity.this, "Subject Saved", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, DisplaySubjectsActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(AddNewSubjectActivity.this, "Subject not Saved", Toast.LENGTH_LONG).show();
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
