package com.ijbh.wcfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WcActivity extends AppCompatActivity {

    private String wcName, wcDesc, wcFloor;
    private WaterCloset wc;
    private ImageView imgIv, likeIv;
    private TextView wcNameTv, wcDescTv, cleanTv, wifiTv, paperTv, odourTv;//, wcFloorTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wc);

        imgIv = findViewById(R.id.wc_image_large);
        likeIv = findViewById(R.id.wc_like_large);
        wcNameTv = findViewById(R.id.wc_name_large);
        wcDescTv = findViewById(R.id.wc_desc_large);
        //wcFloorTv = findViewById(R.id.wc_current_floor);
        cleanTv = findViewById(R.id.clean_large);
        wifiTv = findViewById(R.id.wifi_large);
        paperTv = findViewById(R.id.paper_large);
        odourTv = findViewById(R.id.odour_large);
        imgIv = findViewById(R.id.wc_image_large);

        Intent intent = getIntent();
        wc = (WaterCloset) intent.getSerializableExtra("current_wc");
        //WaterCloset newWc = (WaterCloset) intent.getSerializableExtra("NEWWC");

        if(wc != null){
            //Toast.makeText(this, wc.getWcFloor()+" is current", Toast.LENGTH_SHORT).show();
            wcNameTv.setText(wc.getWcName());
            wcDescTv.setText(wc.getWcDescription());
            cleanTv.setText(wc.getInd_clean()+"");
            wifiTv.setText(wc.getInd_wifi()+"");
            paperTv.setText(wc.getInd_paper()+"");
            odourTv.setText(wc.getInd_odour()+"");

            //wcFloorTv.setText(wc.getWcFloor()+"");

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

            cleanTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(WcActivity.this, R.string.hint_clean_str, Toast.LENGTH_SHORT).show();
                }
            });
            wifiTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(WcActivity.this, R.string.hint_wifi_str, Toast.LENGTH_SHORT).show();
                }
            });
            paperTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(WcActivity.this, R.string.hint_paper_str, Toast.LENGTH_SHORT).show();
                }
            });
            odourTv.setOnClickListener(new View.OnClickListener() {
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
            alertDialog.setTitle(getString(R.string.dialog_edit_title_str));
            alertDialog.setMessage(getString(R.string.dialog_edit_msg));
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
                    Intent intent = new Intent(WcActivity.this, NewWc.class);
                    intent.putExtra("edit_wc", wc);
                    startActivity(intent);

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
