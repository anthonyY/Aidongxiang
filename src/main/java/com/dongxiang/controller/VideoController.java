package com.dongxiang.controller;

import com.dongxiang.base.comm.ApiData;
import com.dongxiang.model.comm.*;
import com.dongxiang.model.entity.StatisticsEntity;
import com.dongxiang.model.entity.VideoEntity;
import com.dongxiang.model.repository.StatisticsRepository;
import com.dongxiang.model.repository.VideoRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/aidongxiang")
@Controller
public class VideoController {

    @Autowired
    VideoRepository videoRepository;
    @Autowired
    StatisticsRepository statisticsRepository;

    private static Logger logger = LoggerFactory.getLogger(VideoController.class);

    @RequestMapping(value = "/VideoList", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody /*只需要响应字符串内容，可以自由控制返回的数据内容*/
    public String requestVideoList(){
        Gson gson = new Gson();
        List<VideoEntity> list = videoRepository.findAll();
        List<VideoListEntity> entitylist = new ArrayList<>();
        for (VideoEntity videoEntity: list){
            VideoListEntity entity = new VideoListEntity();
            entity.setDuration(videoEntity.duration);
            entity.setId(videoEntity.id);
            entity.setImagePath(videoEntity.imagePath);
            entity.setName(videoEntity.name);
            entity.setPath(videoEntity.path);
            entity.setPrice(videoEntity.price);
            StatisticsEntity statistics = statisticsRepository.findOne(videoEntity.getId());
            if(statistics != null){
//                statistics.getStatDownload();
                entity.setStatPraise(statistics.getStatPraise());
                entity.setStatWatch(statistics.getStatWatch());
            }
            entitylist.add(entity);
        }

        VideoListRespBody respBody = new VideoListRespBody(entitylist);
        respBody.status = 0;
        respBody.desc = "操作成功";
        return gson.toJson(new ApiData<>(respBody));
    }

//    @RequestMapping(value = "/VideoSubmit", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
//    @ResponseBody /*只需要响应字符串内容，可以自由控制返回的数据内容*/
//    public String requestVideoSubmit(@ModelAttribute("json")  VideoEntity video){
//
//        Gson gson = new Gson();
//        logger.warn("video:"+gson.toJson(video));
//        logger.warn("requestVideoSubmit");
//        DefaultRespBody defaultRespBody = new DefaultRespBody();
//        try {
//            videoRepository.save(video);
//            defaultRespBody.status = 0;
//            defaultRespBody.desc = "操作成功";
//        } catch (Exception e){
//            e.printStackTrace();
//            defaultRespBody.status = 1000;
//            defaultRespBody.desc = "添加失败";
//        }
//        return gson.toJson(new ApiData("VideoSubmit",defaultRespBody));
//    }
    @RequestMapping(value = "/VideoSubmit2", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody /*只需要响应字符串内容，可以自由控制返回的数据内容*/
    public String requestVideoSubmit2(@RequestParam("json")  String video){

        Gson gson = new Gson();
        DefaultRespBody defaultRespBody = new DefaultRespBody();
        try {
            VideoEntity videoEntity = gson.fromJson(video, VideoEntity.class);
            videoRepository.save(videoEntity);
            defaultRespBody.status = 0;
            defaultRespBody.desc = "操作成功";
        } catch (Exception e){
            e.printStackTrace();
            defaultRespBody.status = 1000;
            defaultRespBody.desc = "添加失败";
        }
        return gson.toJson(new ApiData<>("VideoSubmit",defaultRespBody));
    }

    @RequestMapping(value = "/VideoDetails/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody /*只需要响应字符串内容，可以自由控制返回的数据内容*/
    public String requestVideoDetails(@PathVariable("id") int id){
        Gson gson = new Gson();
        VideoDetailsRespBody respBody = new VideoDetailsRespBody();

        VideoEntity video = videoRepository.findOne(id);
        VideoDetailsEntity entity = new VideoDetailsEntity();
        if(video != null){
            entity.setDescription(video.description);
            entity.setDuration(video.duration);
            entity.setId(video.id);
            entity.setImagePath(video.imagePath);
            entity.setIsCharge(video.isCharge);
            entity.setName(video.name);
            entity.setPath(video.path);
            entity.setPrice(video.price);
            StatisticsEntity statistics = statisticsRepository.findOne(video.getId());
            if(statistics != null){
                entity.setStatDownload(statistics.getStatDownload());
                entity.setStatPraise(statistics.getStatPraise());
                int statWatch = statistics.getStatWatch()+1;
                entity.setStatWatch(statWatch);
                statisticsRepository.updateStatisticsWatch(statWatch, video.id);
            } else {
                statistics = new StatisticsEntity();
                entity.setStatDownload(0);
                entity.setStatPraise(0);
                int statWatch = statistics.getStatWatch()+1;
                entity.setStatWatch(statWatch);
                statistics.setStatWatch(statWatch);
                statisticsRepository.save(statistics);
            }
//            entity.setIsCollection(video.);
//            entity.setIsPraise(video.);
            respBody.setVideo(entity);
            respBody.status = 0;
            respBody.desc = "操作成功";
        } else {
            respBody.status = 2000;
            respBody.desc = "数据为空或不存在";
        }
        return gson.toJson(new ApiData<>(respBody));
    }

    /**
     * 模拟下载更新下载数量
     * @param id
     * @return
     */
    @RequestMapping(value = "/VideoDownload/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody /*只需要响应字符串内容，可以自由控制返回的数据内容*/
    public String requestDownload(@PathVariable("id") int id){
        Gson gson = new Gson();
        DefaultRespBody respBody = new DefaultRespBody();
        try {
            StatisticsEntity statistics = statisticsRepository.findOne(id);
            if(statistics != null){
                int statDownload = statistics.getStatDownload()+1;
                statisticsRepository.updateStatisticsDownload(statDownload, id);
            } else {
                statistics = new StatisticsEntity();
                statistics.setStatDownload(1);
                statisticsRepository.save(statistics);
            }
            respBody.status = 0;
            respBody.desc = "操作成功";
        } catch (Exception e){
            e.printStackTrace();
            respBody.status = 2000;
            respBody.desc = "数据为空或不存在";
        }

        return gson.toJson(new ApiData<>("DownloadVideo",respBody));
    }
    /**
     * 模拟点赞更新赞数量
     * @param id
     * @return
     */
    @RequestMapping(value = "/VideoPraise/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody /*只需要响应字符串内容，可以自由控制返回的数据内容*/
    public String requestPraise(@PathVariable("id") int id){
        Gson gson = new Gson();
        DefaultRespBody respBody = new DefaultRespBody();
        try {
            StatisticsEntity statistics = statisticsRepository.findOne(id);
            if(statistics != null){
                int statPraise = statistics.getStatPraise()+1;
                statisticsRepository.updateStatisticsPraise(statPraise, id);
            } else {
                statistics = new StatisticsEntity();
                statistics.setStatDownload(1);
                statisticsRepository.save(statistics);
            }
            respBody.status = 0;
            respBody.desc = "操作成功";
        } catch (Exception e){
            e.printStackTrace();
            respBody.status = 2000;
            respBody.desc = "数据为空或不存在";
        }

        return gson.toJson(new ApiData<>("PraiseVideo",respBody));
    }

}
