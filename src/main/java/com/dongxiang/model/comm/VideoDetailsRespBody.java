package com.dongxiang.model.comm;


import com.dongxiang.base.comm.AbstractRespBody;

public class VideoDetailsRespBody extends AbstractRespBody {

    private VideoDetailsEntity video;

    public VideoDetailsRespBody(){
    }
    public VideoDetailsRespBody(VideoDetailsEntity video){
        this.video = video;
    }

    public VideoDetailsEntity getVideo() {
        return video;
    }

    public void setVideo(VideoDetailsEntity video) {
        this.video = video;
    }
}
