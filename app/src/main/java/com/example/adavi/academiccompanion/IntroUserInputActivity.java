package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;

public class IntroUserInputActivity extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 1;
    DatabaseHelper myDB;
    public static PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_user_input);
        myDB = new DatabaseHelper(this);
        prefManager = new PrefManager(this);

        ImageView buttonLoadImage = (ImageView) findViewById(R.id.user_input_image);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                ImageView imageView = (ImageView) findViewById(R.id.user_input_image);
                imageView.setImageBitmap(bitmap);

                boolean flag = myDB.insertDataImages("profile_pic", DbBitmapUtility.getBytes(bitmap));
                //shared preference to indicate profile pic is set
                prefManager.setProfilePic(true);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void submit(View view) {
        boolean flag = true;
        EditText nameId = (EditText) findViewById(R.id.name_id);
        EditText semId = (EditText) findViewById(R.id.sem_id);
        EditText emailId = (EditText) findViewById(R.id.email_id);


        if (nameId.getText().toString().length() == 0) {
            nameId.setError("Please Enter Your Name");
            flag = false;
        }
        if (semId.getText().toString().length() == 0) {
            semId.setError("Please Enter Your Semester");
            flag = false;
        }
        if (emailId.getText().toString().length() == 0) {
            emailId.setError("Please Enter Your Email ID");
            flag = false;
        }


        if (flag)
        {
            boolean a,b,c;

            myDB.setCurrentSem( semId.getText().toString() );

            a = myDB.insertDataUserDetails(nameId.getText().toString(), emailId.getText().toString(), "", semId.getText().toString());
            c = myDB.insertDataSemester( semId.getText().toString() );

            com.example.adavi.academiccompanion.WelcomeActivity.prefManager.setFirstTimeLaunch(false);
            Intent intent = new Intent(IntroUserInputActivity.this, MainActivity.class);
            startActivity(intent);
            finish();



        }
    }
}
