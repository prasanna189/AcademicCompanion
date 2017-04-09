package com.example.adavi.academiccompanion;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class DisplayEventDetailsActivity extends AppCompatActivity {


    DatabaseHelper myDB = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_event_details);

        myDB = new DatabaseHelper(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.event_details_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.edit_event:
                editEvent();
                return true;
            case R.id.delete_event:
                deleteEvent();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void editEvent()
    {
//        boolean extra=getIntent().hasCategory("button_event_id");
//        if(extra)
//        {
//            String s=getIntent().getStringExtra("button_event_id");
//            Intent intent = new Intent(this, AddEventActivity.class);
//            intent.putExtra("button_event_id",s);
//            startActivity(intent);
//        }
//        else
//        {
//            Intent intent = new Intent(this, AddEventActivity.class);
//            startActivity(intent);
//        }
//        Intent intent = new Intent(this, AddEventActivity.class);
//        startActivity(intent);

        String s=getIntent().getStringExtra("button_event_id");
        Intent intent = new Intent(this, AddEventActivity.class);
        intent.putExtra("button_event_id",s);
        startActivity(intent);
    }

    void deleteEvent()
    {
        String s=getIntent().getStringExtra("button_event_id");
        final int event_id=Integer.parseInt(s);




        AlertDialog.Builder alert = new AlertDialog.Builder(
                DisplayEventDetailsActivity.this);
        alert.setTitle("Confirmation!!");
        alert.setMessage("Are you sure to delete subject");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do your work here
                dialog.dismiss();
                boolean deleteStatus = myDB.deleteDataEvent(event_id);
                if(deleteStatus )
                {
                    Toast.makeText(DisplayEventDetailsActivity.this, "Successfully Deleted", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(DisplayEventDetailsActivity.this, "Delete Failed!", Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(DisplayEventDetailsActivity.this, DisplayEventActivity.class);
                startActivity(intent);


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
