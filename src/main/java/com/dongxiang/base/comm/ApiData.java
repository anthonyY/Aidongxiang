package com.dongxiang.base.comm;

import com.google.gson.annotations.SerializedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 响应API头
 * @param <T>
 */
public class ApiData<T>{
    private static Logger devLogger = LoggerFactory.getLogger("dev");
    @SerializedName("s")
    public String session="";
    @SerializedName("n")
    public String namespace;
    @SerializedName("q")
    public T query;

    public ApiData(String namespace, T object){
        this.namespace=namespace;
        this.query = object;
    }
    public ApiData(T object){
        String name = object.getClass().getSimpleName();
        if(name.endsWith("RespBody")){
            name = name.substring(0,name.length()-8);
        }else if(name.endsWith("ReqBody")){
            name = name.substring(0,name.length()-7);
        }else{
            devLogger.warn("响应对象类名不规范："+name);
        }
        this.namespace = name;
        this.query = object;
    }
}
