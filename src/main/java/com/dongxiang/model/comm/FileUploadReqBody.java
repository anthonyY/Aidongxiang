package com.dongxiang.model.comm;


import com.dongxiang.base.comm.AbstractReqBody;

import java.util.List;

public class FileUploadReqBody extends AbstractReqBody {

    private List<String> md5s;

    public List<String> getMd5s() {
        return md5s;
    }

    public void setMd5s(List<String> md5s) {
        this.md5s = md5s;
    }
}
