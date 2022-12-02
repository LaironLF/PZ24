//package com.laironlf.pz24;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Environment;
//import android.provider.MediaStore;
//
//import android.util.Log;
//import android.widget.ImageView;
//
//import androidx.core.content.FileProvider;
//
//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//
//public class PhotoModule extends Activity {
//    final int REQUEST_CODE_PHOTO = 1;
//    final String TAG = "myLogs";
//    private Uri imageUri;
//
//    Context context;
//    PackageManager packageManager;
//    private Bitmap bitmapPhoto;
//    private File storageDir;
//
//    public PhotoModule(Context context, PackageManager pk, File storageDir){
//        this.context = context;
//        this.packageManager = pk;
//        this.storageDir = storageDir;
//    }
//
//
//    @SuppressLint("QueryPermissionsNeeded")
//    public Bitmap onClickPhoto() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(packageManager) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                imageUri = FileProvider.getUriForFile(context, "com.example.android.fileprovider", photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                startActivityForResult(takePictureIntent, REQUEST_CODE_PHOTO);
//                overridePendingTransition(R.anim.to_left_in, R.anim.to_left_out);
//            }
//        }
//        return bitmapPhoto;
//    }
//
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        @SuppressLint("SimpleDateFormat")
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//        return image;
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        if (requestCode == REQUEST_CODE_PHOTO && resultCode == RESULT_OK) {
//            try {
//                bitmapPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else if (resultCode == RESULT_CANCELED) {
//            Log.d(TAG, "Canceled");
//        }
//    }
//}
