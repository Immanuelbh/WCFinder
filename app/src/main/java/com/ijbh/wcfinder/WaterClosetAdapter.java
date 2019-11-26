package com.ijbh.wcfinder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


public class WaterClosetAdapter extends RecyclerView.Adapter<WaterClosetAdapter.WCViewHolder> {

    private List<WaterCloset> wcs;
    WaterClosetListener listener;
    double cleanRating =0.0, wifiRating=0.0, paperRating=0.0, odourRating=0.0;

    interface WaterClosetListener{
        boolean onItemMove(int fromPosition, int toPosition);
        void onWcClicked(int position, View view);
        void onFavClick(int position);
    }

    public void setListener(WaterClosetListener listener){
        this.listener = listener;
    }

    public WaterClosetAdapter(List<WaterCloset> wcs) {
        this.wcs = wcs;
    }



    public class WCViewHolder extends RecyclerView.ViewHolder {

        ImageView wcCardImgIv, wcCardLikeIv, cardCleanIv, cardWifiIv, cardPaperIv, cardOdourIv;
        TextView wcCardNameTv, wcCardDescTv;
        RatingBar rBar;

        public WCViewHolder(@NonNull View itemView) {
            super(itemView);

            wcCardNameTv = itemView.findViewById(R.id.wc_card_name);
            wcCardDescTv = itemView.findViewById(R.id.wc_card_desc);
            wcCardImgIv = itemView.findViewById(R.id.wc_card_img);
            wcCardLikeIv = itemView.findViewById(R.id.wc_card_like);
            cardCleanIv = itemView.findViewById(R.id.card_clean);
            cardWifiIv = itemView.findViewById(R.id.card_wifi);
            cardPaperIv = itemView.findViewById(R.id.card_paper);
            cardOdourIv = itemView.findViewById(R.id.card_odour);
            rBar = itemView.findViewById(R.id.ratingBar);

            cardCleanIv.setImageResource(R.drawable.ic_clean);
            cardWifiIv.setImageResource(R.drawable.ic_wifi_black);
            cardPaperIv.setImageResource(R.drawable.ic_paper);
            cardOdourIv.setImageResource(R.drawable.ic_odour);

            //cardWifiIv.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.black));
            //cardPaperIv.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.black));
            //cardOdourIv.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.black));

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
        cleanRating = waterCloset.getInd_clean();
        wifiRating = waterCloset.getInd_wifi();
        paperRating = waterCloset.getInd_paper();
        odourRating = waterCloset.getInd_odour();


        if(waterCloset.getWcUriPath() == null){
            holder.wcCardImgIv.setImageResource(waterCloset.getWcResId());
        }
        else{
            Glide.with(holder.itemView.getContext()).load(waterCloset.getWcUriPath()).thumbnail(0.01f).into(holder.wcCardImgIv);

        }

        holder.wcCardNameTv.setText(waterCloset.getWcName());
        holder.wcCardDescTv.setText(waterCloset.getWcDescription());
        holder.rBar.setRating((float) waterCloset.getOverallScore());

        if(waterCloset.isWcLike()){
            holder.wcCardLikeIv.setImageResource(R.drawable.ic_favorite_red_24dp);
        }
        else{
            holder.wcCardLikeIv.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }

        //holder.currentIv.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(), R.color.red));


        if(cleanRating > 4.0) {
            holder.cardCleanIv.setImageResource(R.drawable.ic_clean_green);
        }else if(cleanRating < 1.5) {
            holder.cardCleanIv.setImageResource(R.drawable.ic_clean_red);
        }else{
            holder.cardCleanIv.setImageResource(R.drawable.ic_clean);
        }

        if(wifiRating > 4.0) {
            holder.cardWifiIv.setImageResource(R.drawable.ic_wifi_green);
        }
        else if(wifiRating < 1.5) {
            holder.cardWifiIv.setImageResource(R.drawable.ic_wifi_red);
        }else{
            holder.cardWifiIv.setImageResource(R.drawable.ic_wifi_black);
        }

        if(paperRating > 4.0) {
            holder.cardPaperIv.setImageResource(R.drawable.ic_paper_green);
        }
        else if(paperRating < 1.5) {
            holder.cardPaperIv.setImageResource(R.drawable.ic_paper_red);
        }else{
            holder.cardPaperIv.setImageResource(R.drawable.ic_paper);
        }

        if(odourRating > 4.0) {
            holder.cardOdourIv.setImageResource(R.drawable.ic_odour_green);
        }
        else if(odourRating < 1.5) {
            holder.cardOdourIv.setImageResource(R.drawable.ic_odour_red);
        }else{
            holder.cardOdourIv.setImageResource(R.drawable.ic_odour);
        }


    }


    @Override
    public int getItemCount() {
        return wcs.size();
    }
}
