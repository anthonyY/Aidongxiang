package com.dongxiang.base.comm;

import com.google.gson.annotations.SerializedName;

/**
 * Where条件
 * @param <WHERE>
 */
public class Table<WHERE>{
    @SerializedName("pa")
    public int page =1;
    @SerializedName("li")
    public int limit=20;
    @SerializedName("ob")
    public int orderBy= 0;
    @SerializedName("ot")
    public int orderType=0;
    @SerializedName("w")
    public WHERE where;

    public Table(WHERE where){
        this.where = where;
    }
}
