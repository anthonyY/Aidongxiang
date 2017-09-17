package com.dongxiang.model.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "feedback", schema = "aidongxiang", catalog = "")
public class FeedbackEntity implements Serializable{

    @Id
    public int id;

    public String contact;
    public String content;
    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "Feedback_Image",
            joinColumns = {@JoinColumn(name = "feedback_id")},
            inverseJoinColumns = {@JoinColumn(name = "image_id")})
    public List<FileEntity> images;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    public Date timestamp;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
    @JoinColumn(name="user_id")//这里设置JoinColum设置了外键的名字，并且orderItem是关系维护端
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

    public List<FileEntity> getImages() {
        return images;
    }

    public void setImages(List<FileEntity> images) {
        this.images = images;
    }
}
