package com.ijbh.wcfinder;


import java.io.IOException;
import java.io.Serializable;

public class WaterCloset implements Serializable {

    private String wcName, wcDescription, wcUriPath, wcLocation, wcDate;
    private int wcResId = 0;
    private boolean wcLike = false;
    private double ind_clean = 0.0, ind_wifi = 0.0, ind_paper = 0.0, ind_odour = 0.0;
    private double overallScore = 0.0;

    public WaterCloset(){}

    public WaterCloset(String wcName, String wcDescription, String location, String date, boolean wcLike, int wcResId, double clean, double wifi, double paper, double odour) {
        this.wcName = wcName;
        this.wcDescription = wcDescription;
        this.wcLocation = location;
        this.wcDate = date;
        this.wcResId = wcResId;
        this.wcLike = wcLike;
        this.ind_clean = clean;
        this.ind_wifi = wifi;
        this.ind_paper = paper;
        this.ind_odour = odour;
        this.overallScore = (clean + wifi + paper + odour)/4.0;
    }


    public WaterCloset(String wcName, String wcDesc, String location, String date, boolean wcLike, String currentImagePath, double ind_clean, double ind_wifi, double ind_paper, double ind_odour) {
        this.wcName = wcName;
        this.wcDescription = wcDesc;
        this.wcLocation = location;
        this.wcDate = date;
        this.wcUriPath = currentImagePath;
        this.wcLike = wcLike;
        this.ind_clean = ind_clean;
        this.ind_wifi = ind_wifi;
        this.ind_paper = ind_paper;
        this.ind_odour = ind_odour;
        this.overallScore = (ind_clean + ind_wifi + ind_paper + ind_odour)/4.0;
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

    public String getWcLocation() {
        return wcLocation;
    }

    public void setWcLocation(String wcLocation) {
        this.wcLocation = wcLocation;
    }

    public String getWcDate() {
        return wcDate;
    }

    public void setWcDate(String wcDate) {
        this.wcDate = wcDate;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {

        out.defaultWriteObject();
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {

        in.defaultReadObject();
    }

    public String getWcUriPath() {
        return wcUriPath;
    }

    public void setWcUriPath(String wcUriPath) {
        this.wcUriPath = wcUriPath;
    }
}

