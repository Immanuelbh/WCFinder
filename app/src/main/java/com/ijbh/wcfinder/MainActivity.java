package com.ijbh.wcfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final List<WaterCloset> wcs = new ArrayList<>();

        wcs.add(new WaterCloset("Hit", "Building 8", 1, false, R.drawable.wc_img_1, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("Hit", "Building 8", 1, false, R.drawable.wc_img_1, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("Hit", "Building 8", 1, false, R.drawable.wc_img_1, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("Hit", "Building 8", 1, false, R.drawable.wc_img_1, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("Hit", "Building 8", 1, false, R.drawable.wc_img_1, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("Hit", "Building 8", 1, false, R.drawable.wc_img_1, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("Hit", "Building 8", 1, false, R.drawable.wc_img_1, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("Hit", "Building 8", 1, false, R.drawable.wc_img_1, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("Hit", "Building 8", 1, false, R.drawable.wc_img_1, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("Hit", "Building 8", 1, false, R.drawable.wc_img_1, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("Hit", "Building 8", 1, false, R.drawable.wc_img_1, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("Hit", "Building 8", 1, false, R.drawable.wc_img_1, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("Hit", "Building 8", 1, false, R.drawable.wc_img_1, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("Hit", "Building 8", 1, false, R.drawable.wc_img_1, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("Hit", "Building 8", 1, false, R.drawable.wc_img_1, R.drawable.ic_favorite_border_black_24dp));
        wcs.add(new WaterCloset("Hit", "Building 8", 1, false, R.drawable.wc_img_1, R.drawable.ic_favorite_border_black_24dp));

        final WaterClosetAdapter waterClosetAdapter = new WaterClosetAdapter(wcs);

        waterClosetAdapter.setListener(new WaterClosetAdapter.WaterClosetListener() {
            @Override
            public void onWcClicked(int position, View view) {

                //Toast.makeText(MainActivity.this, wcs.get(position).getWcName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, WcActivity.class);
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
}
