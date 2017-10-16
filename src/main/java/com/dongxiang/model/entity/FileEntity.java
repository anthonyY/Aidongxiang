package com.dongxiang.model.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "file", schema = "aidongxiang", catalog = "")
public class FileEntity {


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private int type;
    private long duration;
    private String name;
    private String md5;
    private String path;

////    @ManyToMany(mappedBy="images" , fetch = FetchType.LAZY)
//    @ManyToMany(cascade = {CascadeType.ALL /*,CascadeType.MERGE*/ }, fetch = FetchType.LAZY)
//    @JoinTable(name = "post_image",
//            joinColumns = {@JoinColumn(name = "file_id")},
//            inverseJoinColumns = {@JoinColumn(name = "post_id")})
//    public List<PostEntity> posts;

//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "images")
//    @ManyToMany(cascade = {CascadeType.REFRESH ,CascadeType.MERGE }, fetch = FetchType.EAGER)
//    @JoinTable(name = "Feedback_Image",
//        joinColumns = {@JoinColumn(name = "file_id")},
//        inverseJoinColumns = {@JoinColumn(name = "feedback_id")})
////    @ManyToMany(mappedBy="images")
//    private List<FeedbackEntity> feedbacks;

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

//    public List<FeedbackEntity> getFeedbacks() {
//        return feedbacks;
//    }
//
//    public void setFeedbacks(List<FeedbackEntity> feedbacks) {
//        this.feedbacks = feedbacks;
//    }

//    public List<PostEntity> getPosts() {
//        return posts;
//    }
//
//    public void setPosts(List<PostEntity> posts) {
//        this.posts = posts;
//    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
