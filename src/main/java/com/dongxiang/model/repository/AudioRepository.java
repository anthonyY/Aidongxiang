package com.dongxiang.model.repository;

import com.dongxiang.model.entity.AudioEntity;
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
public interface AudioRepository extends JpaRepository<AudioEntity, Integer> {

    @Modifying      // 说明该方法是修改操作
    @Transactional  // 说明该方法是事务性操作
    // 定义查询
    // @Param注解用于提取参数
    @Query("update AudioEntity audio set audio.name=:qName, audio.path=:qPath, audio.imagePath=:qImagePath, audio.price=:qPrice," +
            " audio.isCharge=:qIsCharge, audio.duration=:qDuration, audio.description=:qDescription where audio.id=:qId")
    public void updateUser(@Param("qName") String name, @Param("qPath") String path, @Param("qImagePath") String imagePath,
                           @Param("qPrice") double price, @Param("qIsCharge") int is_charge, @Param("qDuration") long duration,
                           @Param("qDescription") String description, @Param("qId") Integer id);
}
