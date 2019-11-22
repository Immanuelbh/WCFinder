package com.ijbh.wcfinder;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class WaterCloset implements Serializable {

    private String wcName, wcDescription, wcUriPath;
    private int wcFloor, wcResId=0, wcLikeId;
    private boolean wcLike;
    transient private Bitmap wcBitmap;
   // private Bitmap wcIconBitmap;

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
        //this.wcIconBitmap = compressBitmap(wcBitmap);
    }

    public WaterCloset(String wcName, String wcDesc, int wcFloor, boolean b, String currentImagePath, int likeId) {
        this.wcName = wcName;
        this.wcDescription = wcDesc;
        this.wcFloor = wcFloor;
        //this.wcBitmap = wcBitmap;
        this.wcUriPath = currentImagePath;
        compressBitmap(currentImagePath);
        //wcBitmap = compressBitmap(currentImagePath);
        this.wcLikeId = likeId;
        this.wcLike = b;
    }

    private void compressBitmap(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);

        //Glide.with()
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25,stream);
        byte[] byteArray = stream.toByteArray();
        wcBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        //TODO finish the function (first remove the comment)
        //FileOutputStream
        //compBitmap = wcBitmap.compress(Bitmap.CompressFormat.JPEG, )


        //return compBitmap;
    }

    public WaterCloset(String wcName, String wcDescription, int wcFloor, boolean wcLike, int wcResId) {
        this.wcName = wcName;
        this.wcDescription = wcDescription;
        this.wcFloor = wcFloor;
        this.wcLike = wcLike;
        this.wcResId = wcResId;
    }
/*

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
*/

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
/*

    public Bitmap getWcIconBitmap() {
        return wcIconBitmap;
    }

    public void setWcIconBitmap(Bitmap wcIconBitmap) {
        this.wcIconBitmap = wcIconBitmap;
    }
*/

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        if(wcBitmap != null){
            wcBitmap.compress(Bitmap.CompressFormat.JPEG,25,out);
            //wcIconBitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
        }
        out.defaultWriteObject();
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        wcBitmap = BitmapFactory.decodeStream(in);
        //wcIconBitmap = BitmapFactory.decodeStream(in);
        in.defaultReadObject();
    }

    public String getWcUriPath() {
        return wcUriPath;
    }

    public void setWcUriPath(String wcUriPath) {
        this.wcUriPath = wcUriPath;
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
