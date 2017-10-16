package com.dongxiang.model.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "post", schema = "aidongxiang", catalog = "")
public class PostEntity {


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public int id;
    public String name;
    public String imagePath;
    public int regionId;
    public int categoryId;
    public int postId;
    public double longitude;
    public double latitude;
    public String street;

    @Column(name = "user_id")
    public int userId;
    public String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    public Date timestamp;
    @OneToOne()
    @JoinColumn(name="video_id")//关联的表为video表，其主键是id
    public VideoEntity video;

    @ManyToMany(cascade = {CascadeType.REFRESH/*, CascadeType.MERGE*/}, fetch = FetchType.EAGER)
    @JoinTable(name="post_image",
            joinColumns={@JoinColumn(name="post_id")},
            inverseJoinColumns={@JoinColumn(name="file_id")}
    )
//    @ManyToMany(mappedBy="posts" )
    public List<FileEntity> images;

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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public VideoEntity getVideo() {
        return video;
    }

    public void setVideo(VideoEntity video) {
        this.video = video;
    }

    public List<FileEntity> getImages() {
        return images;
    }

    public void setImages(List<FileEntity> images) {
        this.images = images;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}

