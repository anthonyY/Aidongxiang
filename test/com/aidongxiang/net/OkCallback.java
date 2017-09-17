package com.aidongxiang.net;

import com.dongxiang.base.comm.AbstractRespBody;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class OkCallback implements Callback {

    org.slf4j.Logger logger = LoggerFactory.getLogger(OkCallback.class);
    private AIIResponse aiiResponse;
    public OkCallback(AIIResponse aiiResponse){
        this.aiiResponse = aiiResponse;
        aiiResponse.onStart();
    }

//    //网络异常
    private void onFailure(int code){
        aiiResponse.onFailure("网络异常"+code);
        aiiResponse.onFinish();
    }
//    //网络异常
    private void onServiceError(int status){
        aiiResponse.onServiceError("服务器数据异常", status);
        aiiResponse.onFinish();
    }
    //请求成功
    private void onSuccess(Object t){

        aiiResponse.onSuccess(t);
        aiiResponse.onFinish();

    }


    @Override
    public void onFailure(Call call, IOException e) {
        e.printStackTrace();
        logger.warn(e.getMessage());
        aiiResponse.onFailure("网络不给力哦！"+e.getLocalizedMessage());
        aiiResponse.onFinish();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String responseContent = response.body().string();
        logger.warn(responseContent);
        int code = response.code();
        if (code == 200) {
            Object t = aiiResponse.get();
            if(t.getClass() == String.class){

                t = responseContent;
            } else if(AbstractRespBody.class.isAssignableFrom(t.getClass())){
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(responseContent);
                    String respQuery = jsonObject.optString("q");
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                    t = gson.fromJson(respQuery, t.getClass());
                    onSuccess(t);
                } catch (JSONException e) {
                    e.printStackTrace();
                    onServiceError(code);
                }


            } else {
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                t = gson.fromJson(responseContent, t.getClass());
                onSuccess(t);
            }


        } else {
            onFailure(code);
        }
    }
}
