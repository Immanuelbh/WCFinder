package com.ijbh.wcfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class MainActivity extends AppCompatActivity {

    private static final String KEY_RECYCLER_STATE = "recycler_state";
    ArrayList<WaterCloset> wcs;
    WaterClosetAdapter waterClosetAdapter;

    RecyclerView recyclerView;
    private Bundle bundleRecyclerViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recycler);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        wcs = new ArrayList<>();

        //TODO change heart icon to boolean value
        wcs.add(new WaterCloset("Hit", "Building 8", 1, true, R.drawable.wc_img_1));
        wcs.add(new WaterCloset("Office", "Right at the entrence", 2, false, R.drawable.wc_img_2));
        wcs.add(new WaterCloset("Microsoft", "At the end of the hall", 5, true, R.drawable.wc_img_2));
        wcs.add(new WaterCloset("Apple", "Building 11", 9, true, R.drawable.wc_img_1));
        wcs.add(new WaterCloset("VMWare", "Building 20", 11, false, R.drawable.wc_img_2));
        wcs.add(new WaterCloset("Google", "Building 5", 4, false, R.drawable.wc_img_2));
        wcs.add(new WaterCloset("Hit", "Building 8", 2, false, R.drawable.wc_img_1));
        wcs.add(new WaterCloset("Office", "Right at the entrence", 2, false, R.drawable.wc_img_1));
        wcs.add(new WaterCloset("Microsoft", "At the end of the hall", 2, false, R.drawable.wc_img_2));
        wcs.add(new WaterCloset("Apple", "Building 11", 6, false, R.drawable.wc_img_1));
        wcs.add(new WaterCloset("VMWare", "Building 20", 3, true, R.drawable.wc_img_2));
        wcs.add(new WaterCloset("Google", "Building 5", 9, false, R.drawable.wc_img_1));

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
            public void onFavClick(int position) {
                boolean currentLike = wcs.get(position).isWcLike();

                wcs.get(position).setWcLike(!currentLike);

                waterClosetAdapter.notifyItemChanged(position);
            }
/*

            @Override
            public void onWcLongClicked(int position, View view) {
                wcs.remove(position);
                waterClosetAdapter.notifyItemRemoved(position);
            }


*/

            @Override
            public boolean onItemMove(int fromPosition, int toPosition) {
                if(fromPosition < toPosition){
                    for(int i = fromPosition; i < toPosition; i++){
                        Collections.swap(wcs, i, i+1);
                    }
                }
                else{
                    for(int i = fromPosition; i > toPosition; i--){
                        Collections.swap(wcs, i, i-1);
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


                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle(getString(R.string.delete_wc_str));
                alertDialog.setMessage(getString(R.string.delete_wc_msg_1_str)+wcs.get(viewHolder.getAdapterPosition()).getWcName()+ getString(R.string.delete_wc_msg_2_str));
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yes_str), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        wcs.remove(temp.getAdapterPosition());
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


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);


        WaterCloset newWc = (WaterCloset) intent.getSerializableExtra("NEWWC");
        //wcs.add(newWc); //can't use because of bitmap
        //Toast.makeText(this, newWc.getWcName()+"is the new WC", Toast.LENGTH_SHORT).show();

        try {
            FileOutputStream fos = openFileOutput("new_wc", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(newWc);
            oos.close();

            wcs.add(newWc);


        } catch (IOException e) {
            e.printStackTrace();
        }

        waterClosetAdapter.notifyDataSetChanged();
        //TODO update the list.
    }


    @Override
    protected void onPause() {
        super.onPause();
        bundleRecyclerViewState = new Bundle();
        try{
            Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
            bundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE,listState);

        }catch (NullPointerException npe){
            Toast.makeText(this, "Error loading listState", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        super.onPause();
        bundleRecyclerViewState = new Bundle();
        try{
            Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
            bundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE,listState);

        }catch (NullPointerException npe){
            Toast.makeText(this, "Error loading listState", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (bundleRecyclerViewState != null) {
            Parcelable listState = bundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            Objects.requireNonNull(recyclerView.getLayoutManager()).onRestoreInstanceState(listState);
            //TODO check if this is correct
        }
    }
}
