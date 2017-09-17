package com.dongxiang.model.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "file", schema = "aidongxiang", catalog = "")
public class FileEntity {


    @Id
    @GeneratedValue
    private int id;
    private long duration;
    private String name;
    private String md5;
    private String path;

//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "images")
    @ManyToMany(cascade = {CascadeType.REFRESH ,CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinTable(name = "Feedback_Image",
        joinColumns = {@JoinColumn(name = "image_id")},
        inverseJoinColumns = {@JoinColumn(name = "feedback_id")})
    private List<FeedbackEntity> feedbacks;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date timestamp;

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

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public List<FeedbackEntity> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<FeedbackEntity> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
