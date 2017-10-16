package com.aidongxiang;

import com.aidongxiang.net.AIIResponse;
import com.aidongxiang.net.OkRequest;
import com.dongxiang.model.comm.FeedbackSubmitEntity;
import com.dongxiang.model.comm.PostSubmitEntity;
import com.dongxiang.model.comm.model.Address;
import com.dongxiang.model.entity.PostEntity;
import com.dongxiang.model.entity.UserEntity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.aidongxiang.net.OkRequest.print;


public class PostTest {

    public static final String POST_LIST_RUL = "http://localhost:8080/aidongxiang/PostList";
    public static final String POST_SUBMIT_RUL = "http://localhost:8080/aidongxiang/PostSubmit";
    public static void main(String[] args){
//        requestGetData(FEEDBACK_LIST_RUL, null);

        PostSubmitEntity entity = new PostSubmitEntity();

        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(3);
        entity.setImageIds(ids);
        Address address = new Address();
        address.setRegionId(440103);
        address.setStreet("周门北路协晟大厦208");
        entity.setAddress(address);
        entity.setContent("我发的帖子");
        entity.setCategoryId(2);

        Gson gson = new Gson();
//
        requestGetData(POST_SUBMIT_RUL, gson.toJson(entity));
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
