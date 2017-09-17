package com.dongxiang.model.comm;


import com.dongxiang.base.comm.AbstractRespBody;
import com.dongxiang.model.entity.FileEntity;

import java.util.List;

public class FileUploadRespBody extends AbstractRespBody {

    private List<FileEntity> files;
    private List<Integer> ids;

    public List<FileEntity> getFiles() {
        return files;
    }

    public void setFiles(List<FileEntity> files) {
        this.files = files;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
