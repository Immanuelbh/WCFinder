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
import android.text.TextUtils;
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

    private ImageView wcImgIv;
    private File imgFile;
    private Button firstPicBtn;
    private Button addPicBtn;
    private WaterCloset newWc;
    private Bitmap wcBitmap = null;
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

                if(!(isEmpty(wcNameEt))){
                    if(!(isEmpty(wcDescEt))){
                        if(!(isEmpty(wcFloorEt))){
                            if(wcBitmap != null){

                                AlertDialog alertDialog = new AlertDialog.Builder(NewWc.this).create();
                                alertDialog.setTitle("Save new WC");
                                alertDialog.setMessage("Are you sure you want to save this WC?");
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int position) {

                                        wcName = wcNameEt.getText().toString();
                                        wcDesc = wcDescEt.getText().toString();
                                        wcFloor = Integer.parseInt(wcFloorEt.getText().toString());

                                        newWc = new WaterCloset(wcName, wcDesc, wcFloor, false, currentImagePath);
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
                            }
                            else{
                                Toast.makeText(NewWc.this, "Please add a picture of the WC", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            wcFloorEt.setError("Please enter WC floor");
                        }
                    }
                    else{
                        wcDescEt.setError("Please enter a description for the WC");
                    }
                }
                else{
                    wcNameEt.setError("Please enter WC name");
                }


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

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}
