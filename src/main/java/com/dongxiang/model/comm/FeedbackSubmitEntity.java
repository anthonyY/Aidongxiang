package com.dongxiang.model.comm;

import com.dongxiang.model.entity.FeedbackEntity;

import java.util.List;

public class FeedbackSubmitEntity extends FeedbackEntity {

    private List<Integer> imageIds;

    public List<Integer> getImageIds() {
        return imageIds;
    }

    public void setImageIds(List<Integer> imageIds) {
        this.imageIds = imageIds;
    }



}
