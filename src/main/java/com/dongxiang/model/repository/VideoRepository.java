package com.dongxiang.model.repository;

import com.dongxiang.model.entity.VideoEntity;
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
public interface VideoRepository extends JpaRepository<VideoEntity, Integer> {

    @Modifying      // 说明该方法是修改操作
    @Transactional  // 说明该方法是事务性操作
    // 定义查询
    // @Param注解用于提取参数
    @Query("update VideoEntity video set video.name=:qName, video.path=:qPath, video.imagePath=:qImagePath, video.price=:qPrice," +
            " video.isCharge=:qIsCharge, video.duration=:qDuration, video.description=:qDescription where video.id=:qId")
    public void updateUser(@Param("qName") String name, @Param("qPath") String path, @Param("qImagePath") String imagePath,
                           @Param("qPrice") double price, @Param("qIsCharge") int is_charge, @Param("qDuration") long duration,
                           @Param("qDescription") String description, @Param("qId") Integer id);
}
