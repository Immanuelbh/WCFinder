package com.ijbh.wcfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewWc extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1;
    private static final int WRITE_PERMISSION_REQUEST = 1;
    private static final String IMAGE_NAME = "Pic";

    private ImageView wcImgIv;
    private File imgFile;
    private Button firstPicBtn;
    private Button addPicBtn;
    private WaterCloset newWc;
    private Bitmap wcBitmap;
    private String wcName, wcDesc;
    private int wcFloor;
    private String currentImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_wc);

        wcImgIv = findViewById(R.id.wc_new_img);

        firstPicBtn = findViewById(R.id.first_pic_btn);
        addPicBtn = findViewById(R.id.add_pic_btn);
        final EditText wcNameEt = findViewById(R.id.wc_new_name);
        final EditText wcFloorEt = findViewById(R.id.wc_new_floor);
        final EditText wcDescEt = findViewById(R.id.wc_new_desc);
        Button saveWcBtn = findViewById(R.id.save_wc_btn);


        firstPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= 23){
                    int hasWritePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

                    if(hasWritePermission != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(NewWc.this, "no permissions!", Toast.LENGTH_SHORT).show();
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_PERMISSION_REQUEST);
                    }
                    else {

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        try {
                            imgFile = new File(String.valueOf(createImageFile()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if(imgFile != null){
                            Uri imgUri = FileProvider.getUriForFile(NewWc.this, getPackageName()+".provider",imgFile);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                            startActivityForResult(intent, CAMERA_REQUEST);
                        }

                        /* works
                        file = new File(Environment.getExternalStorageDirectory(), IMAGE_NAME+"jpg");
                        Uri imageUri = FileProvider.getUriForFile(
                            NewWc.this,
                                getPackageName()+".provider",
                                file);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, CAMERA_REQUEST);
                        */

                        //file = new File("/storage/emulated/0/Pic.jpeg");

                        //file = new File(Environment.getExternalStorageDirectory(), "Pic.jpeg");
                        //file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "DemoFile.jpg"); // different function than example
                        //Uri fileUri = FileProvider.getUriForFile(NewWc.this, "com.ijbh.wcfinder.provider", file);

                        //Uri fileUri = Uri.fromFile(file);

                        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        ////intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        //intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        ////Toast.makeText(NewWc.this, file.getAbsolutePath(), Toast.LENGTH_SHORT).show();

                        //startActivityForResult(intent, CAMERA_REQUEST);

                    }
                }
                else{
                    Toast.makeText(NewWc.this, "SDK Lower than 24", Toast.LENGTH_SHORT).show();
                }

            }
        });


        saveWcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(NewWc.this).create();
                alertDialog.setTitle("Save new WC");
                alertDialog.setMessage("Are you sure you want to save this WC?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        wcName = wcNameEt.getText().toString();
                        wcDesc = wcDescEt.getText().toString();
                        wcFloor = Integer.parseInt(wcFloorEt.getText().toString());

                        //newWc = new WaterCloset(wcName, wcDesc, wcFloor,false, wcBitmap
                        //       ,R.drawable.ic_favorite_border_black_24dp);

                        newWc = new WaterCloset(wcName, wcDesc, wcFloor, false, currentImagePath,R.drawable.ic_favorite_border_black_24dp);
                        Intent intent = new Intent(NewWc.this, MainActivity.class);
                        intent.putExtra("NEWWC",newWc);
                        startActivity(intent);

                        finish();

                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        //do nothing

                    }
                });

                alertDialog.show();
/*

                wcName = wcNameEt.getText().toString();
                wcDesc = wcDescEt.getText().toString();
                wcFloor = Integer.parseInt(wcFloorEt.getText().toString());

                //newWc = new WaterCloset(wcName, wcDesc, wcFloor,false, wcBitmap
                 //       ,R.drawable.ic_favorite_border_black_24dp);

                newWc = new WaterCloset(wcName, wcDesc, wcFloor, false, currentImagePath,R.drawable.ic_favorite_border_black_24dp);
                Intent intent = new Intent(NewWc.this, MainActivity.class);
                intent.putExtra("NEWWC",newWc);
                startActivity(intent);

                finish();
*/

            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == WRITE_PERMISSION_REQUEST){
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Cannot Take Picture", Toast.LENGTH_SHORT).show();
            }
            else{
                firstPicBtn.setVisibility(View.GONE);
                addPicBtn.setVisibility(View.VISIBLE);

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            firstPicBtn.setVisibility(View.GONE);
            addPicBtn.setVisibility(View.VISIBLE);

            File file = new File(currentImagePath);
            try {
                wcBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),Uri.fromFile(file));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(wcBitmap != null){
                wcImgIv.setImageBitmap(wcBitmap);
            }

            //works
                //wcBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                //wcImgIv.setImageBitmap(wcBitmap);

            //wcImgIv.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));

            //wcImgIv.setImageDrawable(Drawable.createFromPath(file.getAbsolutePath()));
            /*
            Glide.with(getBaseContext())
                    .load(file.getAbsolutePath())
                    .apply(new RequestOptions().override(wcImgIv.getWidth(),wcImgIv.getHeight()))
                    .into(wcImgIv);
            String path = getFilesDir().getAbsolutePath();
            Glide.with(getBaseContext())
                    .load(file.getAbsolutePath())
                    .apply(new RequestOptions().override(wcImgIv.getWidth(),200))
                    .into(path);
            */


        }
    }

    private File createImageFile() throws IOException{
        String timestamp = new SimpleDateFormat("HHmmss_ddMMyyyy").format(new Date());
        String imageFileName = "JPEG_" + timestamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,timestamp,storageDir);

        currentImagePath = image.getAbsolutePath();
        return image;
    }
}
