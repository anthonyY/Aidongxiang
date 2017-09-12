package com.dongxiang.model.repository;

import com.dongxiang.model.entity.StatisticsEntity;
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
public interface StatisticsRepository extends JpaRepository<StatisticsEntity, Integer> {

//    @Modifying      // 说明该方法是修改操作
//    @Transactional  // 说明该方法是事务性操作
//    // 定义查询
//    // @Param注解用于提取参数
//    @Query("update StatisticsEntity s set s.statPraise=:qStatPraise, s.statDownload=:qStatDownload, s.statWatch=:qStatWatch where s.id=:qId")
//    public void updateStatistics(@Param("qStatPraise") int statPraise,@Param("qStatDownload") int statDownload,
//                           @Param("qStatWatch") int statWatch, @Param("qId") Integer id);
    @Modifying      // 说明该方法是修改操作
    @Transactional  // 说明该方法是事务性操作
    @Query("update StatisticsEntity s set s.statPraise=:qStatPraise where s.id=:qId")
    public void updateStatisticsPraise(@Param("qStatPraise") int statPraise, @Param("qId") Integer id);

    @Modifying      // 说明该方法是修改操作
    @Transactional  // 说明该方法是事务性操作
    @Query("update StatisticsEntity s set s.statDownload=:qStatDownload where s.id=:qId")
    public void updateStatisticsDownload(@Param("qStatDownload") int statDownload, @Param("qId") Integer id);

    @Modifying      // 说明该方法是修改操作
    @Transactional  // 说明该方法是事务性操作
    @Query("update StatisticsEntity s set s.statWatch=:qStatWatch where s.id=:qId")
    public void updateStatisticsWatch(@Param("qStatWatch") int statWatch, @Param("qId") Integer id);
}
