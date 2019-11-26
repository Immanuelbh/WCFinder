package com.ijbh.wcfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WcActivity extends AppCompatActivity {

    private String wcName, wcDesc, wcFloor;
    //private WaterCloset wc;
    private ImageView imgIv, likeIv, cleanIv, wifiIv, paperIv, odourIv;
    private TextView wcNameTv, wcDescTv, wcLocationTv, wcDateTv, cleanTv, wifiTv, paperTv, odourTv;
    private LinearLayout locationLl;
    private WaterCloset waterCloset;
    private int wcPosition;
    private boolean likeWC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wc);

        wcPosition = getIntent().getIntExtra("current_wc", 0);
        waterCloset = WaterClosetManager.getInstance(this).getWaterCloset(wcPosition);

        imgIv = findViewById(R.id.wc_image_large);
        likeIv = findViewById(R.id.wc_like_large);
        cleanIv = findViewById(R.id.wc_clean_icon);
        wifiIv = findViewById(R.id.wc_wifi_icon);
        paperIv = findViewById(R.id.wc_paper_icon);
        odourIv = findViewById(R.id.wc_odour_icon);

        wcNameTv = findViewById(R.id.wc_name_large);
        wcDescTv = findViewById(R.id.wc_desc_large);
        wcLocationTv = findViewById(R.id.wc_location_large);
        locationLl = findViewById(R.id.show_address);
        wcDateTv = findViewById(R.id.wc_date_large);
        cleanTv = findViewById(R.id.clean_large);
        wifiTv = findViewById(R.id.wifi_large);
        paperTv = findViewById(R.id.paper_large);
        odourTv = findViewById(R.id.odour_large);
        imgIv = findViewById(R.id.wc_image_large);


        //Intent intent = getIntent();
        //waterCloset = (WaterCloset) intent.getSerializableExtra("current_wc");
        //WaterCloset newWc = (WaterCloset) intent.getSerializableExtra("NEWWC");

        if(waterCloset != null){

            likeWC = waterCloset.isWcLike();

            wcNameTv.setText(waterCloset.getWcName());
            wcDescTv.setText(waterCloset.getWcDescription());
            wcLocationTv.setText(waterCloset.getWcLocation());
            wcDateTv.setText(waterCloset.getWcDate());
            cleanTv.setText(waterCloset.getInd_clean()+"");
            wifiTv.setText(waterCloset.getInd_wifi()+"");
            paperTv.setText(waterCloset.getInd_paper()+"");
            odourTv.setText(waterCloset.getInd_odour()+"");


            //wcFloorTv.setText(wc.getWcFloor()+"");

            if(likeWC){
                likeIv.setImageResource(R.drawable.ic_favorite_red_24dp);
            }
            else{
                likeIv.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }

            if(waterCloset.getWcResId()!= 0){ //existing image
                imgIv.setImageResource(waterCloset.getWcResId());
            }
            else{ // user created image
                Bitmap bitmap = BitmapFactory.decodeFile(waterCloset.getWcUriPath());
                imgIv.setImageBitmap(bitmap);
                //imgIv.setImageBitmap(wc.getWcBitmap());
            }

            likeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    likeWC = !likeWC;
                    waterCloset.setWcLike(likeWC);


                    if(likeWC){
                        likeIv.setImageResource(R.drawable.ic_favorite_red_24dp);
                    }
                    else{
                        likeIv.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    }

                }
            });

            locationLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = wcNameTv.getText().toString();
                    String address = wcLocationTv.getText().toString();

                    try{

                        Intent showLocationIntent = new Intent(Intent.ACTION_VIEW);
                        showLocationIntent.setData(Uri.parse("https://waze.com/ul?q="+ name + " " + address));
                        startActivity(showLocationIntent);
                    }catch (ActivityNotFoundException e) {
                        Intent noWazeIntent = new Intent(Intent.ACTION_VIEW);
                        noWazeIntent.setData(Uri.parse("market://id=com.waze"));
                        startActivity(noWazeIntent);

                    }

                }
            });

            double cleanRating = waterCloset.getInd_clean();
            double wifiRating = waterCloset.getInd_wifi();
            double paperRating = waterCloset.getInd_paper();
            double odourRating = waterCloset.getInd_odour();

            if(cleanRating > 4){
                cleanIv.setImageResource(R.drawable.ic_clean_green);
            }
            else if(cleanRating < 1.5){
                cleanIv.setImageResource(R.drawable.ic_clean_red);
            }

            if(wifiRating > 4){
                wifiIv.setImageResource(R.drawable.ic_wifi_green);
            }
            else if(wifiRating < 1.5){
                wifiIv.setImageResource(R.drawable.ic_wifi_red);
            }

            if(paperRating > 4){
                paperIv.setImageResource(R.drawable.ic_paper_green);
            }
            else if(paperRating < 1.5){
                paperIv.setImageResource(R.drawable.ic_paper_red);

            }

            if(odourRating > 4) {
                odourIv.setImageResource(R.drawable.ic_odour_green);
            }
            else if(odourRating < 1.5) {
                odourIv.setImageResource(R.drawable.ic_odour_red);
            }

            cleanIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(WcActivity.this, R.string.hint_clean_str, Toast.LENGTH_SHORT).show();
                }
            });
            wifiIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(WcActivity.this, R.string.hint_wifi_str, Toast.LENGTH_SHORT).show();
                }
            });
            paperIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(WcActivity.this, R.string.hint_paper_str, Toast.LENGTH_SHORT).show();
                }
            });
            odourIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(WcActivity.this, R.string.hint_odour_str, Toast.LENGTH_SHORT).show();
                }
            });


        }
        else{
            Toast.makeText(this, R.string.err_wc_load, Toast.LENGTH_SHORT).show();

        }

        //populate fields


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.edit_btn){
            //dialog box


            AlertDialog alertDialog = new AlertDialog.Builder(WcActivity.this).create();
            alertDialog.setTitle(getString(R.string.delete_wc_str));
            alertDialog.setMessage(getString(R.string.dialog_delete_msg));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yes_str), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int position) {

                  /*  wcName = wcNameEt.getText().toString();
                    wcDesc = wcDescEt.getText().toString();
                    //wcFloor = Integer.parseInt(wcFloorEt.getText().toString());
                    ind_clean = Integer.parseInt(cleanEt.getText().toString());
                    ind_wifi = Integer.parseInt(wifiEt.getText().toString());
                    ind_paper = Integer.parseInt(paperEt.getText().toString());
                    ind_odour = Integer.parseInt(odourEt.getText().toString());
*/
                    /*//newWc = new WaterCloset(wcName, wcDesc, wcFloor, wcLike, currentImagePath);
                    newWc = new WaterCloset(wcName, wcDesc, wcLike, currentImagePath, ind_clean, ind_wifi, ind_paper, ind_odour);
                    Intent intent = new Intent(NewWc.this, MainActivity.class);
                    intent.putExtra("NEWWC",newWc);
                    startActivity(intent);
*/
                    WaterClosetManager manager = WaterClosetManager.getInstance(WcActivity.this);
                    manager.removeWaterCloset(wcPosition);
                    manager.saveWaterClosets();
                    //WaterClosetAdapter adapter =

                    finish();

                    //Intent intent = new Intent(WcActivity.this, NewWc.class);
                    //intent.putExtra("edit_wc", wc);//not needed if used
                    //startActivity(intent);

                    //finish();

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
        return super.onOptionsItemSelected(item);
    }
}
