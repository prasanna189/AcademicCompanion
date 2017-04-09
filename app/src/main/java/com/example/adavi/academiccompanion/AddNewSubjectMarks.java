package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewSubjectMarks extends AppCompatActivity {

    DatabaseHelper myDB;
    EditText examtype,maxmarks,marks;
    Button buttonSaveMarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_subject_marks);
        myDB = new DatabaseHelper(this);

        examtype = (EditText) findViewById(R.id.examtype_edittext);
        maxmarks = (EditText) findViewById(R.id.maxmarks_edittext);
        marks = (EditText) findViewById(R.id.marksobtained_edittext);

        buttonSaveMarks = (Button) findViewById(R.id.save_marks_button);



        displayMarks();
    }

    void displayMarks()
    {
        String s= getIntent().getStringExtra("sub_id");
        String e_type=getIntent().getStringExtra("examtype");
        if(s!=null)
        {
            Cursor res=myDB.getAllData("marks");
            while(res.moveToNext()){
                if(res.getString(1).equals(s))
                {
                    examtype.setText(e_type);

                    int mark=res.getInt(3);
                    String str1=Integer.toString(mark);
                    marks.setText(str1);
                    mark=res.getInt(4);
                    str1=Integer.toString(mark);
                    maxmarks.setText(str1);
                    buttonSaveMarks.setText("Update");

                }
            }
        }
    }

    public void saveSubject(View view) {

        String s= getIntent().getStringExtra("sub_id");
        String e_type=getIntent().getStringExtra("examtype");

        if (s != null) {
            int flag=0;
            Cursor res=myDB.getAllData("marks");
            String exam_type=examtype.getText().toString();
            String mar=marks.getText().toString();
            String mm=maxmarks.getText().toString();
            int maxmar=Integer.parseInt(mm);
            int mark=Integer.parseInt(mar);

            boolean j=false;

            while(res.moveToNext())
            {
//                Toast.makeText(AddNewSubjectActivity.this, Integer.toString(res.getInt(1)), Toast.LENGTH_LONG).show();
                if(res.getString(1).equals(s) && res.getString(2).equals(e_type))
                {
                    j=myDB.updateDataMarks(res.getInt(0),res.getInt(1),res.getString(2),mark,maxmar,exam_type);
                }
            }

            if(j)
            {

                Toast.makeText(AddNewSubjectMarks.this, "Marks Details Updated", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, DisplayExamDetails.class);
                intent.putExtra("sub_id",s);
                intent.putExtra("examtype",e_type);
                startActivity(intent);
            }
            else {
                Toast.makeText(AddNewSubjectMarks.this, "Marks Updation Failed", Toast.LENGTH_LONG).show();
            }

        }
        else
        {
            int sem=myDB.getcurrentsem();

            boolean isInserted = myDB.insertDataMarks(sem,Integer.parseInt(s),examtype.getText().toString(),Integer.parseInt(marks.getText().toString()),Integer.parseInt(maxmarks.getText().toString()));

            if (isInserted == true) {
                Toast.makeText(AddNewSubjectMarks.this, "Marks Saved", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, SubjectMarks.class);
                startActivity(intent);
            } else {
                Toast.makeText(AddNewSubjectMarks.this, "Subject not Saved", Toast.LENGTH_LONG).show();
            }

        }
    }


    public void resetmarks(View view) {
        Intent intent = new Intent(this, AddNewSubjectActivity.class);
        startActivity(intent);
    }
}
