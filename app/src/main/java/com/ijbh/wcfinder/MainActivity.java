package com.ijbh.wcfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<WaterCloset> wcs;
    WaterClosetAdapter waterClosetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        RecyclerView recyclerView = findViewById(R.id.recycler);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        wcs = new ArrayList<WaterCloset>();

        //TODO change heart icon to boolean value
        wcs.add(new WaterCloset("Hit", "Building 8", 1, false, R.drawable.wc_img_1, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("Office", "Right at the entrence", 2, false, R.drawable.wc_img_2, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("Microsoft", "At the end of the hall", 5, false, R.drawable.wc_img_2, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("Apple", "Building 11", 9, false, R.drawable.wc_img_1, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("VMWare", "Building 20", 11, false, R.drawable.wc_img_2, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("Google", "Building 5", 4, false, R.drawable.wc_img_2, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("Hit", "Building 8", 2, false, R.drawable.wc_img_1, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("Office", "Right at the entrence", 2, false, R.drawable.wc_img_1, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("Microsoft", "At the end of the hall", 2, false, R.drawable.wc_img_2, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("Apple", "Building 11", 6, false, R.drawable.wc_img_1, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("VMWare", "Building 20", 3, false, R.drawable.wc_img_2, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("Google", "Building 5", 9, false, R.drawable.wc_img_1, R.drawable.ic_favorite_border_black_24dp));

        waterClosetAdapter = new WaterClosetAdapter(wcs);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewWc.class);

                startActivity(intent);//TODO convert to activity for result
            }
        });

        waterClosetAdapter.setListener(new WaterClosetAdapter.WaterClosetListener() {
            @Override
            public void onWcClicked(int position, View view) {

                Intent intent = new Intent(MainActivity.this, WcActivity.class);
                intent.putExtra("current_wc", wcs.get(position));
                startActivity(intent);
            }

            @Override
            public void onWcLongClicked(int position, View view) {
                wcs.remove(position);
                waterClosetAdapter.notifyItemRemoved(position);
            }
        });

        recyclerView.setAdapter(waterClosetAdapter);

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        WaterCloset newWc = (WaterCloset) intent.getSerializableExtra("NEWWC");
        //wcs.add(newWc); //can't use because of bitmap
        Toast.makeText(this, newWc.getWcName()+"is the new WC", Toast.LENGTH_SHORT).show();

        try {
            FileOutputStream fos = openFileOutput("new_wc", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(newWc);
            oos.close();

            wcs.add(newWc);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        waterClosetAdapter.notifyDataSetChanged();
        //TODO update the list.
    }
}
