package com.dongxiang.model.comm;


import com.dongxiang.base.comm.AbstractRespBody;

public class PostDetailsRespBody extends AbstractRespBody {

    private PostDetailsEntity post;

    public PostDetailsRespBody(){
    }
    public PostDetailsRespBody(PostDetailsEntity post){
        this.post = post;
    }

    public PostDetailsEntity getPost() {
        return post;
    }

    public void setPost(PostDetailsEntity post) {
        this.post = post;
    }
}
