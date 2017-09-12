package com.dongxiang.model.comm;


import com.dongxiang.base.comm.AbstractRespBody;

import java.util.List;

public class VideoListRespBody extends AbstractRespBody {

    private int total;
    private List<VideoListEntity> videos;
    public VideoListRespBody(List<VideoListEntity> videos){
        this.videos = videos;
    }

    public List<VideoListEntity> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoListEntity> videos) {
        this.videos = videos;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
