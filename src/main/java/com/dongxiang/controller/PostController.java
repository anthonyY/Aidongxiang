package com.dongxiang.controller;

import com.dongxiang.base.comm.ApiData;
import com.dongxiang.model.comm.*;
import com.dongxiang.model.comm.model.Address;
import com.dongxiang.model.comm.model.Image;
import com.dongxiang.model.entity.FileEntity;
import com.dongxiang.model.entity.PostEntity;
import com.dongxiang.model.entity.StatisticsPostEntity;
import com.dongxiang.model.entity.VideoEntity;
import com.dongxiang.model.repository.PostRepository;
import com.dongxiang.model.repository.StatisticsPostRepository;
import com.dongxiang.model.repository.UploadFileRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PostController {

    private static Logger logger = LoggerFactory.getLogger(PostController.class);


    @Autowired
    PostRepository postRepository;
    @Autowired
    StatisticsPostRepository statisticsPostRepository;

    @RequestMapping(value = "/PostList", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody /*只需要响应字符串内容，可以自由控制返回的数据内容*/
    public String requestPostList(){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        PostListRespBody respBody = new PostListRespBody();
        List<PostEntity> list = postRepository.findAll();
        int total = 0;
        if(list != null){
            total = list.size();
            List<PostListEntity> entitylist = new ArrayList<>();
            for (PostEntity postEntity: list){
                PostListEntity entity = new PostListEntity();
                entity.setId(postEntity.id);
                entity.setImagePath(postEntity.imagePath);
                entity.setName(postEntity.name);
                entity.setContent(postEntity.content);
                entity.setVideo(postEntity.video);
                entity.setTimestamp(postEntity.timestamp);
                List<FileEntity> images = postEntity.images;
                if(images != null){
                    List<Image> images1 = new ArrayList<>();
                    for (FileEntity fileEntity: images){
                        Image image = new Image();
                        image.setId(fileEntity.getId());
                        image.setPath(fileEntity.getPath());
                        images1.add(image);
                    }
                    entity.setImages(images1);
                }
                StatisticsPostEntity statistics = statisticsPostRepository.findOne(postEntity.getId());
                if(statistics != null){
                    entity.setStatPraise(statistics.getStatPraise());
                    entity.setStatComment(statistics.getStatCommnent());
                    entity.setStatShare(statistics.getStatShare());
                }
                entitylist.add(entity);
            }
            respBody.setTotal(total);
            respBody.setPosts(entitylist);
        }


        respBody.status = 0;
        respBody.desc = "操作成功";
        return gson.toJson(new ApiData<>(respBody));
    }



    @RequestMapping(value = "/PostSubmit", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody /*只需要响应字符串内容，可以自由控制返回的数据内容*/
    public String requestPostSubmit(@RequestParam("json")  String audio){

        Gson gson = new Gson();
        DefaultRespBody defaultRespBody = new DefaultRespBody();
        try {
            PostSubmitEntity postSubmitEntity = gson.fromJson(audio, PostSubmitEntity.class);
            PostEntity postEntity = new PostEntity();
            if(postSubmitEntity.getImageIds() != null){
//                List<FileEntity> fileEntities = new ArrayList<>();
//                for(Integer id: postSubmitEntity.getImageIds()){
//                    FileEntity fileEntity = new FileEntity();
//                    fileEntity.setId(id);
//                    fileEntities.add(fileEntity);
//                    postEntity.setImages(fileEntities);
//                }
            }
            if(postSubmitEntity.getVideoId() > 0){
                VideoEntity videoEntity = new VideoEntity();
                videoEntity.setId(postSubmitEntity.getVideoId());
                postEntity.setVideo(videoEntity);
            }
            postEntity.setContent(postSubmitEntity.getContent());
            Address address = new Address();
            if(address != null){
                postEntity.setRegionId(address.getRegionId());
                postEntity.setLongitude(address.getLongitude());
                postEntity.setLatitude(address.getLatitude());
                postEntity.setStreet(address.getStreet());
            }
            postEntity.setTimestamp(new Date());
            postEntity.setCategoryId(postSubmitEntity.getCategoryId());
            postEntity.setPostId(postSubmitEntity.getPostId());

            postRepository.save(postEntity);
            defaultRespBody.status = 0;
            defaultRespBody.desc = "操作成功";
        } catch (Exception e){
            e.printStackTrace();
            defaultRespBody.status = 1000;
            defaultRespBody.desc = "添加失败";
        }
        return gson.toJson(new ApiData<>("PostSubmit",defaultRespBody));
    }

    @RequestMapping(value = "/PostDetails/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody /*只需要响应字符串内容，可以自由控制返回的数据内容*/
    public String requestPostDetails(@PathVariable("id") int id){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        PostDetailsRespBody respBody = new PostDetailsRespBody();
        PostEntity post = postRepository.findOne(id);
        PostDetailsEntity entity = new PostDetailsEntity();
        if(post != null){
            entity.setId(id);
            entity.setImagePath(post.imagePath);
            entity.setName(post.name);
            StatisticsPostEntity statistics = statisticsPostRepository.findOne(id);
            if(statistics != null){
                entity.setStatComment(statistics.getStatCommnent());
                entity.setStatPraise(statistics.getStatPraise());
                entity.setStatShare(statistics.getStatShare());

            } else {
                statistics = new StatisticsPostEntity();
                entity.setStatComment(0);
                entity.setStatPraise(0);
                entity.setStatShare(0);
                statistics.setId(id);
                statisticsPostRepository.save(statistics);
            }

            respBody.setPost(entity);
            respBody.status = 0;
            respBody.desc = "操作成功";
        } else {
            respBody.status = 2000;
            respBody.desc = "数据为空或不存在";
        }
        return gson.toJson(new ApiData<>(respBody));
    }

    /**
     * 模拟点赞更新赞数量
     * @param id
     * @return
     */
    @RequestMapping(value = "/PostPraise/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody /*只需要响应字符串内容，可以自由控制返回的数据内容*/
    public String requestPraise(@PathVariable("id") int id){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        DefaultRespBody respBody = new DefaultRespBody();
        try {
            StatisticsPostEntity statistics = statisticsPostRepository.findOne(id);
            if(statistics != null){
                int statPraise = statistics.getStatPraise()+1;
                statisticsPostRepository.updateStatisticsPraise(statPraise, id);
            } else {
                statistics = new StatisticsPostEntity();
                statistics.setStatPraise(1);
                statistics.setId(id);
                statisticsPostRepository.save(statistics);
            }
            respBody.status = 0;
            respBody.desc = "操作成功";
        } catch (Exception e){
            e.printStackTrace();
            respBody.status = 2000;
            respBody.desc = "数据为空或不存在";
        }

        return gson.toJson(new ApiData<>("PostPraise",respBody));
    }
    /**
     * 点赞更新赞数量
     * @param id
     * @return
     */
    public String requestShare(int id){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        DefaultRespBody respBody = new DefaultRespBody();
        try {
            StatisticsPostEntity statistics = statisticsPostRepository.findOne(id);
            if(statistics != null){
                int statShare = statistics.getStatShare()+1;
                statisticsPostRepository.updateStatisticsShare(statShare, id);
            } else {
                statistics = new StatisticsPostEntity();
                statistics.setStatShare(1);
                statistics.setId(id);
                statisticsPostRepository.save(statistics);
            }
            respBody.status = 0;
            respBody.desc = "操作成功";
        } catch (Exception e){
            e.printStackTrace();
            respBody.status = 2000;
            respBody.desc = "数据为空或不存在";
        }

        return gson.toJson(new ApiData<>("PostShare",respBody));
    }


}
