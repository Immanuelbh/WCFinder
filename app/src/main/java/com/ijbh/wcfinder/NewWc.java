package com.ijbh.wcfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class NewWc extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1;
    private static final int WRITE_PERMISSION_REQUEST = 1;

    ImageView wcImgIv;
    File file;
    Button firstPicBtn;
    Button addPicBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_wc);

        wcImgIv = findViewById(R.id.wc_new_img);

        firstPicBtn = findViewById(R.id.first_pic_btn);
        addPicBtn = findViewById(R.id.add_pic_btn);

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


                        Uri fileUri = FileProvider.getUriForFile(NewWc.this, "com.ijbh.wcfinder.provider", file);

                        //file = new File(getExternalFilesDir(null), "DemoFile.jpg"); // different function than example
                        //Uri fileUri = Uri.fromFile(file);

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_REQUEST);

                        //maybe only after return successful
                        firstPicBtn.setVisibility(View.GONE);
                        addPicBtn.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    Toast.makeText(NewWc.this, "SDK Lower than 24", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == WRITE_PERMISSION_REQUEST){
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Cannot Take Picture", Toast.LENGTH_SHORT).show();
            }
            else{
                /*
                file = new File(getExternalFilesDir(null), "DemoFile.jpg"); // different function than example
                Uri fileUri = Uri.fromFile(file);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, CAMERA_REQUEST);
*/
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            wcImgIv.setImageDrawable(Drawable.createFromPath(file.getAbsolutePath()));
        }
    }
}
