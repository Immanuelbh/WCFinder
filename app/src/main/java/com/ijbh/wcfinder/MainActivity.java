package com.ijbh.wcfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends Activity {

    WaterClosetManager manager;
    WaterClosetAdapter waterClosetAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recycler);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        manager = WaterClosetManager.getInstance(this);
        waterClosetAdapter = new WaterClosetAdapter(manager.getWcs());


        ////////////////////////
        /*
        ArrayList<WaterCloset> sampleWcs = new ArrayList<>();
        sampleWcs.add(new WaterCloset("Hit", "Building 8", true, R.drawable.wc_img_1, 2, 2, 2, 2));
        sampleWcs.add(new WaterCloset("Microsoft", "At the end of the hall", true, R.drawable.wc_img_2, 1, 2, 3, 4));
        sampleWcs.add(new WaterCloset("Apple", "Building 11", true, R.drawable.wc_img_1, 3,4,5,3));
        */

        ////////////////////////

        addSamples();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewWc.class);
                startActivity(intent);
            }
        });

        waterClosetAdapter.setListener(new WaterClosetAdapter.WaterClosetListener() {

            @Override
            public void onWcClicked(int position, View view) {

                Intent intent = new Intent(MainActivity.this, WcActivity.class);
                intent.putExtra("current_wc", position);
                //intent.putExtra("adapter", waterClosetAdapter);
                startActivity(intent);
            }

            @Override
            public void onFavClick(int position) {
                boolean currentLike = manager.getWaterCloset(position).isWcLike();

                manager.getWaterCloset(position).setWcLike(!currentLike);

                if(!currentLike){
                    Toast.makeText(MainActivity.this, manager.getWaterCloset(position).getWcName() + " " + getString(R.string.fav_msg), Toast.LENGTH_SHORT).show();
                }


                waterClosetAdapter.notifyItemChanged(position);
            }

            @Override
            public boolean onItemMove(int fromPosition, int toPosition) {
                //Toast.makeText(MainActivity.this, R.string.drag_msg, Toast.LENGTH_SHORT).show();

                if(fromPosition < toPosition){
                    for(int i = fromPosition; i < toPosition; i++){
                        Collections.swap(manager.getWcs(), i, i+1);
                    }
                }
                else{
                    for(int i = fromPosition; i > toPosition; i--){
                        Collections.swap(manager.getWcs(), i, i-1);
                    }
                }
                waterClosetAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }
        });

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override //not sure if needed
            public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                        @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                waterClosetAdapter.listener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());

                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final RecyclerView.ViewHolder temp = viewHolder;

                /*manager.getWcs().remove(temp.getAdapterPosition());
                waterClosetAdapter.notifyItemRemoved(temp.getAdapterPosition());
*/
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle(getString(R.string.delete_wc_str));
                alertDialog.setMessage(getString(R.string.delete_wc_msg_1_str) + " " + manager.getWaterCloset(viewHolder.getAdapterPosition()).getWcName() + " " + getString(R.string.delete_wc_msg_2_str));
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yes_str), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        manager.removeWaterCloset(temp.getAdapterPosition());
                        //manager.getWcs().remove(temp.getAdapterPosition());
                        //wcs.remove(temp.getAdapterPosition());
                        waterClosetAdapter.notifyItemRemoved(temp.getAdapterPosition());
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.no_str), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        waterClosetAdapter.notifyDataSetChanged();

                    }
                });

                alertDialog.show();


            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(waterClosetAdapter);

    }


    private void addSamples() {

        manager.addWaterCloset(new WaterCloset(getString(R.string.sample1_name), getString(R.string.sample1_desc), getString(R.string.sample1_location), "1/1/19", true, R.drawable.sample_img_1, 2, 2, 5, 5));
        manager.addWaterCloset(new WaterCloset(getString(R.string.sample3_name), getString(R.string.sample3_desc), getString(R.string.sample3_location), "8/8/18", false, R.drawable.sample_img_2, 1,4,5,1));
        manager.addWaterCloset(new WaterCloset(getString(R.string.sample2_name), getString(R.string.sample2_desc),getString(R.string.sample2_location), "2/3/19",  true, R.drawable.sample_img_3, 1, 5, 3, 4));

    }

    private void removeSamples() {
        //manager.removeWaterCloset();

    }

/*

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for(int i = 0; i < manager.getWcs().size(); i++){
            manager.removeWaterCloset(i);
        }
        //manager.getWcs().clear();
        waterClosetAdapter.notifyDataSetChanged();

    }

*/


    @Override
    protected void onResume() {
        super.onResume();
        //manager.saveWaterClosets();
        waterClosetAdapter.notifyDataSetChanged();

    }


}
