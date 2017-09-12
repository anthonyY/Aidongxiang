package com.dongxiang.model.comm;

public class VideoListEntity {

    public int id;
    public String name;
    public String path;
    public String imagePath;
    public double price;
//    public int isCharge;
    public long duration;
    public int statWatch;
    public int statPraise;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getStatWatch() {
        return statWatch;
    }

    public void setStatWatch(int statWatch) {
        this.statWatch = statWatch;
    }

    public int getStatPraise() {
        return statPraise;
    }

    public void setStatPraise(int statPraise) {
        this.statPraise = statPraise;
    }
}
