package com.ijbh.wcfinder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.IOException;
import java.io.Serializable;

public class WaterCloset implements Serializable {
    private String wcName, wcDescription;
    private int wcFloor, wcResId, wcLikeId;
    private boolean wcLike;
    transient private Bitmap wcBitmap;

    public WaterCloset(String wcName, String wcDescription, int wcFloor, boolean wcLike, int wcResId, int wcLikeId) {
        this.wcName = wcName;
        this.wcDescription = wcDescription;
        this.wcFloor = wcFloor;
        this.wcResId = wcResId;
        this.wcLikeId = wcLikeId;
        this.wcLike = wcLike;
    }
    public WaterCloset(String wcName, String wcDescription, int wcFloor, boolean wcLike, Bitmap wcBitmap, int wcLikeId) {
        this.wcName = wcName;
        this.wcDescription = wcDescription;
        this.wcFloor = wcFloor;
        this.wcBitmap = wcBitmap;
        this.wcLikeId = wcLikeId;
        this.wcLike = wcLike;
    }

    public WaterCloset(String wcName, String wcDescription, int wcFloor, boolean wcLike, int wcResId) {
        this.wcName = wcName;
        this.wcDescription = wcDescription;
        this.wcFloor = wcFloor;
        this.wcLike = wcLike;
        this.wcResId = wcResId;
    }

    public WaterCloset(Parcel in){
        this.wcName = in.readString();
        this.wcDescription = in.readString();
        this.wcFloor = in.readInt();
        this.wcResId = in.readInt();
        this.wcLikeId = in.readInt();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.wcLike = in.readBoolean();
        }
    }

    public String getWcName() {
        return wcName;
    }

    public void setWcName(String wcName) {
        this.wcName = wcName;
    }

    public String getWcDescription() {
        return wcDescription;
    }

    public void setWcDescription(String wcDescription) {
        this.wcDescription = wcDescription;
    }

    public int getWcFloor() {
        return wcFloor;
    }

    public void setWcFloor(int wcFloor) {
        this.wcFloor = wcFloor;
    }

    public boolean isWcLike() {
        return wcLike;
    }

    public void setWcLike(boolean wcLike) {
        this.wcLike = wcLike;
    }

    public int getWcResId() {
        return wcResId;
    }

    public void setWcResId(int wcResId) {
        this.wcResId = wcResId;
    }

    public int getWcLikeId() {
        return wcLikeId;
    }

    public void setWcLikeId(int wcLikeId) {
        this.wcLikeId = wcLikeId;
    }

    public Bitmap getWcBitmap() {
        return wcBitmap;
    }

    public void setWcBitmap(Bitmap wcBitmap) {
        this.wcBitmap = wcBitmap;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        wcBitmap.compress(Bitmap.CompressFormat.JPEG,50,out);
        out.defaultWriteObject();
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        wcBitmap = BitmapFactory.decodeStream(in);
        in.defaultReadObject();
    }

    /*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(wcName);
        parcel.writeString(wcDescription);
        parcel.writeInt(wcFloor);
        parcel.writeInt(wcResId);
        parcel.writeInt(wcLikeId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            parcel.writeBoolean(wcLike);
        }
    }

    public static final Parcelable.Creator<WaterCloset> CREATOR = new Parcelable.Creator<WaterCloset>()
    {
        public WaterCloset createFromParcel(Parcel in)
        {
            return new WaterCloset(in);
        }
        public WaterCloset[] newArray(int size)
        {
            return new WaterCloset[size];
        }
    };*/
}
