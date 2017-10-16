package com.dongxiang.model.comm.model;

import java.util.List;

public class Address {

    private int id;
    private int regionId;
    private String street;
    private List<Region> regionInfo;
    private double longitude;
    private double latitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public List<Region> getRegionInfo() {
        return regionInfo;
    }

    public void setRegionInfo(List<Region> regionInfo) {
        this.regionInfo = regionInfo;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
