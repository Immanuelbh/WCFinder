package com.ijbh.wcfinder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collections;
import java.util.List;


public class WaterClosetAdapter extends RecyclerView.Adapter<WaterClosetAdapter.WCViewHolder> {

    private List<WaterCloset> wcs;
    WaterClosetListener listener;

    interface WaterClosetListener{
        boolean onItemMove(int fromPosition, int toPosition);
        void onWcClicked(int position, View view);
        void onFavClick(int position);
        //void onWcLongClicked(int position, View view);
    }

    public void setListener(WaterClosetListener listener){
        this.listener = listener;
    }

    public WaterClosetAdapter(List<WaterCloset> wcs) {
        this.wcs = wcs;
    }



    public class WCViewHolder extends RecyclerView.ViewHolder {

        ImageView wcCardImgIv, wcCardLikeIv;
        TextView wcCardNameTv, wcCardDescTv, wcCardFloorTv;

        public WCViewHolder(@NonNull View itemView) {
            super(itemView);

            wcCardNameTv = itemView.findViewById(R.id.wc_card_name);
            wcCardDescTv = itemView.findViewById(R.id.wc_card_desc);
            wcCardFloorTv = itemView.findViewById(R.id.wc_card_floor);
            wcCardImgIv = itemView.findViewById(R.id.wc_card_img);
            wcCardLikeIv = itemView.findViewById(R.id.wc_card_like);

            wcCardLikeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onFavClick(getAdapterPosition());
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onWcClicked(getAdapterPosition(), v);
                    }
                }
            });



/*
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(listener != null){
                        listener.onWcLongClicked(getAdapterPosition(), v);
                    }
                    return false;
                }
            });*/
        }
    }

    @NonNull
    @Override
    public WCViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wc_card_layout,parent,false);
        WCViewHolder wcViewHolder = new WCViewHolder(view);

        return wcViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WCViewHolder holder, int position) {
        WaterCloset waterCloset = wcs.get(position);

        //TODO check after closing and reopening the app, how to restore correctly
        if(waterCloset.getWcUriPath() == null){
            holder.wcCardImgIv.setImageResource(waterCloset.getWcResId());

        }
        else{

            //holder.wcCardImgIv.setImageBitmap(waterCloset.getWcBitmap());

            Glide.with(holder.itemView.getContext()).load(waterCloset.getWcUriPath()).thumbnail(0.1f).into(holder.wcCardImgIv);

            //works but very slow (full image)
            //Bitmap bitmap = BitmapFactory.decodeFile(waterCloset.getWcUriPath());
            //holder.wcCardImgIv.setImageBitmap(bitmap);

            //holder.wcImgIv.setImageBitmap(waterCloset.getWcIconBitmap());
        }

        //works
/*
        try{
            holder.wcImgIv.setImageResource(waterCloset.getWcResId());
        }catch (NullPointerException npe){
            //holder.wcImgIv.setImageBitmap(waterCloset.getWcBitmap());
            try {
                FileInputStream fis = holder.itemView.getContext().openFileInput("new_wc");
                ObjectInputStream ois = new ObjectInputStream(fis);
                waterCloset = (WaterCloset)ois.readObject();
                ois.close();

                holder.wcImgIv.setImageBitmap(waterCloset.getWcBitmap());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
*/


        holder.wcCardNameTv.setText(waterCloset.getWcName());
        holder.wcCardDescTv.setText(waterCloset.getWcDescription());
        holder.wcCardFloorTv.setText(waterCloset.getWcFloor()+"");

        if(waterCloset.isWcLike()){
            holder.wcCardLikeIv.setImageResource(R.drawable.ic_favorite_red_24dp);
        }
        else{
            holder.wcCardLikeIv.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }

    }

    @Override
    public int getItemCount() {
        return wcs.size();
    }
}
