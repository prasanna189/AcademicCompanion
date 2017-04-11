package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class EditUserProfileActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    private int PICK_IMAGE_REQUEST = 1;
    public static PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        myDB = new DatabaseHelper(this);
        prefManager = new PrefManager(this);

        EditText username = (EditText) findViewById(R.id.edit_username_et);
        EditText useremail = (EditText) findViewById(R.id.edit_useremail_et);
        EditText userphone = (EditText) findViewById(R.id.edit_userphone_et);
        ImageView imageView = (ImageView)findViewById(R.id.edit_profile_icon);

        username.setText(myDB.getUserName());
        useremail.setText(myDB.getUserEmail());
        userphone.setText(myDB.getUserPhone());

        if(prefManager.isProfilePicSet())
        {
            imageView.setImageBitmap(DbBitmapUtility.getImage(myDB.getImage("profile_pic")));
        }

        ImageView buttonLoadImage = (ImageView) findViewById(R.id.edit_profile_icon);
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

    public void saveUserProfile(View view)
    {
        boolean flag;
        EditText username = (EditText) findViewById(R.id.edit_username_et);
        EditText useremail = (EditText) findViewById(R.id.edit_useremail_et);
        EditText userphone = (EditText) findViewById(R.id.edit_userphone_et);



        flag = myDB.updateDataUserDetails( username.getText().toString(), useremail.getText().toString(), userphone.getText().toString() );

        if(flag == true)//insertion successful
        {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(EditUserProfileActivity.this, "Update not successful.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                if(prefManager.isProfilePicSet())
                {
                    boolean flag = myDB.updateImage("profile_pic", DbBitmapUtility.getBytes(bitmap));
                }
                else
                {
                    boolean flag = myDB.insertDataImages("profile_pic", DbBitmapUtility.getBytes(bitmap));
                }
                ImageView imageView = (ImageView)findViewById(R.id.edit_profile_icon);
                imageView.setImageBitmap(DbBitmapUtility.getImage(myDB.getImage("profile_pic")));
                prefManager.setProfilePic(true);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //On clicking back button, it takes back to main acitivity..

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent intent = new Intent(this, DisplayUserProfileActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}
