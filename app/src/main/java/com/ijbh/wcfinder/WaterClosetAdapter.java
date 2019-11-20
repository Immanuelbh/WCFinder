package com.ijbh.wcfinder;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class WaterClosetAdapter extends RecyclerView.Adapter<WaterClosetAdapter.WCViewHolder> {

    List<WaterCloset> wcs;
    WaterClosetListener listener;

    interface WaterClosetListener{
        void onWcClicked(int position, View view);
        void onWcLongClicked(int position, View view);
    }

    public void setListener(WaterClosetListener listener){
        this.listener = listener;
    }

    public WaterClosetAdapter(List<WaterCloset> wcs) {
        this.wcs = wcs;
    }

    public class WCViewHolder extends RecyclerView.ViewHolder {

        ImageView wcImgIv, wcLikeIv;
        TextView wcNameTv, wcDescTv, wcFloorTv;

        public WCViewHolder(@NonNull View itemView) {
            super(itemView);

            wcNameTv = itemView.findViewById(R.id.wc_name);
            wcDescTv = itemView.findViewById(R.id.wc_desc);
            wcFloorTv = itemView.findViewById(R.id.wc_floor);
            wcImgIv = itemView.findViewById(R.id.wc_image);
            wcLikeIv = itemView.findViewById(R.id.wc_like);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onWcClicked(getAdapterPosition(), v);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(listener != null){
                        listener.onWcLongClicked(getAdapterPosition(), v);
                    }
                    return false;
                }
            });
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
        try{
            holder.wcImgIv.setImageResource(waterCloset.getWcResId());
        }catch (NullPointerException npe){
            //holder.wcImgIv.setImageBitmap(waterCloset.getWcBitmap());
            try {
                FileInputStream fis = holder.wcImgIv.getContext().openFileInput("new_wc");
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


        holder.wcNameTv.setText(waterCloset.getWcName());
        holder.wcDescTv.setText(waterCloset.getWcDescription());
        holder.wcFloorTv.setText(waterCloset.getWcFloor()+"");
        holder.wcLikeIv.setImageResource(waterCloset.getWcLikeId());

    }

    @Override
    public int getItemCount() {
        return wcs.size();
    }
}
