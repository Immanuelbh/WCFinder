package com.ijbh.wcfinder;

public class WaterCloset {
    private String wcName, wcDescription;
    private int wcFloor, wcResId, wcLikeId;
    private boolean wcLike;

    public WaterCloset(String wcName, String wcDescription, int wcFloor, boolean wcLike, int wcResId, int wcLikeId) {
        this.wcName = wcName;
        this.wcDescription = wcDescription;
        this.wcFloor = wcFloor;
        this.wcResId = wcResId;
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
}
