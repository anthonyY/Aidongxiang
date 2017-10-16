package com.dongxiang.model.repository;

import com.dongxiang.model.entity.StatisticsAudioEntity;
import com.dongxiang.model.entity.StatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 音频赞，查看，下载等统计
 * @author Anthony
 * createTime 2017-08-19
 */
@Repository
public interface StatisticsAudioRepository extends JpaRepository<StatisticsAudioEntity, Integer> {

    /***
     * 修改赞统计数量
     * @param statPraise 赞数量
     * @param id 音频id
     */
    @Modifying      // 说明该方法是修改操作
    @Transactional  // 说明该方法是事务性操作
    @Query("update StatisticsAudioEntity s set s.statPraise=:qStatPraise where s.id=:qId")
    public void updateStatisticsPraise(@Param("qStatPraise") int statPraise, @Param("qId") Integer id);

    /**
     * 更新下载量统计
     * @param statDownload 下载量
     * @param id 音频id
     */
    @Modifying
    @Transactional
    @Query("update StatisticsAudioEntity s set s.statDownload=:qStatDownload where s.id=:qId")
    public void updateStatisticsDownload(@Param("qStatDownload") int statDownload, @Param("qId") Integer id);

    /**
     * 更新查看音频的统计
     * @param statWatch 查看人数
     * @param id 音频id
     */
    @Modifying
    @Transactional
    @Query("update StatisticsAudioEntity s set s.statWatch=:qStatWatch where s.id=:qId")
    public void updateStatisticsWatch(@Param("qStatWatch") int statWatch, @Param("qId") Integer id);
}
