package com.dongxiang.model.component;

import com.dongxiang.base.comm.ApiData;
import com.dongxiang.constant.AIIStatus;
import com.dongxiang.interfaces.IRequestDispatcher;
import com.dongxiang.model.comm.DefaultRespBody;
import com.dongxiang.model.comm.request.SwitchActionReqBody;
import com.dongxiang.model.entity.StatisticsAudioEntity;
import com.dongxiang.model.entity.StatisticsPostEntity;
import com.dongxiang.model.entity.StatisticsVideoEntity;
import com.dongxiang.model.repository.StatisticsAudioRepository;
import com.dongxiang.model.repository.StatisticsPostRepository;
import com.dongxiang.model.repository.StatisticsVideoRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 点赞协议分发处理
 * @author Anthony
 * createTime 2017-10-16
 */
@Service
public class SwitchActionComponent implements IRequestDispatcher{

    @Autowired
    StatisticsPostRepository statisticsPostRepository;
    @Autowired
    StatisticsAudioRepository statisticsAudioRepository;
    @Autowired
    StatisticsVideoRepository statisticsVideoRepository;
    @Override
    public String dispatchRequest(String namespace, String session, String query) {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        SwitchActionReqBody reqBody = gson.fromJson(query, SwitchActionReqBody.class);
//        1赞 新闻
//        2赞 微博
//        3赞 视频
//        4赞 音频
//        5收藏 视频
//        6收藏 音频

        switch (reqBody.action){
            case 1:

                break;
            case 2:
                return praisePost(reqBody.id);
            case 3:
                return praiseAudio(reqBody.id);
            case 4:
                return praiseVideo(reqBody.id);
            case 5:

                break;
            case 6:

                break;
        }
        DefaultRespBody respBody = new DefaultRespBody();
        respBody.desc = "请求参数不完整";
        respBody.status = AIIStatus.INCOMPLETE_PARAMETERS;
        return gson.toJson(new ApiData<>("SwitchAction",respBody));
    }

    /**
     * 点赞帖子
     * @param id 帖子id
     * @return
     */
    private String praisePost(int id){
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

        return gson.toJson(new ApiData<>("SwitchAction",respBody));
    }

    /**
     * 点赞音频
     * @param id 音频id
     * @return
     */
    private String praiseAudio(int id){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        DefaultRespBody respBody = new DefaultRespBody();
        try {
            StatisticsAudioEntity statistics = statisticsAudioRepository.findOne(id);
            if(statistics != null){
                int statPraise = statistics.getStatPraise()+1;
                statisticsAudioRepository.updateStatisticsPraise(statPraise, id);
            } else {
                statistics = new StatisticsAudioEntity();
                statistics.setStatDownload(1);
                statisticsAudioRepository.save(statistics);
            }
            respBody.status = 0;
            respBody.desc = "操作成功";
        } catch (Exception e){
            e.printStackTrace();
            respBody.status = 2000;
            respBody.desc = "数据为空或不存在";
        }

        return gson.toJson(new ApiData<>("SwitchAction",respBody));
    }

    /**
     * 点赞视频
     * @param id 视频id
     * @return
     */
    private String praiseVideo(int id){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        DefaultRespBody respBody = new DefaultRespBody();
        try {
            StatisticsVideoEntity statistics = statisticsVideoRepository.findOne(id);
            if(statistics != null){
                int statPraise = statistics.getStatPraise()+1;
                statisticsVideoRepository.updateStatisticsPraise(statPraise, id);
            } else {
                statistics = new StatisticsVideoEntity();
                statistics.setStatDownload(1);
                statisticsVideoRepository.save(statistics);
            }
            respBody.status = 0;
            respBody.desc = "操作成功";
        } catch (Exception e){
            e.printStackTrace();
            respBody.status = 2000;
            respBody.desc = "数据为空或不存在";
        }

        return gson.toJson(new ApiData<>("SwitchAction",respBody));
    }
}
