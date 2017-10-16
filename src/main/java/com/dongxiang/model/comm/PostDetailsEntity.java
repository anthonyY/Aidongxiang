package com.dongxiang.model.comm;

import com.dongxiang.model.comm.model.Address;
import com.dongxiang.model.comm.model.Image;
import com.dongxiang.model.entity.UserEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

public class PostDetailsEntity {

    public int id;
    public String name;
    public String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date timestamp;
    public String imagePath;
    public int categoryId;
    private Address address;
    private List<Image> images;
    @Column(name = "stat_praise")
    public int statPraise;
    @Column(name = "stat_comment")
    public int statComment;
    @Column(name = "stat_share")
    public int statShare;
    public int isPraise;
    public int isCollection;
    private UserEntity user;
    private PostDetailsEntity originalPost;

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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public int getStatPraise() {
        return statPraise;
    }

    public void setStatPraise(int statPraise) {
        this.statPraise = statPraise;
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

    public int getIsPraise() {
        return isPraise;
    }

    public void setIsPraise(int isPraise) {
        this.isPraise = isPraise;
    }

    public int getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(int isCollection) {
        this.isCollection = isCollection;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public PostDetailsEntity getOriginalPost() {
        return originalPost;
    }

    public void setOriginalPost(PostDetailsEntity originalPost) {
        this.originalPost = originalPost;
    }
}
