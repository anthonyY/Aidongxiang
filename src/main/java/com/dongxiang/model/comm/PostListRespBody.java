package com.dongxiang.model.comm;


import com.dongxiang.base.comm.AbstractRespBody;

import java.util.List;

public class PostListRespBody extends AbstractRespBody {

    private int total;
    private List<PostListEntity> posts;
    public PostListRespBody(){
    }

    public PostListRespBody(List<PostListEntity> posts){
        this.posts = posts;
    }

    public List<PostListEntity> getPosts() {
        return posts;
    }

    public void setPosts(List<PostListEntity> posts) {
        this.posts = posts;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
