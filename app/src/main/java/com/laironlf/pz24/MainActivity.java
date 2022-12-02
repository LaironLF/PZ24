package com.laironlf.pz24;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;

public class MainActivity extends Activity {
    final int REQUEST_CODE_PHOTO = 1;
    int width;

    final String TAG = "myLogs";
    private Uri imageUri;
    File storageDir;

    ScrollView scrollView;
    ConstraintLayout main_layout;
    ImageView ivPhoto;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWidth();

        scrollView = (ScrollView) findViewById(R.id.scrollPhoto);
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        main_layout = (ConstraintLayout) findViewById(R.id.main_layout);
        storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        LinearLayout linearLayout = createLinearLayout();
        setContentView(linearLayout);

//        photoModule = new PhotoModule(this, getPackageManager(), getExternalFilesDir(Environment.DIRECTORY_PICTURES));

        main_layout.setOnTouchListener(new SwipeListener(){
            @Override
            public void swipeLeft() {
                super.swipeLeft();
                onClickPhoto();
//                Bitmap bitmap = photoModule.onClickPhoto();
//                ivPhoto.setImageBitmap(bitmap);
            }
        });

    }

    private void getWidth(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
    }

    @Override
    protected void onResume() {
        super.onResume();

        String[] titles = storageDir.list();


        TextView textView = (TextView) findViewById(R.id.textTest);
        textView.setText(titles[0]);
    }

    private void fillScrollView(String[] filesPaths){
        int length = filesPaths.length/2;
        int k = 0;
        for (int i = 0; i < length; i++){
            LinearLayout linearLayout = createLinearLayout();
            for (int j = 0; j < 2; j++){

                k++;
            }
        }
    }

    private LinearLayout createLinearLayout() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(params);
        scrollView.addView(linearLayout);
        return linearLayout;

    }

    private void fillPicture(LinearLayout linearLayout){
        int bitWidth = width / 3;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
        params.rightMargin = bitWidth/4;
        params.leftMargin = bitWidth/4;

        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(params);
        linearLayout.addView(imageView);
    }

    private Bitmap loadImageFromAsset(String absolutePath) {
        return BitmapFactory.decodeFile(absolutePath);
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