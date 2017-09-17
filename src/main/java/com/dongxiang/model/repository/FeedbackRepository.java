package com.dongxiang.model.repository;

import com.dongxiang.model.entity.FeedbackEntity;
import com.dongxiang.model.entity.FileEntity;
import com.dongxiang.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Anthony
 * createTime 2017-08-19
 */
@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Integer> {

    @Modifying      // 说明该方法是修改操作
    @Transactional  // 说明该方法是事务性操作
    // 定义查询
    // @Param注解用于提取参数
    @Query("update FeedbackEntity feedback set feedback.contact=:qContact, feedback.content=:qContent, feedback.images=:qImages, feedback.user=:qUser where feedback.id=:qId")
    public void updateFeedback(@Param("qContact") String contact, @Param("qContent") String content, @Param("qImages") List<FileEntity> images, @Param("qUser") UserEntity userEntity, @Param("qId") Integer id);
}
