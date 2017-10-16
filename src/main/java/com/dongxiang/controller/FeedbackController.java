package com.dongxiang.controller;

import com.dongxiang.base.comm.ApiData;
import com.dongxiang.model.comm.*;
import com.dongxiang.model.comm.model.Image;
import com.dongxiang.model.entity.*;
import com.dongxiang.model.repository.FeedbackRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@RequestMapping("/aidongxiang")
@Controller
public class FeedbackController {

    @Autowired
    FeedbackRepository feedbackRepository;


    private static Logger logger = LoggerFactory.getLogger(FeedbackController.class);

    @RequestMapping(value = "/FeedbackList", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody /*只需要响应字符串内容，可以自由控制返回的数据内容*/
    public String requestFeedbackList(ModelMap modelMap){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        List<FeedbackEntity> list = feedbackRepository.findAll();
        FeedbackListRespBody feedbackListRespBody = new FeedbackListRespBody();
        int total = 0;
        if(list != null){
            List<FeedbackListEntity> feedbacks = new ArrayList<>();
            total = list.size();
            for (FeedbackEntity entity: list){
                FeedbackListEntity feedback = new FeedbackListEntity();
                feedback.setContact(entity.getContact());
                feedback.setContent(entity.getContent());
                feedback.setId(entity.getId());
                feedback.setTimestamp(entity.getTimestamp());
                if(entity.getUser() != null){
                    UserEntity user = new UserEntity();
                    user.setId(entity.getUser().getId());
                    user.setNickname(entity.getUser().getNickname());
                    feedback.setUser(user);
                }
                if(entity.getImages() != null){
                    List<Image> images = new ArrayList<>();
                    for (FileEntity fileEntity : entity.getImages()){
                        Image image = new Image();
                        image.setId(fileEntity.getId());
                        image.setPath(fileEntity.getPath());
                        images.add(image);
                    }
                    feedback.setImages(images);
                }
                feedbacks.add(feedback);
            }
            feedbackListRespBody.setFeedbacks(feedbacks);
        }
        feedbackListRespBody.setTotal(total);
        feedbackListRespBody.status = 0;
        feedbackListRespBody.desc = "操作成功";
        return gson.toJson(new ApiData<>("FeedbackList",feedbackListRespBody));


    }

    @RequestMapping(value = "/FeedbackSubmit", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody /*只需要响应字符串内容，可以自由控制返回的数据内容*/
    public String requestFeedbackSubmit(@RequestParam("json")  String feedback){

        Gson gson = new Gson();
        DefaultRespBody defaultRespBody = new DefaultRespBody();
        try {
            FeedbackSubmitEntity feedbackSubmitEntity = gson.fromJson(feedback, FeedbackSubmitEntity.class);
            List<Integer> imageIds = feedbackSubmitEntity.getImageIds();

            FeedbackEntity feedbackEntity = new FeedbackEntity();
            if(imageIds != null){
                List<FileEntity> fileEntities = new ArrayList<>();
                for(int id : imageIds){
                    FileEntity fileEntity = new FileEntity();
                    fileEntity.setId(id);
                    fileEntities.add(fileEntity);
                }
                feedbackEntity.setImages(fileEntities);
            }

            feedbackEntity.setTimestamp(new Date());
            feedbackEntity.setContact(feedbackSubmitEntity.getContact());
            feedbackEntity.setContent(feedbackSubmitEntity.getContent());
            feedbackEntity.setUser(feedbackSubmitEntity.getUser());

            feedbackRepository.save(feedbackEntity);
            defaultRespBody.status = 0;
            defaultRespBody.desc = "操作成功";
        } catch (Exception e){
            e.printStackTrace();
            defaultRespBody.status = 1000;
            defaultRespBody.desc = "添加失败";
        }
        return gson.toJson(new ApiData<>("FeedbackSubmit",defaultRespBody));
    }

    @RequestMapping(value = "/FeedbackSubmit2", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody /*只需要响应字符串内容，可以自由控制返回的数据内容*/
    public String requestFeedbackSubmit2(String content, String contact, int userId, List<Integer> imageIds){

        Gson gson = new Gson();
        DefaultRespBody defaultRespBody = new DefaultRespBody();
        try {
            FeedbackEntity feedbackEntity = new FeedbackEntity();
            feedbackEntity.contact = contact;
            feedbackEntity.content = content;
            List<FileEntity> fileEntities = new ArrayList<>();
            for (int id : imageIds){
                FileEntity fileEntity = new FileEntity();
                fileEntity.setId(id);
                fileEntities.add(fileEntity);
            }
            feedbackEntity.images = fileEntities;
            UserEntity userEntity = new UserEntity();
            userEntity.setId(userId);
            feedbackEntity.user = userEntity;
            feedbackEntity.timestamp = new Date();
            feedbackRepository.save(feedbackEntity);
            defaultRespBody.status = 0;
            defaultRespBody.desc = "操作成功";
        } catch (Exception e){
            e.printStackTrace();
            defaultRespBody.status = 1000;
            defaultRespBody.desc = "添加失败";
        }
        return gson.toJson(new ApiData<>("FeedbackSubmit",defaultRespBody));
    }



    @RequestMapping(value = "/Feedbacks", method = RequestMethod.GET)
    public String addUser() {
        // 返回 admin/addUser.jsp页面
        return "admin/feedbacks";
    }

}
