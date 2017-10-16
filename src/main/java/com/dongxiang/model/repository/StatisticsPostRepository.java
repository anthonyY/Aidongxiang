package com.dongxiang.model.repository;

import com.dongxiang.model.entity.StatisticsPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Anthony
 * createTime 2017-08-19
 */
@Repository
public interface StatisticsPostRepository extends JpaRepository<StatisticsPostEntity, Integer> {

    /***
     * 修改赞统计数量
     * @param statPraise 赞数量
     * @param id 视频id
     */
    @Modifying      // 说明该方法是修改操作
    @Transactional  // 说明该方法是事务性操作
    @Query("update StatisticsPostEntity s set s.statPraise=:qStatPraise where s.id=:qId")
    public void updateStatisticsPraise(@Param("qStatPraise") int statPraise, @Param("qId") Integer id);

    /**
     * 更新评论量统计
     * @param statComment 评论数量
     * @param id 评论id
     */
    @Modifying
    @Transactional
    @Query("update StatisticsPostEntity s set s.statCommnent=:qStatComment where s.id=:qId")
    public void updateStatisticsComment(@Param("qStatComment") int statComment, @Param("qId") Integer id);

    /**
     * 更新查分享次数的统计
     * @param statShare 分享次数
     * @param id 帖子id
     */
    @Modifying
    @Transactional
    @Query("update StatisticsPostEntity s set s.statShare=:qStatShare where s.id=:qId")
    public void updateStatisticsShare(@Param("qStatShare") int statShare, @Param("qId") Integer id);

}
