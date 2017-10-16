package com.dongxiang.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "statistics_post", schema = "aidongxiang", catalog = "")
public class StatisticsPostEntity {

    @Id
    public int id;
    @Column(name = "stat_comment")
    public int statCommnent;
    @Column(name = "stat_praise")
    public int statPraise;
    @Column(name = "stat_share")
    public int statShare;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatCommnent() {
        return statCommnent;
    }

    public void setStatCommnent(int statCommnent) {
        this.statCommnent = statCommnent;
    }

    public int getStatPraise() {
        return statPraise;
    }

    public void setStatPraise(int statPraise) {
        this.statPraise = statPraise;
    }

    public int getStatShare() {
        return statShare;
    }

    public void setStatShare(int statShare) {
        this.statShare = statShare;
    }
}
