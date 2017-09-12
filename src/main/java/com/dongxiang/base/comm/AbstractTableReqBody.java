package com.dongxiang.base.comm;

import com.google.gson.annotations.SerializedName;

public abstract class AbstractTableReqBody<WHERE> {
    @SerializedName("a")
    public int action=1;
    @SerializedName("ta")
    public Table<WHERE> table;
    public AbstractTableReqBody(Table<WHERE> where){
        this.table=where;
    }
}
