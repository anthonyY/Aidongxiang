package com.dongxiang.model.comm;

import com.dongxiang.model.comm.model.Image;
import com.dongxiang.model.entity.UserEntity;
import com.dongxiang.model.entity.VideoEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class PostListEntity {

    public int id;
    public String name;
    public String content;
    public String isPraise;
//    public String path;
    public String imagePath;
    public int statComment;
    public int statShare;
    public int statPraise;
    public UserEntity user;
    private List<Image> images;
    private VideoEntity video;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    public Date timestamp;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsPraise() {
        return isPraise;
    }

    public void setIsPraise(String isPraise) {
        this.isPraise = isPraise;
    }

//    public String getPath() {
//        return path;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    public int getStatComment() {
        return statComment;
    }

    public void setStatComment(int statComment) {
        this.statComment = statComment;
    }

    public int getStatShare() {
        return statShare;
    }

    public void setStatShare(int statShare) {
        this.statShare = statShare;
    }

    public int getStatPraise() {
        return statPraise;
    }

    public void setStatPraise(int statPraise) {
        this.statPraise = statPraise;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public VideoEntity getVideo() {
        return video;
    }

    public void setVideo(VideoEntity video) {
        this.video = video;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
