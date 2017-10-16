package com.dongxiang.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "video", schema = "aidongxiang", catalog = "")
public class VideoEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public int id;
    public String name;
    public String path;
    public String imagePath;
    public double price;
    @Column(name = "is_charge")
    public int isCharge;
    public long duration;
    public String description;
//    @ManyToMany(cascade = CascadeType.PERSIST, fetch=FetchType.LAZY)
//    @JoinTable(name="user_collection_video",joinColumns={@JoinColumn(name="user_id")},inverseJoinColumns={@JoinColumn(name="video_id")})
//    private List<UserEntity> users;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getIsCharge() {
        return isCharge;
    }

    public void setIsCharge(int isCharge) {
        this.isCharge = isCharge;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public List<UserEntity> getUsers() {
//        return users;
//    }
//
//    public void setUsers(List<UserEntity> users) {
//        this.users = users;
//    }
}
