package com.dongxiang.model.entity;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dzkan on 2016/3/8.
 */
@Entity
@Table(name = "user", schema = "aidongxiang", catalog = "")
public class UserEntity implements Serializable{
    private int id;
    private String nickname;
    private String password;
    private String firstName;
    private String lastName;
//    private Collection<BlogEntity> blogsById;

//    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//    @JoinTable(name = "user_collection_video",
//            joinColumns = {@JoinColumn(name = "video_id", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName ="id")})
//    private List<VideoEntity> collectionVideos;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nickname", nullable = false, length = 45)
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 45)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "first_name", nullable = true, length = 45)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = true, length = 45)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

//    public List<VideoEntity> getCollectionVideos() {
//        return collectionVideos;
//    }
//
//    public void setCollectionVideos(List<VideoEntity> collectionVideos) {
//        this.collectionVideos = collectionVideos;
//    }

//    @OneToMany(mappedBy = "userByUserId")
//    public Collection<BlogEntity> getBlogsById() {
//        return blogsById;
//    }
//
//    public void setBlogsById(Collection<BlogEntity> blogsById) {
//        this.blogsById = blogsById;
//    }
}
