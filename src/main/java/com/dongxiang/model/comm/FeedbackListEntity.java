package com.dongxiang.model.comm;

import com.dongxiang.model.comm.model.Image;
import com.dongxiang.model.entity.UserEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class FeedbackListEntity {

    public int id;
    public String contact;
    public String content;
    public List<Image> images;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    public Date timestamp;
    public UserEntity user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
