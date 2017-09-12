package com.dongxiang.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "statistics", schema = "aidongxiang", catalog = "")
public class StatisticsEntity {

    @Id
    public int id;
    @Column(name = "stat_watch")
    public int statWatch;
    @Column(name = "stat_praise")
    public int statPraise;
    @Column(name = "stat_download")
    public int statDownload;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatWatch() {
        return statWatch;
    }

    public void setStatWatch(int statWatch) {
        this.statWatch = statWatch;
    }

    public int getStatPraise() {
        return statPraise;
    }

    public void setStatPraise(int statPraise) {
        this.statPraise = statPraise;
    }

    public int getStatDownload() {
        return statDownload;
    }

    public void setStatDownload(int statDownload) {
        this.statDownload = statDownload;
    }
}
