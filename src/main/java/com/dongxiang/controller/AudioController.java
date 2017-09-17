package com.dongxiang.controller;

import com.dongxiang.base.comm.ApiData;
import com.dongxiang.model.comm.*;
import com.dongxiang.model.component.UploadComponent;
import com.dongxiang.model.entity.AudioEntity;
import com.dongxiang.model.entity.FileEntity;
import com.dongxiang.model.entity.StatisticsEntity;
import com.dongxiang.model.repository.AudioRepository;
import com.dongxiang.model.repository.StatisticsRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//@RequestMapping("/aidongxiang")
@Controller
public class AudioController {

    @Autowired
    AudioRepository audioRepository;
    @Autowired
    StatisticsRepository statisticsRepository;
    @Autowired
    UploadComponent uploadComponent;

    private static Logger logger = LoggerFactory.getLogger(AudioController.class);

    @RequestMapping(value = "/AudioList", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody /*只需要响应字符串内容，可以自由控制返回的数据内容*/
    public String requestAudioList(){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        List<AudioEntity> list = audioRepository.findAll();
        List<AudioListEntity> entitylist = new ArrayList<>();
        for (AudioEntity audioEntity: list){
            AudioListEntity entity = new AudioListEntity();
            entity.setDuration(audioEntity.duration);
            entity.setId(audioEntity.id);
            entity.setImagePath(audioEntity.imagePath);
            entity.setName(audioEntity.name);
            entity.setPath(audioEntity.path);
            entity.setPrice(audioEntity.price);
            StatisticsEntity statistics = statisticsRepository.findOne(audioEntity.getId());
            if(statistics != null){
//                statistics.getStatDownload();
                entity.setStatPraise(statistics.getStatPraise());
                entity.setStatWatch(statistics.getStatWatch());
            }
            entitylist.add(entity);
        }

        AudioListRespBody respBody = new AudioListRespBody(entitylist);
        respBody.status = 0;
        respBody.desc = "操作成功";
        return gson.toJson(new ApiData<>(respBody));
    }



    @RequestMapping(value = "/AudioSubmit", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody /*只需要响应字符串内容，可以自由控制返回的数据内容*/
    public String requestAudioSubmit(@RequestParam("json")  String audio){

        Gson gson = new Gson();
        DefaultRespBody defaultRespBody = new DefaultRespBody();
        try {
            AudioEntity audioEntity = gson.fromJson(audio, AudioEntity.class);
            audioRepository.save(audioEntity);
            defaultRespBody.status = 0;
            defaultRespBody.desc = "操作成功";
        } catch (Exception e){
            e.printStackTrace();
            defaultRespBody.status = 1000;
            defaultRespBody.desc = "添加失败";
        }
        return gson.toJson(new ApiData<>("AudioSubmit",defaultRespBody));
    }

    @RequestMapping(value = "/AudioDetails/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody /*只需要响应字符串内容，可以自由控制返回的数据内容*/
    public String requestAudioDetails(@PathVariable("id") int id){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        AudioDetailsRespBody respBody = new AudioDetailsRespBody();

        AudioEntity audio = audioRepository.findOne(id);
        AudioDetailsEntity entity = new AudioDetailsEntity();
        if(audio != null){
            entity.setDescription(audio.description);
            entity.setDuration(audio.duration);
            entity.setId(audio.id);
            entity.setImagePath(audio.imagePath);
            entity.setIsCharge(audio.isCharge);
            entity.setName(audio.name);
            entity.setPath(audio.path);
            entity.setPrice(audio.price);
            StatisticsEntity statistics = statisticsRepository.findOne(audio.getId());
            if(statistics != null){
                entity.setStatDownload(statistics.getStatDownload());
                entity.setStatPraise(statistics.getStatPraise());
                int statWatch = statistics.getStatWatch()+1;
                entity.setStatWatch(statWatch);
                statisticsRepository.updateStatisticsWatch(statWatch, audio.id);
            } else {
                statistics = new StatisticsEntity();
                entity.setStatDownload(0);
                entity.setStatPraise(0);
                int statWatch = statistics.getStatWatch()+1;
                entity.setStatWatch(statWatch);
                statistics.setStatWatch(statWatch);
                statisticsRepository.save(statistics);
            }
//            entity.setIsCollection(audio.);
//            entity.setIsPraise(audio.);
            respBody.setAudio(entity);
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
    @RequestMapping(value = "/AudioDownload/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody /*只需要响应字符串内容，可以自由控制返回的数据内容*/
    public String requestDownload(@PathVariable("id") int id){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
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

        return gson.toJson(new ApiData<>("AudioDownload",respBody));
    }
    /**
     * 模拟点赞更新赞数量
     * @param id
     * @return
     */
    @RequestMapping(value = "/AudioPraise/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody /*只需要响应字符串内容，可以自由控制返回的数据内容*/
    public String requestPraise(@PathVariable("id") int id){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
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

        return gson.toJson(new ApiData<>("AudioPraise",respBody));
    }

    @RequestMapping(value = "/UploadAudio", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
//    @ResponseBody
    public String uploadAudio(HttpServletRequest request, ModelMap modelMap) throws IllegalStateException, IOException
    {
        String fileDir = "/audios/";
        //上传文件保存
        List<FileEntity> files = uploadComponent.upload(request,fileDir);

        if(files.size() > 0){
            //下面是存储视频数据
            modelMap.addAttribute("text", "上传成功");
            float price = 0f;
            int isCharge = 0;
            try {
                isCharge = Integer.parseInt(request.getParameter("isCharge"));
            } catch (NumberFormatException e){
                e.printStackTrace();
            }

            try {
                price = Float.parseFloat(request.getParameter("price"));
            } catch (NumberFormatException e){
                e.printStackTrace();
            }
            String description = request.getParameter("description");

            AudioEntity audioEntity = new AudioEntity();
            audioEntity.setDescription(description);
            audioEntity.setPrice(price);
            audioEntity.setIsCharge(isCharge);
            for (FileEntity file : files){
                audioEntity.setName(file.getName());
                audioEntity.setPath(file.getPath());
                audioEntity.setDuration(file.getDuration());
            }
            audioRepository.save(audioEntity);
        } else {
            modelMap.addAttribute("text", "上传失败");
        }
        return "upload/uploadResult";
    }


    // 上传音频
    @RequestMapping(value = "/jspUploadAudio", method = RequestMethod.GET)
    public String uploadFile(ModelMap modelMap) {
        return "upload/uploadAudio";
    }

}
