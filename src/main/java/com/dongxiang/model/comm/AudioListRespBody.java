package com.dongxiang.model.comm;


import com.dongxiang.base.comm.AbstractRespBody;
import com.dongxiang.model.entity.AudioEntity;

import java.util.List;

public class AudioListRespBody extends AbstractRespBody {

    private int total;
    private List<AudioListEntity> audios;
    public AudioListRespBody(List<AudioListEntity> audios){
        this.audios = audios;
    }

    public List<AudioListEntity> getAudios() {
        return audios;
    }

    public void setAudios(List<AudioListEntity> audios) {
        this.audios = audios;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
