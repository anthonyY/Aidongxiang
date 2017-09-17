package com.dongxiang.model.comm;


import com.dongxiang.base.comm.AbstractRespBody;

public class AudioDetailsRespBody extends AbstractRespBody {

    private AudioDetailsEntity audio;

    public AudioDetailsRespBody(){
    }

    public AudioDetailsEntity getAudio() {
        return audio;
    }

    public void setAudio(AudioDetailsEntity audio) {
        this.audio = audio;
    }
}
