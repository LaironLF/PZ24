package com.laironlf.pz24;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {
    final int REQUEST_CODE_PHOTO = 1;

    final String TAG = "myLogs";
    private Uri imageUri;
    File storageDir;

    ScrollView scrollView;
    LinearLayout linearScroll;
    ConstraintLayout main_layout;
    ImageView ivPhoto;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        main_layout = (ConstraintLayout) findViewById(R.id.main_layout);

        storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        main_layout.setOnTouchListener(new SwipeListener(){
            @Override
            public void swipeLeft() {
                super.swipeLeft();
                onClickPhoto();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        List<String> titles = Arrays.asList(storageDir.list());
        Collections.sort(titles);

        String pathName = storageDir.getAbsolutePath() + "/" + titles.get(titles.size() - 1);
        ivPhoto.setImageBitmap(BitmapFactory.decodeFile(pathName));
        ivPhoto.setRotation(270);

    }

    @SuppressLint("QueryPermissionsNeeded")
    public void onClickPhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                imageUri = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(takePictureIntent, REQUEST_CODE_PHOTO);
                overridePendingTransition(R.anim.to_left_in, R.anim.to_left_out);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CODE_PHOTO && resultCode == RESULT_OK) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ivPhoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (resultCode == RESULT_CANCELED) {
            Log.d(TAG, "Canceled");
        }
    }

}