package com.ijbh.wcfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WcActivity extends AppCompatActivity {

    private String wcName, wcDesc, wcFloor;
    private WaterCloset wc;
    private ImageView imgIv, likeIv;
    private TextView wcNameTv, wcDescTv, wcFloorTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wc);

        imgIv = findViewById(R.id.wc_image_large);
        likeIv = findViewById(R.id.wc_like_large);
        wcNameTv = findViewById(R.id.wc_name_large);
        wcDescTv = findViewById(R.id.wc_desc_large);
        wcFloorTv = findViewById(R.id.wc_current_floor);
        imgIv = findViewById(R.id.wc_image_large);

        Intent intent = getIntent();
        wc = (WaterCloset) intent.getSerializableExtra("current_wc");
        //WaterCloset newWc = (WaterCloset) intent.getSerializableExtra("NEWWC");

        if(wc != null){
            //Toast.makeText(this, wc.getWcFloor()+" is current", Toast.LENGTH_SHORT).show();
            wcNameTv.setText(wc.getWcName());
            wcDescTv.setText(wc.getWcDescription());
            wcFloorTv.setText(wc.getWcFloor()+"");

            if(wc.isWcLike()){
                likeIv.setImageResource(R.drawable.ic_favorite_red_24dp);
            }
            else{
                likeIv.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }

            if(wc.getWcResId()!= 0){ //existing image
                imgIv.setImageResource(wc.getWcResId());
            }
            else{ // user created image
                Bitmap bitmap = BitmapFactory.decodeFile(wc.getWcUriPath());
                imgIv.setImageBitmap(bitmap);
                //imgIv.setImageBitmap(wc.getWcBitmap());
            }
        }
        else{
            Toast.makeText(this, R.string.err_wc_load, Toast.LENGTH_SHORT).show();

        }

        //populate fields


    }
}
