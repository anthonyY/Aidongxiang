package com.dongxiang.model.comm;


import com.dongxiang.base.comm.AbstractRespBody;
import com.dongxiang.model.entity.FeedbackEntity;

import java.util.List;

public class FeedbackListRespBody extends AbstractRespBody {

    private int total;
    private List<FeedbackListEntity> feedbacks;
    public FeedbackListRespBody(){
    }
    public FeedbackListRespBody(List<FeedbackListEntity> feedbacks){
        this.feedbacks = feedbacks;
    }

    public List<FeedbackListEntity> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<FeedbackListEntity> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
