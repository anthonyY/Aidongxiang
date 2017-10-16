package com.aidongxiang;

import com.aidongxiang.net.AIIResponse;
import com.aidongxiang.net.JsonUtils;
import com.aidongxiang.net.OkRequest;
import com.dongxiang.base.comm.ApiData;
import com.dongxiang.model.comm.FeedbackSubmitEntity;
import com.dongxiang.model.comm.FileUploadReqBody;
import com.dongxiang.model.comm.FileUploadRespBody;
import com.dongxiang.model.entity.FileEntity;
import com.dongxiang.model.entity.UserEntity;
import com.dongxiang.utils.MD5Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static com.aidongxiang.FileUploadTest1.print;

public class FeedbackTest {

    public static final String FEEDBACK_LIST_RUL = "http://localhost:8080/aidongxiang/FeedbackList";
    public static final String FEEDBACK_SUBMIT_RUL = "http://localhost:8080/aidongxiang/FeedbackSubmit";
    public static void main(String[] args){
//        requestGetData(FEEDBACK_LIST_RUL, null);

        FeedbackSubmitEntity entity = new FeedbackSubmitEntity();
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(3);
        entity.setImageIds(ids);
        entity.setContact("15217210030");
        entity.setContent("反馈啦啦啦");
        UserEntity user = new UserEntity();
        user.setId(2);
        entity.setUser(user);
        Gson gson = new Gson();
//
        requestGetData(FEEDBACK_SUBMIT_RUL, gson.toJson(entity));
    }

    private static void requestGetData(String url, String json){

        OkRequest okRequest = new OkRequest();
        HashMap<String, String> stringParams = new HashMap<>();
        if(json != null){
            stringParams.put("json", json);
        }

        okRequest.get(url, stringParams, new AIIResponse<String>(){
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);

            }

            @Override
            public void onFailure(String content) {
                super.onFailure(content);
            }

        });

    }
    private static void requestPostData(String url, String json){

        OkRequest okRequest = new OkRequest();
        HashMap<String, String> stringParams = new HashMap<>();
        stringParams.put("json", json);
        okRequest.post(url, stringParams, new AIIResponse<String>(){
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                print(""+response);

            }

            @Override
            public void onFailure(String content) {
                super.onFailure(content);
                print(""+content);
            }

        });

    }
}
