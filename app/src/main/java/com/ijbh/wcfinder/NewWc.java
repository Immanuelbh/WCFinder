package com.ijbh.wcfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.DatePickerDialog;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewWc extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1;
    private static final int WRITE_PERMISSION_REQUEST = 1;

    private File imgFile;
    private WaterCloset newWc;
    private Button firstPicBtn, addPicBtn;
    private ImageView wcImgIv, wcLikeIv;
    private Bitmap wcBitmap = null;
    private String wcName, wcDesc, wcLocation, wcDate;
    private String currentImagePath;
    private boolean wcLike = false, dateSet = false;
    private double ind_clean = 0.0, ind_wifi = 0.0, ind_paper = 0.0, ind_odour = 0.0;
    EditText wcNameEt, wcDescEt, wcLocationEt, cleanEt, wifiEt, paperEt, odourEt;
    TextView wcDateTv;
    LinearLayout locationLl, dateLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_wc);

        wcImgIv = findViewById(R.id.wc_new_img);
        wcLikeIv = findViewById(R.id.wc_new_like);

        firstPicBtn = findViewById(R.id.first_pic_btn);
        addPicBtn = findViewById(R.id.add_pic_btn);

        wcNameEt = findViewById(R.id.wc_new_name);
        wcDescEt = findViewById(R.id.wc_new_desc);
        wcLocationEt = findViewById(R.id.wc_location);
        wcDateTv = findViewById(R.id.wc_date);
        cleanEt = findViewById(R.id.new_clean);
        wifiEt = findViewById(R.id.new_wifi);
        paperEt = findViewById(R.id.new_paper);
        odourEt = findViewById(R.id.new_odour);

        locationLl = findViewById(R.id.add_address);
        dateLl = findViewById(R.id.add_date);

        firstPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    int hasWritePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

                    if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(NewWc.this, R.string.no_permissions_str, Toast.LENGTH_SHORT).show();
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_REQUEST);
                    } else {

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        try {
                            imgFile = new File(String.valueOf(createImageFile()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (imgFile != null) {
                            Uri imgUri = FileProvider.getUriForFile(NewWc.this, getPackageName() + ".provider", imgFile);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                            startActivityForResult(intent, CAMERA_REQUEST);
                        }

                    }
                } else {
                    Toast.makeText(NewWc.this, R.string.low_sdk_str, Toast.LENGTH_SHORT).show();
                }

            }
        });

        wcLikeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wcLike) {
                    wcLikeIv.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    wcLike = false;
                } else {
                    wcLikeIv.setImageResource(R.drawable.ic_favorite_red_24dp);
                    wcLike = true;
                }
            }
        });

        locationLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dateLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(NewWc.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        wcDateTv.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                        dateSet = true;
                    }
                }, year, month,day);
                dpd.show();
            }
        });


    }

    private boolean fieldsAreValid() {
        //fields not empty
        /*if(isEmpty(wcNameEt)||){
            return false;
        }*/
        if((isEmpty(wcNameEt) || isEmpty(wcDescEt) || isEmpty(cleanEt) || isEmpty(wifiEt) || isEmpty(paperEt) || isEmpty(odourEt))){
            return false;
        }

        //values are between 0-5
        if(!validValue(cleanEt) || !validValue(wifiEt) || !validValue(paperEt) || !validValue(odourEt)){
            return false;
        }
        return true;
    }

    private void sendError() {
        if(isEmpty(wcNameEt)){
            wcNameEt.setError(getString(R.string.err_add_name));
            Toast.makeText(this, R.string.err_add_name, Toast.LENGTH_SHORT).show();
        }
        else if(isEmpty(wcDescEt)){
            wcDescEt.setError(getString(R.string.err_add_desc));
            Toast.makeText(this, R.string.err_add_desc, Toast.LENGTH_SHORT).show();

        }
        else if(isEmpty(wcLocationEt)){
            wcLocationEt.setError(getString(R.string.err_add_location));
            Toast.makeText(this, R.string.err_add_location, Toast.LENGTH_SHORT).show();

        }
        else if(!dateSet){
            wcDateTv.setError(getString(R.string.err_add_date));
            Toast.makeText(this, R.string.err_add_date, Toast.LENGTH_SHORT).show();

        }
        else if(isEmpty(cleanEt)){
            cleanEt.setError(getString(R.string.err_clean_str));
            Toast.makeText(this, R.string.err_clean_str, Toast.LENGTH_SHORT).show();

        }
        else if(isEmpty(wifiEt)){
            wifiEt.setError(getString(R.string.err_wifi_str));
            Toast.makeText(this, R.string.err_wifi_str, Toast.LENGTH_SHORT).show();

        }
        else if (isEmpty(paperEt)){
            paperEt.setError(getString(R.string.err_paper_str));

        }
        else if(isEmpty(odourEt)){
            odourEt.setError(getString(R.string.err_odour_str));
            Toast.makeText(this, R.string.err_odour_str, Toast.LENGTH_SHORT).show();

        }
        else{
            if(!validValue(cleanEt) || !validValue(wifiEt) || !validValue(paperEt) || !validValue(odourEt)){
                Toast.makeText(this, R.string.err_value_range_str, Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == WRITE_PERMISSION_REQUEST){
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, getString(R.string.err_cant_take_pic), Toast.LENGTH_SHORT).show();
            }
            else{

                firstPicBtn.setVisibility(View.GONE);
                //addPicBtn.setVisibility(View.VISIBLE);

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            firstPicBtn.setVisibility(View.GONE);
            //addPicBtn.setVisibility(View.VISIBLE);

            //thumbnail
            //Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            //imageView.setImageBitmap(bitmap);

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
    private boolean validValue(EditText etText) {
        if(isEmpty(etText)){
            return false;
        }
        else{
            double value = Double.parseDouble(etText.getText().toString());
            if(value >=0 && value <= 5){
                return true;
            }
        }

        return false;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.save_btn){
            if(fieldsAreValid()){
                if(wcBitmap != null){
                    AlertDialog alertDialog = new AlertDialog.Builder(NewWc.this).create();
                    alertDialog.setTitle(getString(R.string.dialog_new_save_str));
                    alertDialog.setMessage(getString(R.string.dialog_msg_new_wc_str));
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yes_str), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {

                            wcName = wcNameEt.getText().toString();
                            wcDesc = wcDescEt.getText().toString();
                            ind_clean = Integer.parseInt(cleanEt.getText().toString());
                            ind_wifi = Integer.parseInt(wifiEt.getText().toString());
                            ind_paper = Integer.parseInt(paperEt.getText().toString());
                            ind_odour = Integer.parseInt(odourEt.getText().toString());
                            wcLocation = wcLocationEt.getText().toString();
                            wcDate = wcDateTv.getText().toString();

                            newWc = new WaterCloset(wcName, wcDesc, wcLocation, wcDate, wcLike, currentImagePath, ind_clean, ind_wifi, ind_paper, ind_odour);

                            WaterClosetManager manager = WaterClosetManager.getInstance(NewWc.this);
                            manager.addWaterCloset(newWc);

                            //Intent intent = new Intent(NewWc.this, MainActivity.class);
                            //intent.putExtra("NEWWC",newWc);
                            //startActivity(intent);

                            finish();

                        }
                    });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.no_str), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            //do nothing

                        }
                    });

                    alertDialog.show();
                }
                else{
                    Toast.makeText(NewWc.this, getString(R.string.err_add_pic_str), Toast.LENGTH_SHORT).show();
                }
            }
            else{
                sendError();
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
