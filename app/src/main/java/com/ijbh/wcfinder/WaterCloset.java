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
    private int wcFloor, wcResId = 0;//, wcLikeId;
    private boolean wcLike = false;
    transient private Bitmap wcBitmap;
    private double ind_clean = 0.0, ind_wifi = 0.0, ind_paper = 0.0, ind_odour = 0.0;
    private double overallScore = 0.0;
    // private Bitmap wcIconBitmap;


    //NOT IN USE
    public WaterCloset(String wcName, String wcDescription, int wcFloor, boolean wcLike, int wcResId) {
        this.wcName = wcName;
        this.wcDescription = wcDescription;
        this.wcFloor = wcFloor;
        this.wcResId = wcResId;
        //this.wcLikeId = wcLikeId;
        this.wcLike = wcLike;
    }
    public WaterCloset(String wcName, String wcDescription, boolean wcLike, int wcResId, double clean, double wifi, double paper, double odour) {
        this.wcName = wcName;
        this.wcDescription = wcDescription;
        this.wcFloor = wcFloor;
        this.wcResId = wcResId;
        //this.wcLikeId = wcLikeId;
        this.wcLike = wcLike;
        this.ind_clean = clean;
        this.ind_wifi = wifi;
        this.ind_paper = paper;
        this.ind_odour = odour;
        this.overallScore = (clean + wifi + paper + odour)/4;
    }

    //NOT IN USE
    public WaterCloset(String wcName, String wcDesc, int wcFloor, boolean b, String currentImagePath) {
        this.wcName = wcName;
        this.wcDescription = wcDesc;
        this.wcFloor = wcFloor;
        //this.wcBitmap = wcBitmap;
        this.wcUriPath = currentImagePath;
        compressBitmap(currentImagePath);
        //wcBitmap = compressBitmap(currentImagePath);
        //this.wcLikeId = likeId;
        this.wcLike = b;
    }

    public WaterCloset(String wcName, String wcDesc, boolean wcLike, String currentImagePath, double ind_clean, double ind_wifi, double ind_paper, double ind_odour) {
        this.wcName = wcName;
        this.wcDescription = wcDesc;
        //this.wcFloor = wcFloor;
        this.wcUriPath = currentImagePath;
        compressBitmap(currentImagePath);
        this.wcLike = wcLike;
        this.ind_clean = ind_clean;
        this.ind_wifi = ind_wifi;
        this.ind_paper = ind_paper;
        this.ind_odour = ind_odour;
        this.overallScore = (ind_clean + ind_wifi + ind_paper + ind_odour)/4;
    }

    private void compressBitmap(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);

        //Glide.with()
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, stream);
        byte[] byteArray = stream.toByteArray();
        wcBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        //TODO finish the function (first remove the comment)
        //FileOutputStream
        //compBitmap = wcBitmap.compress(Bitmap.CompressFormat.JPEG, )


        //return compBitmap;
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

    public Bitmap getWcBitmap() {
        return wcBitmap;
    }

    public void setWcBitmap(Bitmap wcBitmap) {
        this.wcBitmap = wcBitmap;
    }

    public double getInd_clean() {
        return ind_clean;
    }

    public void setInd_clean(double ind_clean) {
        this.ind_clean = ind_clean;
    }

    public double getInd_wifi() {
        return ind_wifi;
    }

    public void setInd_wifi(double ind_wifi) {
        this.ind_wifi = ind_wifi;
    }

    public double getInd_paper() {
        return ind_paper;
    }

    public void setInd_paper(double ind_paper) {
        this.ind_paper = ind_paper;
    }

    public double getInd_odour() {
        return ind_odour;
    }

    public void setInd_odour(double ind_odour) {
        this.ind_odour = ind_odour;
    }

    public double getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(double overallScore) {
        this.overallScore = overallScore;
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
        if (wcBitmap != null) {
            wcBitmap.compress(Bitmap.CompressFormat.JPEG, 25, out);
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
}

