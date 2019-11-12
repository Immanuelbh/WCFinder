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

import java.util.List;

public class WaterClosetAdapter extends RecyclerView.Adapter<WaterClosetAdapter.WCViewHolder> {

    List<WaterCloset> wcs;

    public WaterClosetAdapter(List<WaterCloset> wcs) {
        this.wcs = wcs;
    }

    public class WCViewHolder extends RecyclerView.ViewHolder {

        ImageView wcImg, wcLike;
        TextView wcNameTv, wcDescTv, wcFloorTv;



        public WCViewHolder(@NonNull View itemView) {
            super(itemView);

            wcNameTv = itemView.findViewById(R.id.wc_name);
            wcDescTv = itemView.findViewById(R.id.wc_desc);
            wcFloorTv = itemView.findViewById(R.id.wc_floor);
            wcImg = itemView.findViewById(R.id.wc_image);
            wcLike = itemView.findViewById(R.id.wc_like);

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
        holder.wcImg.setImageResource(waterCloset.getWcResId());
        holder.wcNameTv.setText(waterCloset.getWcName());
        holder.wcDescTv.setText(waterCloset.getWcDescription());
        holder.wcFloorTv.setText(waterCloset.getWcFloor()+"");
        holder.wcLike.setImageResource(waterCloset.getWcLikeId());

    }

    @Override
    public int getItemCount() {
        return wcs.size();
    }
}
